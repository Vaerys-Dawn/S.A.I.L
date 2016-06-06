import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.handle.impl.events.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.MentionEvent;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Message;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.MissingPermissionsException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Vaerys on 19/05/2016.
 */
public class AnnotationListener {

    final static Logger logger = LoggerFactory.getLogger(AnnotationListener.class);

    FileHandler handler;


    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        try {
            final Image avatar = Image.forFile(new File("Icons/Sailvector.png"));
            event.getClient().changeAvatar(avatar);
            final Status status = Status.game("with your heart.");
            event.getClient().changeStatus(status);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String message = reader.readLine();
                Channel channel = (Channel) event.getClient().getChannelByID(Globals.consoleMessageCID);
                channel.sendMessage(message);
            }
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (HTTP429Exception e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventSubscriber
    public void onGuildCreateEvent(GuildCreateEvent event) {
        try {
            String readableGuildID = event.getGuild().getID();
            logger.info("[GuildCreateEvent] - Connected to Guild with ID " + readableGuildID);
            handler = new FileHandler();
            handler.createDirectory("GuildConfigs");
            String file = "GuildConfigs/" + readableGuildID + "_config.json";
            GuildConfig guildConfig = new GuildConfig();
            if (!Files.exists(Paths.get(file))) {
                handler.writetoJson(file, guildConfig);
                logger.info("[GuildCreateEvent] - Creating config file for Guild with ID " + readableGuildID);
            }
            guildConfig = (GuildConfig) handler.readfromJson(file, GuildConfig.class);
            if (guildConfig.getDoLoginMessage()) {
                Channel channel;
                if (!guildConfig.getGeneralChannel().equals("")) {
                    channel = (Channel) event.getClient().getChannelByID(guildConfig.getGeneralChannel());
                } else {
                    channel = (Channel) event.getClient().getChannelByID(event.getGuild().getID());
                }
                channel.sendMessage("Hello everyone I am a Bot and my name is S.A.I.L, I am now helping out on your server. " +
                        "If you want to see my commands you can perform " + Globals.commandPrefix + "Help\n" +
                        "(if you dont want to see this message again have an admin do the command " + Globals.commandPrefix + "doLoginMessage)");
            }
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (HTTP429Exception e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        }
    }

    @EventSubscriber
    public void onMessageRecivedEvent(MessageReceivedEvent event) {
        String readableGuildID = event.getMessage().getGuild().getID();
        Message message = (Message) event.getMessage();
        String file;

        Commands commands = new Commands(message);
        Channel channel = (Channel) event.getMessage().getChannel();

        file = "GuildConfigs/" + readableGuildID + "_config.json";
        GuildConfig guildConfig = (GuildConfig) handler.readfromJson(file, GuildConfig.class);

        if (event.getMessage().getAuthor().getID().equals(Globals.creatorID)) {
            Globals.consoleMessageCID = event.getMessage().getChannel().getID().toString();
        }
        //calls the commands
        try {
            if (message.toString().equalsIgnoreCase("/tableflip")) {
                channel.sendMessage("(╯°□°）╯︵ ┻━┻");
            }
            if (message.toString().equalsIgnoreCase("/unflip")) {
                channel.sendMessage("┬─┬ノ( ゜-゜ノ)");
            }
            if (message.toString().equalsIgnoreCase("/shrug")) {
                channel.sendMessage("¯" + "\\" + "_(ツ)_/¯");
            }
            commands.setPOGOS(guildConfig);
            Method[] methods = Commands.class.getMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(CommandAnnotation.class)) {
                    CommandAnnotation anno = m.getAnnotation(CommandAnnotation.class);
                    String testMessage = message.toString().toLowerCase();
                    String testTo = (Globals.commandPrefix + anno.name()).toLowerCase();

                    if (testMessage.startsWith(testTo) && anno.channel().equalsIgnoreCase("any")) {
                        channel.sendMessage((String) m.invoke(commands, new Object[]{}));
                    }
                    if (guildConfig.getServersChannel().equals("") && anno.channel().equalsIgnoreCase("Servers")) {
                        if (testMessage.startsWith(testTo)) {
                            channel.sendMessage("This Command must be run in a 'Servers' Channel. Currently there is no Race Select Channel Configured." +
                                    "Please Run the command " + Globals.commandPrefix + "SetServersChannel in the channel you would like to assign as the server's" +
                                    "'Servers' channel.");
                        }
                    } else {
                        if ((testMessage.startsWith(testTo)) && anno.channel().equalsIgnoreCase("Servers")) {
                            if (channel.getID().equals(guildConfig.getServersChannel())) {
                                channel.sendMessage((String) m.invoke(commands, new Object[]{}));
                            } else {
                                Channel channelName = (Channel) event.getClient().getChannelByID(guildConfig.getServersChannel());
                                channel.sendMessage("Cannot perform command in this channel please go to " + channelName);
                            }
                        }
                    }
                    if (guildConfig.getRaceSelectChannel().equals("") && anno.channel().equalsIgnoreCase("RaceSelect")) {
                        if (testMessage.startsWith(testTo)) {
                            channel.sendMessage("This Command must be run in a 'Race Select' Channel. Currently there is no Race Select Channel Configured." +
                                    "Please Run the command " + Globals.commandPrefix + "SetRaceSelect in the channel you would like to assign as the server's" +
                                    "'Race Select' channel.");
                        }
                    } else {
                        if (testMessage.startsWith(testTo) && anno.channel().equalsIgnoreCase("RaceSelect")) {
                            if (channel.getID().equals(guildConfig.getRaceSelectChannel())) {
                                channel.sendMessage((String) m.invoke(commands, new Object[]{}));
                            } else {
                                Channel channelName = (Channel) event.getClient().getChannelByID(guildConfig.getRaceSelectChannel());
                                channel.sendMessage("Cannot perform command in this channel please go to " + channelName);
                            }
                        }
                    }
                }
            }
            commands.flushFiles();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (HTTP429Exception e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @EventSubscriber
    public void onMentionEvent(MentionEvent event) {
        String mentions = event.getMessage().getMentions().toString();
        String[] message = event.getMessage().toString().split("@!182502964404027392>");
        if (mentions.contains(event.getClient().getOurUser().mention())) {
            handler.createDirectory("Mentions");
            String location = "Mentions/Mentions.txt";
            String mention = event.getMessage().getGuild().getID() + ": " + event.getMessage().getAuthor().getName() + " - " + message[1];
            handler.writeToFile(location, mention);
        }
    }
}

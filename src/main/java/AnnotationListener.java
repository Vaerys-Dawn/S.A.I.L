import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.MentionEvent;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.impl.obj.Message;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

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
            final Status status = Status.game("Starbound");
            event.getClient().changeStatus(status);

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                Channel channel = (Channel) event.getClient().getChannelByID(Globals.consoleMessageCID);
                String message = scanner.nextLine();
                message = message.replaceAll("#Dawn#", event.getClient().getUserByID("153159020528533505").toString());
                message = message.replaceAll("teh", "the");
                message = message.replaceAll("Teh", "The");
                if (!message.equals("")) {
                    channel.sendMessage(message);
                }
            }
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
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
            handler.createDirectory("CommandLists");
            String file = "GuildConfigs/" + readableGuildID + "_config.json";
            GuildConfig guildConfig = new GuildConfig();
            if (!Files.exists(Paths.get(file))) {
                handler.writetoJson(file, guildConfig);
                logger.info("[GuildCreateEvent] - Creating config file for Guild with ID " + readableGuildID);
            }
            file = "CommandLists/" + readableGuildID + "_CustomCommands.json";
            CustomCommands customCommands = new CustomCommands();
            if (!Files.exists(Paths.get(file))){
                handler.writetoJson(file, customCommands);
                logger.info("Creating Custom Commands List");
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
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }
    }

    @EventSubscriber
    public void onMessageRecivedEvent(MessageReceivedEvent event) {
        Message message = (Message) event.getMessage();
        Channel channel = (Channel) event.getMessage().getChannel();
        Guild guild = (Guild) event.getMessage().getGuild();
        String readableGuildID = event.getMessage().getGuild().getID();
        String file;

        if (event.getMessage().getAuthor().getID().equals(Globals.creatorID)) {
            Globals.consoleMessageCID = event.getMessage().getChannel().getID().toString();
        }


        file = "GuildConfigs/" + readableGuildID + "_config.json";
        GuildConfig guildConfig = (GuildConfig) handler.readfromJson(file, GuildConfig.class);
        file = "CommandLists/" + readableGuildID + "_CustomCommands.json";
        CustomCommands customCommands = (CustomCommands) handler.readfromJson(file, CustomCommands.class);

        Commands commands = new Commands((Message) event.getMessage());

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
            if (message.toString().equalsIgnoreCase("sail.updateInfo")) {
                if (event.getMessage().getAuthor().equals(event.getMessage().getGuild().getOwner())) {
                    InfoChannel infoChannel = new InfoChannel();
                    infoChannel.updateInfo(channel,guild);
                }
            }


            commands.setPOGOS(guildConfig,customCommands);

            Method[] methods = Commands.class.getMethods();
            Method doMethod;

            if (message.toString().toLowerCase().startsWith(Globals.CCPrefix.toLowerCase())){
                StringBuilder CCName = new StringBuilder();
                String[] splitMessage = message.toString().split(" ");
                CCName.append(splitMessage[0]);
                CCName.delete(0, Globals.CCPrefix.length());
                String regex;
                String args;
                StringBuilder builder = new StringBuilder();
                String[] CCResponse = customCommands.getCommand(CCName.toString());
                if (message.toString().length() != (CCResponse[1].length() + Globals.CCPrefix.length())){
                    builder.append(message.toString());
                    builder.delete(0,CCResponse[1].length() + Globals.CCPrefix.length() + 1);
                    args = builder.toString();
                } else {
                    args = "Nothing";
                }
                regex = CCResponse[2];
                regex = regex.replaceAll("#author!#", message.getAuthor().getName());
                regex = regex.replaceAll("#author#", message.getAuthor().getDisplayName(message.getGuild()));
                regex = regex.replaceAll("#args", args);
                channel.sendMessage(regex);
            }


            if (message.toString().toLowerCase().startsWith(Globals.commandPrefix.toLowerCase())) {
                for (Method m : methods) {
                    if (m.isAnnotationPresent(CommandAnnotation.class)) {
                        CommandAnnotation commandAnno = m.getAnnotation(CommandAnnotation.class);
                        String testMessage = message.toString().toLowerCase();
                        String[] splitMessage = testMessage.split(" ");
                        String testTo = (Globals.commandPrefix + commandAnno.name()).toLowerCase();
                        if (m.isAnnotationPresent(AliasAnnotation.class)) {
                            AliasAnnotation aliasAnno = m.getAnnotation(AliasAnnotation.class);
                            String[] alias = aliasAnno.alias();
                            for (int i = 0; i < alias.length; i++) {
                                String testAlias = Globals.commandPrefix.toLowerCase() + alias[i].toLowerCase();
                                if (testMessage.startsWith(testAlias) && splitMessage[0].length() == testAlias.length()) {
                                    handleCommand(m, event, guildConfig, commands);
                                }
                            }
                        } else {
                            if (testMessage.startsWith(testTo) && splitMessage[0].length() == testTo.length()) {
                                handleCommand(m, event,guildConfig, commands);
                            }
                        }
                    }
                }
            }
            commands.flushFiles();
        } catch (MissingPermissionsException e) {
            logger.info("Bot does not have Permission for that thing");
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }
    }

    public void handleCommand(Method doMethod, MessageReceivedEvent event, GuildConfig guildConfig,Commands commands) {
        try {
            Channel channel = (Channel) event.getMessage().getChannel();


            CommandAnnotation commandAnno = doMethod.getAnnotation(CommandAnnotation.class);
            if (commandAnno.responseGeneral()){
                channel = (Channel) event.getMessage().getGuild().getChannelByID(guildConfig.getGeneralChannel());
            }

            if (commandAnno.channel().equalsIgnoreCase("any")) {
                channel.sendMessage((String) doMethod.invoke(commands, new Object[]{}));
            } else if (commandAnno.channel().equalsIgnoreCase("servers")) {
                if (guildConfig.getServersChannel().equalsIgnoreCase("")) {
                    channel.sendMessage(commands.channelNotInit("Servers"));
                } else {
                    if (channel.equals((Channel) event.getClient().getChannelByID(guildConfig.getServersChannel()))) {
                        channel.sendMessage((String) doMethod.invoke(commands, new Object[]{}));
                    } else {
                        channel.sendMessage(commands.wrongChannel(guildConfig.getServersChannel()));
                    }
                }
            } else if (commandAnno.channel().equalsIgnoreCase("raceselect")) {
                if (guildConfig.getRaceSelectChannel().equalsIgnoreCase("")) {
                    channel.sendMessage(commands.channelNotInit("RaceSelect"));
                } else {
                    if (channel.equals((Channel) event.getClient().getChannelByID(guildConfig.getRaceSelectChannel()))) {
                        channel.sendMessage((String) doMethod.invoke(commands, new Object[]{}));
                    } else {
                        channel.sendMessage(commands.wrongChannel(guildConfig.getRaceSelectChannel()));
                    }
                }
            }

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        }
    }


    @EventSubscriber
    public void onMentionEvent(MentionEvent event) {
//        if (!event.getMessage().toString().toLowerCase().contains("@everyone") || !event.getMessage().toString().toLowerCase().contains("@here")) {
//            String mentions = event.getMessage().getMentions().toString();
//            String[] message = event.getMessage().toString().split("@!182502964404027392>");
//            if (mentions.contains(event.getClient().getOurUser().mention())) {
//                handler.createDirectory("Mentions");
//                String location = "Mentions/Mentions.txt";
//                String mention = event.getMessage().getGuild().getID() + ": " + event.getMessage().getAuthor().getName() + " - " + message[1];
//                handler.writeToFile(location, mention);
//            }
//        }
    }
}

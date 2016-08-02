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
            try {
                final Image avatar = Image.forFile(new File("Icons/Sailvector.png"));
                event.getClient().changeAvatar(avatar);
                final Status status = Status.game("Starbound");
                event.getClient().changeStatus(status);
                Thread.sleep(30000);

                Scanner scanner = new Scanner(System.in);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
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
            handler.createDirectory("Characters");
            String file = "GuildConfigs/" + readableGuildID + "_config.json";
            String CCfile = "CommandLists/" + readableGuildID + "_CustomCommands.json";
            String charFile = "Characters/" + readableGuildID + "_CharList.json";
            GuildConfig guildConfig = new GuildConfig();
            if (!Files.exists(Paths.get(file))) {
                handler.writetoJson(file, guildConfig);
                logger.info("[GuildCreateEvent] - Creating config file for Guild with ID " + readableGuildID);
            }
            guildConfig = (GuildConfig) handler.readfromJson(file, GuildConfig.class);
            CustomCommands customCommands = new CustomCommands();
            if (!Files.exists(Paths.get(CCfile))) {
                handler.writetoJson(CCfile, customCommands);
                logger.info("Creating Custom Commands List for guild with ID " + readableGuildID);
            }
            Characters characters = new Characters();
            if (!Files.exists(Paths.get(charFile))){
                handler.writetoJson(charFile, characters);
                logger.info("Creating Char file for guild with ID " + readableGuildID);
            }
            if (guildConfig.getDoLoginMessage()) {
                Channel channel;
                if (!guildConfig.getGeneralChannel().equals("")) {
                    channel = (Channel) event.getClient().getChannelByID(guildConfig.getGeneralChannel());
                } else {
                    channel = (Channel) event.getClient().getChannelByID(event.getGuild().getID());
                }
                channel.sendMessage("> I have finished booting and I am now listening for commands...");
            }
            guildConfig.setGuildName(event.getGuild().getName());
            handler.writetoJson(file, guildConfig);
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
        boolean failedToSend = false;
        String errorMessage = "";
        Message message = (Message) event.getMessage();
        Channel channel = (Channel) event.getMessage().getChannel();
        String readableGuildID = event.getMessage().getGuild().getID();
        String file;

        if (event.getMessage().getAuthor().getID().equals(Globals.creatorID)) {
            Globals.consoleMessageCID = event.getMessage().getChannel().getID().toString();
        }


        file = "GuildConfigs/" + readableGuildID + "_config.json";
        GuildConfig guildConfig = (GuildConfig) handler.readfromJson(file, GuildConfig.class);
        file = "CommandLists/" + readableGuildID + "_CustomCommands.json";
        CustomCommands customCommands = (CustomCommands) handler.readfromJson(file, CustomCommands.class);
        file = "Characters/" + readableGuildID + "_CharList.json";
        Characters characters = (Characters) handler.readfromJson(file, Characters.class);

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

            commands.setPOGOS(guildConfig, customCommands,characters);

            Method[] methods = Commands.class.getMethods();

            if (message.toString().toLowerCase().startsWith(Globals.CCPrefix.toLowerCase())) {
                channel.sendMessage(customCommands.getCommand(message.toString(),event.getMessage().getAuthor(),(Guild) event.getMessage().getGuild()));
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
                                handleCommand(m, event, guildConfig, commands);
                            }
                        }
                    }
                }
            }
            commands.flushFiles();
        } catch (MissingPermissionsException e) {
            logger.info("Bot does not have Permission for that thing");
        } catch (DiscordException e) {
            failedToSend = true;
            errorMessage = e.getErrorMessage();
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }
        if (failedToSend) {
            try {
                Thread.sleep(2000);
                channel.sendMessage(errorMessage + "\n" +
                        "> Please Try Again In a Second.");
            } catch (DiscordException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (RateLimitException e) {
                e.printStackTrace();
            } catch (MissingPermissionsException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleCommand(Method doMethod, MessageReceivedEvent event, GuildConfig guildConfig, Commands commands) {
        Channel channel = (Channel) event.getMessage().getChannel();
        try {
            String response;

            CommandAnnotation commandAnno = doMethod.getAnnotation(CommandAnnotation.class);
            if (commandAnno.responseGeneral()) {
                channel = (Channel) event.getMessage().getGuild().getChannelByID(guildConfig.getGeneralChannel());
            }

            if (commandAnno.channel().equalsIgnoreCase("any")) {
                response = (String) doMethod.invoke(commands, new Object[]{});
                if (!response.equals("")) {
                    channel.sendMessage(response);
                }
            } else if (commandAnno.channel().equalsIgnoreCase(Globals.channelServers)) {
                if (guildConfig.getServersChannel().equalsIgnoreCase("")) {
                    channel.sendMessage(commands.channelNotInit(Globals.channelServers));
                } else {
                    if (channel.equals(event.getClient().getChannelByID(guildConfig.getServersChannel()))) {
                        response = (String) doMethod.invoke(commands, new Object[]{});
                        if (!response.equals("")) {
                            channel.sendMessage(response);
                        }
                    } else {
                        channel.sendMessage(commands.wrongChannel(guildConfig.getServersChannel()));
                    }
                }
            } else if (commandAnno.channel().equalsIgnoreCase(Globals.channelRaceSelect)) {
                if (guildConfig.getRaceSelectChannel().equalsIgnoreCase("")) {
                    channel.sendMessage(commands.channelNotInit(Globals.channelRaceSelect));
                } else {
                    if (channel.equals(event.getClient().getChannelByID(guildConfig.getRaceSelectChannel()))) {
                        response = (String) doMethod.invoke(commands, new Object[]{});
                        if (!response.equals("")) {
                            channel.sendMessage(response);
                        }
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

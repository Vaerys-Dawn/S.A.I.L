import jdk.nashorn.internal.objects.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.handle.impl.events.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.json.requests.KeepAliveRequest;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.MissingPermissionsException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by Vaerys on 19/05/2016.
 */
public class AnnotationListener {

    final static Logger logger = LoggerFactory.getLogger(AnnotationListener.class);

    ArrayList<Command> commands = new ArrayList<Command>();
    Command info;
    Command iAmListening;
    Command hello;
    Command nightlyFAQ;
    Command please;
    Command botMessage;
    Command whatAreYou;
    Command competition;
    Command addRace;
    Command help;
    Command newQC;
    Command nightlyfix;
    ArrayList<GuildConfig> guildConfigs;



    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        logger.info("Connected to Discord");
        //Example of a new command
        //Command command = new Command("Name", "Command", '>', "Usage", "Description");
        //Initiating commands
        commands.add(info = new Command(Globals.GENERAL_COMMAND_PREFIX, Globals.INFO_NAME, "", "Displays some information about Sail and It's Command list"));
       // commands.add(iAmListening = new Command(Globals.GENERAL_COMMAND_PREFIX, "IAmListening", "", "Tells you what Sail does"));
        commands.add(hello = new Command(Globals.GENERAL_COMMAND_PREFIX, Globals.HELLO_NAME, "", "Says Hello"));
        commands.add(nightlyFAQ = new Command(Globals.GENERAL_COMMAND_PREFIX, Globals.FAQ_NAME, "", "Links the nightly FAQ Reddit post"));
       // commands.add(whatAreYou = new Command(Globals.GENERAL_COMMAND_PREFIX, "WhatAreYou", "", "Demoralises the bot"));
       // commands.add(competition = new Command(Globals.GENERAL_COMMAND_PREFIX, "Comp", " [Link to Image]", "Enters your art into the competition"));
        commands.add(help = new Command(Globals.GENERAL_COMMAND_PREFIX, Globals.HELP_NAME, " [Command]", "Gives information about commands"));
       // commands.add(newQC = new Command(Globals.GENERAL_COMMAND_PREFIX, "NewQC", " [Command name] [Responce]", "Makes a New Quick Response Command"));
        commands.add(nightlyfix = new Command(Globals.GENERAL_COMMAND_PREFIX, Globals.NFIX_NAME, "", "Posts a link to the Nightly fix Mod"));

//        commands.add(please = new Command(Globals.ADMIN_COMMAND_PREFIX, "Please", "", "Does Magic"));
//        commands.add(addRace = new Command(Globals.ADMIN_COMMAND_PREFIX, "AddRace", " [Role Name]", "adds a race to the list of races that can be self assigned"));
//
//        commands.add(botMessage = new Command(Globals.CREATOR_COMMAND_PREFIX, "BotMessage", "", "Says Stuff"));

        try {
            final Image avatar = Image.forFile(new File("Icons/S_A_I_L.png"));
            event.getClient().changeAvatar(avatar);
            final Status status = Status.game("with your heart.");
            event.getClient().changeStatus(status);
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (HTTP429Exception e) {
            e.printStackTrace();
        }
    }

    @EventSubscriber
    public void onGuildCreateEvent(GuildCreateEvent event) {
        logger.info("[GuildCreateEvent] connected Guilds: " + event.getClient().getGuilds().size());
            try {
            File configDir = new File("ServerConfigs");
            if (!configDir.exists()) {
                configDir.mkdirs();
            }
            Guild guild = (Guild) event.getGuild();
            String checkableGuildID = guild.getID();
            Channel channel = (Channel) event.getClient().getGuildByID(checkableGuildID).getChannelByID(checkableGuildID);

            File file = new File("ServerConfigs/" + checkableGuildID + "_Config.txt");
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);
            } else {
                boolean canSendmessage = false;
                int i = 0;
                do {
                    try {
                        channel.sendMessage("Hello My Name is S.A.I.L, I am now listening for Commands\n");
                        canSendmessage = true;
                    } catch (MissingPermissionsException e) {
                        i++;
                    }
                    System.out.println("[GuildCreateEvent] Guild with ID " + checkableGuildID + " Is being Initialised");
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file, true);
                    for (int o = 0; o < 3; o++) {
                        writer.append(guild.getChannels().get(i).toString() + "\n");
                    }
                    writer.flush();
                    writer.close();
                } while (!canSendmessage);
            }
            Globals.consoleMessageCID = checkableGuildID;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (HTTP429Exception e) {
            e.printStackTrace();
        }
    }

    @EventSubscriber
    public void onMessageRecivedEvent(MessageReceivedEvent event) {
        String message = event.getMessage().toString(); //gets the message converts it to a String
        String roles = event.getMessage().getAuthor().getRolesForGuild(event.getMessage().getGuild()).toString().toLowerCase(); //gets the roles of the user and turns it into a string
        Guild guild = (Guild) event.getMessage().getGuild(); //gets the guild that the message was sent to
        Channel channel = (Channel) event.getMessage().getChannel(); //gets the channel the message was sent to
        IUser author = event.getMessage().getAuthor();
        GeneralCommands generalCommands = new GeneralCommands(channel, author, guild, commands, message);
        AdminCommands adminCommands = new AdminCommands(channel, author, guild);
        CreatorCommands creatorCommands = new CreatorCommands(channel, author, guild);
        if (event.getMessage().getAuthor().getID().equals(Globals.creatorID)){
            Globals.consoleMessageCID = event.getMessage().getChannel().getID().toString();
        }
        //calls the commands
        try {
            //unlisted commanda
            if (message.equalsIgnoreCase("/tableflip")) {
                channel.sendMessage("(╯°□°）╯︵ ┻━┻");
            }
            if (message.equalsIgnoreCase("/unflip")) {
                channel.sendMessage("┬─┬ノ( ゜-゜ノ)");
            }
            if (message.equalsIgnoreCase("/shrug")) {
                channel.sendMessage("¯" + "\\" + "_(ツ)_/¯");
            }
            if (message.toLowerCase().startsWith(Globals.RESPONSE_COMMAND_PREFIX.toLowerCase())) {

            }

            //listed commands
            if (message.toLowerCase().startsWith(Globals.GENERAL_COMMAND_PREFIX.toLowerCase())) {
                for (int i = 0; i < commands.size(); i++) {
                    if (message.toLowerCase().startsWith(commands.get(i).getCommand())) {
                        GeneralCommands.callEvent(commands.get(i).getCommand());
                    }
                }
            }
//                if (message.equalsIgnoreCase(hello.getCommand())) {
//                    System.out.println(message);
//                    channel.sendMessage(generalCommands.HelloSail(isAdmin, isMod, isCF, isOwner, isCreator));
//                }
//                if (message.equalsIgnoreCase(iAmListening.getCommand())) {
//                    System.out.println(message);
//                    channel.sendMessage(generalCommands.IAmListening());
//                }
//                if (message.equalsIgnoreCase(info.getCommand())) {
//                    System.out.println(message);
//                    channel.sendMessage(generalCommands.InfoSail(commands));
//                }
//                if (message.equalsIgnoreCase(nightlyFAQ.getCommand())) {
//                    System.out.println(message);
//                    channel.sendMessage(generalCommands.NightlyFAQ());
//                }
//                if (message.equalsIgnoreCase(whatAreYou.getCommand())) {
//                    System.out.println(message);
//                    channel.sendMessage(generalCommands.whatAreYouSail());
//                }
//                if (message.toLowerCase().startsWith(competition.getCommand())) {
//                    System.out.println(message);
//                    channel.sendMessage(generalCommands.sailCompetition(message));
//                }
//                if (message.toLowerCase().startsWith(help.getCommand())) {
//                    System.out.println(message);
//                    channel.sendMessage(generalCommands.Help(commands, message));
//                }
//                if (message.toLowerCase().equalsIgnoreCase(nightlyfix.getCommand())) {
//                    System.out.println(message);
//                    channel.sendMessage(generalCommands.nightlyFix());
//                }
//            }
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (HTTP429Exception e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
    }
}

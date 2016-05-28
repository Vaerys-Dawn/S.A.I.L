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
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    ArrayList<GuildConfig> guildConfigs;

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        logger.info("Connected to Discord");
        //Example of a new command
        //Command command = new Command("Name", "Command", '>', "Usage", "Description");
        //Initiating commands
        commands.add(info = new Command(Globals.GENERAL_COMMAND_PREFIX, "Info", "", "Displays some information about Sail and It's Command list"));
        commands.add(iAmListening = new Command(Globals.GENERAL_COMMAND_PREFIX, "IAmListening", "", "Tells you what Sail does"));
        commands.add(hello = new Command(Globals.GENERAL_COMMAND_PREFIX, "Hello", "", "Says Hello"));
        commands.add(nightlyFAQ = new Command(Globals.GENERAL_COMMAND_PREFIX, "NightlyFAQ", "", "Links the nightly FAQ Reddit post"));
        commands.add(whatAreYou = new Command(Globals.GENERAL_COMMAND_PREFIX, "WhatAreYou", "", "Demoralises the bot"));
        commands.add(competition = new Command(Globals.GENERAL_COMMAND_PREFIX, "Comp", " [Link to Image]", "Enters your art into the competition"));
        commands.add(help = new Command(Globals.GENERAL_COMMAND_PREFIX, "Help", " [Command]", "Gives information about commands"));

        commands.add(please = new Command(Globals.ADMIN_COMMAND_PREFIX, "Please", "", "Does Magic"));
        commands.add(addRace = new Command(Globals.ADMIN_COMMAND_PREFIX, "AddRace", " [Role Name]", "adds a race to the list of races that can be self assigned"));

        commands.add(botMessage = new Command(Globals.CREATOR_COMMAND_PREFIX, "BotMessage", "", "Says Stuff"));

//        try {
//            System.out.println(event.getClient().getGuilds().size());
//            for (int i = 0; i < event.getClient().getGuilds().size(); i++) {
//                System.out.println("test");
//                Channel channel;
//                channel = (Channel) event.getClient().getGuilds().get(i).getChannelByID(event.getClient().getGuilds().get(i).getID());
//                channel.sendMessage("Hello My Name is S.A.I.L, I am now listening for Commands");
//                String key = event.getClient().getGuilds().get(i).getID() + "_config";
//                FileReader reader = new FileReader(key);
//            }
//        } catch (FileNotFoundException ex) {
//            for (int i = 0; i < event.getClient().getGuilds().size(); i++) {
//                String key = event.getClient().getGuilds().get(i).getID() + "_config";
//                File file = File.createTempFile(key, ".txt", new File("../ServerConfigs"));
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } catch (DiscordException ex) {
//            ex.printStackTrace();
//        } catch (MissingPermissionsException ex) {
//            ex.printStackTrace();
//        } catch (HTTP429Exception ex) {
//            ex.printStackTrace();
//        }
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

            File file = new File("ServerConfigs/" + checkableGuildID + "_Config.json");
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
            } else {
                System.out.println("[GuildCreateEvent] Guild with ID " + checkableGuildID + " Is being Initialised");
                file.createNewFile();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        GeneralCommands generalCommands = new GeneralCommands(channel, author, guild);
        AdminCommands adminCommands = new AdminCommands(channel, author, guild);
        CreatorCommands creatorCommands = new CreatorCommands(channel, author, guild);
        boolean isOwner = false;
        boolean isAdmin = false;
        boolean isMod = false;
        boolean isCF = false;
        boolean isCreator = false;
        //get the user's type
        if (author.getID().equals("153159020528533505")) {
            isCreator = true;
        }
        if (guild.getOwner().equals(author)) {
            isOwner = true;
        }
        for (int i = 0; i < author.getRolesForGuild(guild).size(); i++) {
            IRole role = author.getRolesForGuild(guild).get(i);
            if (role.getPermissions().contains(Permissions.ADMINISTRATOR)) {
                isAdmin = true;
            }
            if (role.getPermissions().contains(Permissions.MANAGE_ROLES)) {
                isMod = true;
            }
            if (role.getName().toLowerCase().contains("chucklefish")) {
                isCF = true;
            }
        }
        //calls the commands
        try {
            //unlisted commanda
            if (message.equalsIgnoreCase("/tableflip")){
                channel.sendMessage("(╯°□°）╯︵ ┻━┻");
            }
            if (message.equalsIgnoreCase("/unflip")){
                channel.sendMessage("┬─┬ノ( ゜-゜ノ)");
            }
            if (message.equalsIgnoreCase("/shrug")){
                channel.sendMessage("¯" + "\\" + "_(ツ)_/¯");
            }

            //listed commands
            if (message.toLowerCase().startsWith(Globals.GENERAL_COMMAND_PREFIX.toLowerCase())) {
                if (message.equalsIgnoreCase(hello.getCommand())) {
                    System.out.println(message);
                    channel.sendMessage(generalCommands.HelloSail(isAdmin, isMod, isCF, isOwner, isCreator));
                }
                if (message.equalsIgnoreCase(iAmListening.getCommand())) {
                    System.out.println(message);
                    channel.sendMessage(generalCommands.IAmListening());
                }
                if (message.equalsIgnoreCase(info.getCommand())) {
                    System.out.println(message);
                    channel.sendMessage(generalCommands.InfoSail(commands));
                }
                if (message.equalsIgnoreCase(nightlyFAQ.getCommand())) {
                    System.out.println(message);
                    channel.sendMessage(generalCommands.NightlyFAQ());
                }
                if (message.equalsIgnoreCase(whatAreYou.getCommand())) {
                    System.out.println(message);
                    channel.sendMessage(generalCommands.whatAreYouSail());
                }
                if (message.toLowerCase().startsWith(competition.getCommand())) {
                    System.out.println(message);
                    channel.sendMessage(generalCommands.sailCompetition(message));
                }
                if (message.toLowerCase().startsWith(help.getCommand())){
                    System.out.println(message);
                    channel.sendMessage(generalCommands.Help(commands, message));
                }
            }
            if (message.toLowerCase().startsWith(Globals.ADMIN_COMMAND_PREFIX.toLowerCase())) {
                if(isAdmin || isMod || isCF ||isOwner) {
                    if (message.equalsIgnoreCase(please.getCommand())) {
                        System.out.println(message);
                        channel.sendMessage(adminCommands.SailPlease());
                    }
                    if (message.toLowerCase().startsWith(addRace.getCommand().toLowerCase())){
                        channel.sendMessage(adminCommands.AddRace());
                    }
                }else if(isAdmin || isOwner){

                }else{
                    channel.sendMessage("I'm afraid I cannot do that for you " + author.getName());
                }

            }
            if (message.toLowerCase().startsWith(Globals.CREATOR_COMMAND_PREFIX.toLowerCase())) {
                if (isCreator)
                    if (message.equalsIgnoreCase(botMessage.getCommand())) {
                        System.out.println(message);
                        channel.sendMessage(creatorCommands.botMessage());
                    }
            }

        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (HTTP429Exception e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
    }
}

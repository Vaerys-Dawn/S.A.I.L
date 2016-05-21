import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;

import java.util.ArrayList;

/**
 * Created by Vaerys on 19/05/2016.
 */
public class AnnotationListener {

    ArrayList<Command> commands = new ArrayList<Command>();
    Command infoSail;
    Command iAmListening;
    Command helloSail;
    Command nightlyFAQ;
    Command sailPlease;
    Command botMessage;


    String iAmListeningMessage;
    String infoSailPrefix;
    String infoSailSuffix;
    String nightlyFAQLink;

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        System.out.println("connected");
        //Example of a new command
        //Command command = new Command("Name", "Command", '>', "Usage", "Description");

        //Initiating commands
        commands.add(infoSail = new Command(Globals.GENERAL_COMMAND_PREFIX + "InfoSail", "", "Displays some information about Sail and It's Command list"));
        commands.add(iAmListening = new Command(Globals.GENERAL_COMMAND_PREFIX + "IAmListening", "", "Tells you what Sail does"));
        commands.add(helloSail = new Command(Globals.GENERAL_COMMAND_PREFIX + "HelloSail", "", "Says Hello"));
        commands.add(nightlyFAQ = new Command(Globals.GENERAL_COMMAND_PREFIX + "NightlyFAQ", "", "Links the nightly FAQ Reddit post"));
        commands.add(sailPlease = new Command(Globals.ADMIN_COMMAND_PREFIX + "SailPlease", "", "Does Magic"));
        commands.add(botMessage = new Command(Globals.CREATOR_COMMAND_PREFIX + "BotMessage", "", "Says Stuff"));
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
        String userType;


        //user type detectors (All those strings a temporary)
        if (roles.contains("chucklefish")) {
            userType = "CFStaff"; //tests for the Chucklefish role
        } else if (roles.contains("admin")) {
            userType = "Admin"; //tests for the Admin role
        } else if (roles.contains("moderator")) {
            userType = "Moderator";//tests for the Moderator role
        } else {
            userType = "User";
        }

        try {
            if (message.startsWith(Globals.GENERAL_COMMAND_PREFIX)) {
                if (message.equalsIgnoreCase(helloSail.getCommand())) {
                    System.out.println(message);
                    channel.sendMessage(generalCommands.HelloSail(userType));
                }
                if (message.equalsIgnoreCase(iAmListening.getCommand())) {
                    System.out.println(message);
                    channel.sendMessage(generalCommands.IAmListening());
                }
                if (message.equalsIgnoreCase(infoSail.getCommand())) {
                    System.out.println(message);
                    channel.sendMessage(generalCommands.InfoSail(commands));
                }
                if (message.equalsIgnoreCase(nightlyFAQ.getCommand())) {
                    System.out.println(message);
                    channel.sendMessage(generalCommands.NightlyFAQ());
                }
            }
            if (message.startsWith(Globals.ADMIN_COMMAND_PREFIX)) {
                if (message.equalsIgnoreCase(sailPlease.getCommand())) {
                    System.out.println(message);
                    channel.sendMessage(adminCommands.SailPlease(userType));
                }
            }
            if (message.startsWith(Globals.CREATOR_COMMAND_PREFIX)){
                if (author.getID().equals("153159020528533505"))
                if (message.equalsIgnoreCase(botMessage.getCommand())){
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

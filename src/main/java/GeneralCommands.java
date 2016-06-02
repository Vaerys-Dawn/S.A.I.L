import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * Created by Vaerys on 20/05/2016.
 */
public class GeneralCommands {

    private static Channel channel;
    private static IUser user;
    private static Guild guild;
    private static String message;

    private static boolean isOwner;
    private static boolean isAdmin;
    private static boolean isMod;
    private static boolean isCF;
    private static boolean isCreator;
    private static ArrayList<Command> commands;


    public GeneralCommands(Channel channel, IUser user, Guild guild, ArrayList<Command> commands, String message) {
        this.channel = channel;
        this.user = user;
        this.guild = guild;
        this.commands = commands;
        this.message = message;

        isAdmin = false;
        isCF = false;
        isCreator = false;
        isMod = false;
        isOwner = false;

        //get the user's type
        if (user.getID().equals(Globals.creatorID)) {
            isCreator = true;
        }
        if (guild.getOwner().equals(user)) {
            isOwner = true;
        }
        for (int i = 0; i < user.getRolesForGuild(guild).size(); i++) {
            IRole role = user.getRolesForGuild(guild).get(i);
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
    }

    public static void callEvent(String command) {
        try {
            if (command.contains(Globals.HELLO_NAME.toLowerCase())) {
                channel.sendMessage(HelloSail());
            }
            if (command.contains(Globals.INFO_NAME.toLowerCase())) {
                channel.sendMessage(InfoSail());
            }
            if (command.contains(Globals.HELP_NAME.toLowerCase())) {
                channel.sendMessage(Help());
            }
            if (command.contains(Globals.FAQ_NAME.toLowerCase())) {
                channel.sendMessage(NightlyFAQ());
            }
            if (command.contains(Globals.NFIX_NAME.toLowerCase())) {
                channel.sendMessage(nightlyFix());
            }
        } catch (HTTP429Exception e){
            e.printStackTrace();
        } catch (DiscordException e){
            e.printStackTrace();
        } catch (MissingPermissionsException e){
            e.printStackTrace();
        }
    }

    public static String HelloSail() {
        if (isCreator) {
            return "Hello Creator";
        } else if (isOwner) {
            return "Hello Server Owner";
        } else if (isAdmin) {
            return "Hello Admin";
        } else if (isMod) {
            return "Hello Moderator";
        } else if (isCF) {
            return "Hello Chucklefish Staff Member";
        } else {
            return "Hello User";
        }
    }


    public String IAmListening() {
        return "Hello all i am S.A.I.L and i will be managing \n" +
                "your server soon, at the moment all i am doing is listening to\n" +
                "your messages and waiting for a command, try >InfoSail.";
    }

    public static String NightlyFAQ() {
        return "Here are the Frequently Asked Questions when it comes to the starbound nightly Build.\n" +
                "https://www.reddit.com/r/starbound/comments/4jnhjw/nightly_faq_so_you_want_to_play_nightly/#announce";
    }

    public static String InfoSail() {
        StringBuilder response = new StringBuilder();
        response.append("Hello My Name is S.A.I.L\n" +
                "Here are the commands currently at my disposal:");
        for (int i = 0; i < commands.size(); i++) {
            response.append("\n  " + commands.get(i).getCommand());
        } //add all of the commands to the list
        response.append("\nMy Author is Dawn Felstar and I am currently In Development\n" +
                "I am being written using Java with the Discord4J Libraries.");
        String message = response.toString();
        return message;
    }

    public String whatAreYouSail() {
        return "I am a bot fear me";
    }

    public String sailCompetition(String message) {
        File configDir = new File("competition");
        StringBuilder stringBuilder = new StringBuilder(message);
        stringBuilder.delete(0, 9);
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        File file = new File("competition/Entries.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.append(user.getName() + ", " + stringBuilder.toString()+ "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user.getName() + " thank you for entering the competition.";
    }

    public static String Help() {
        StringBuilder messageBuilder = new StringBuilder(message);
        StringBuilder commandBuilder = new StringBuilder();
        int location = 0;

        for (int i = 0;i < commands.size();i++){
            if (commands.get(i).getCommand().contains("help")){
                location = i;
                messageBuilder.delete(0, commands.get(i).getCommand().length());
            }
        }
        for (int i = 0; i < commands.size();i++){
            commandBuilder.append(commands.get(i).getCommand());
            commandBuilder.delete(0, commands.get(i).getPrefix().length());
            //return stringBuilder.toString();
            if (messageBuilder.toString().toLowerCase().contains(commandBuilder.toString())){
                return commands.get(i).getHelp();
            }
            commandBuilder.delete(0,commandBuilder.length());
        }
        if (message.length() == commands.get(location).getCommand().length()){
            return commands.get(location).getHelp();
        }else {
            return "Command does not exist";
        }
    }

    public static String nightlyFix() {
        return "Add this to your Starbound - Unstable/mods folder\n" +
                "http://community.playstarbound.com/resources/cheerful-giraffe-nightly-fix.3397/";
    }
}

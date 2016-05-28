import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.obj.IUser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Vaerys on 20/05/2016.
 */
public class GeneralCommands {

    Channel channel;
    IUser user;
    Guild guild;

    public GeneralCommands(Channel channel, IUser user, Guild guild) {
        this.channel = channel;
        this.user = user;
        this.guild = guild;
    }

    public String HelloSail(boolean isAdmin, boolean isMod, boolean isCF, boolean isOwner,boolean isCreator) {
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

    public String NightlyFAQ() {
        return "Here are the Frequently Asked Questions when it comes to the starbound nightly Build.\n" +
                "https://www.reddit.com/r/starbound/comments/4jnhjw/nightly_faq_so_you_want_to_play_nightly/#announce";
    }

    public String InfoSail(ArrayList<Command> commands) {
        StringBuilder responce = new StringBuilder();
        responce.append("Hello My Name is S.A.I.L\n" +
                "Here are the commands currently at my disposal:");
        for (int i = 0; i < commands.size(); i++) {
            responce.append("\n  " + commands.get(i).getCommand());
        } //add all of the commands to the list
        responce.append("\nMy Author is Dawn Felstar and I am currently In Development\n" +
                "I am being written using Java with the Discord4J Libraries.");
        String message = responce.toString();
        return message;
    }

    public String whatAreYouSail() {
        return "I am a bot i like to break on a regular basis and its all my fault.\n" +
                "Dawn is the best. I suck";
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

    public String Help(ArrayList<Command> commands, String message) {
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
}

import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.obj.IUser;

import java.util.ArrayList;

/**
 * Created by Vaerys on 25/06/2016.
 */
public class CustomCommands {

    ArrayList<String[]> commands = new ArrayList<String[]>();
    final String[] commandNotFound = {"noUser", "404", "No Command with that name found."};

    public String createCommand(String userID, String commandName, String response) {
        if (response.equals("")) {
            return "> You must specify a response\n`" + Globals.commandPrefix + "CCNew [Name] [Response]`.";
        }
        if (response.contains("<@!") || commandName.contains("<@!")) {
            return "> Please do not put mentions in custom commands.";
        }
        boolean noDuplicate = true;
        for (String[] sA : commands) {
            if (sA[1].equalsIgnoreCase(commandName)) {
                noDuplicate = false;
            }
        }
        if (noDuplicate) {
            String[] newEntry = new String[3];
            newEntry[0] = userID;
            newEntry[1] = commandName;
            newEntry[2] = response;
            commands.add(newEntry);
            return "> Command Added, you can perform you command by doing `" + Globals.CCPrefix + commandName + "`.";
        }
        return "> A Command with that name already exists, Cannot create command.";
    }

    public String getCommand(String message, IUser author, Guild guild) {
        StringBuilder CCName = new StringBuilder();
        String[] splitMessage = message.split(" ");
        CCName.append(splitMessage[0]);
        CCName.delete(0, Globals.CCPrefix.length());
        String regex;
        String args;
        StringBuilder builder = new StringBuilder();
        String[] CCResponse = commandNotFound;
        for (String[] cA : commands) {
            if (cA[1].equalsIgnoreCase(CCName.toString())) {
                CCResponse = cA;
            }
        }
        if (message.toString().length() != (CCResponse[1].length() + Globals.CCPrefix.length())) {
            builder.append(message.toString());
            builder.delete(0, CCResponse[1].length() + Globals.CCPrefix.length() + 1);
            args = builder.toString();
        } else {
            args = "Nothing";
        }
        regex = CCResponse[2];
        regex = regex.replaceAll("#author!#", author.getName());
        regex = regex.replaceAll("#author#", author.getDisplayName(guild));
        regex = regex.replaceAll("#args#", args);
        return regex;
    }

    public String getCreator(String args){
        String creator = "";
        boolean isfound = false;
        for (String[]sA: commands){
            if (args.equalsIgnoreCase(sA[1])){
                creator = sA[0];
                isfound = true;
            }
        }
        if (!isfound){
            return "-1";
        }
        if (creator.equalsIgnoreCase("LockedCommand")){
            return "locked";
        }
        return creator;
    }

    public String removeCommand(boolean isMod, String userID, String commandName) {
        for (String[] sA : commands) {
            if (sA[1].equalsIgnoreCase(commandName)) {
                if (sA[0].equalsIgnoreCase("LockedCommand")) {
                    return "> This command cannot be removed.";
                }
                if (isMod || sA[0].equals(userID)) {
                    commands.remove(sA);
                    return "> Command Removed";
                }
                return "> You Do not have Permission to Remove this command.";
            }
        }
        return "> A command with that name does not exist.";
    }

    public String listCommands(int args) {
        StringBuilder stringBuilder = new StringBuilder();
        int finalPage = 0;
        boolean counterComplete = false;
        int testCounter = 0;
        do{
            finalPage ++;
            if (commands.size() > testCounter && commands.size() < testCounter + 15){
                counterComplete = true;
            }
            testCounter = testCounter + 15;
        }while (!counterComplete);
        String header = "Here is Page `" + args + "/" + finalPage + "` of Custom Commands:\n`";
        stringBuilder.append(header);
        int count = args * 15;
        for (int i = count - 15; i < count && i< commands.size(); i++) {
            stringBuilder.append(Globals.CCPrefix + commands.get(i)[1] + ", ");
        }
        if (stringBuilder.toString().equals(header)){
            return "> That Page does not exist.";
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(".`");
        return stringBuilder.toString();
    }
}

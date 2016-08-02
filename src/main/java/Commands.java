import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.impl.obj.Message;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Vaerys on 19/05/2016.
 */
public class Commands {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(AnnotationListener.class);
    private FileHandler handler = new FileHandler();

    Guild guild;
    Channel channel;
    Message message;
    IUser author;
    String guildID;

    private GuildConfig guildConfig;
    private CustomCommands customCommands;
    private Characters characters;
    private String guildConfigFile;
    private String CCFile;
    private String charFile;

    boolean isOwner;
    boolean isAdmin;
    boolean isMod;
    boolean isCF;
    boolean isCreator;

    private String notAllowed;
    private String errorMessage;


    public Commands(Message message) {
        this.message = message;
        guild = (Guild) message.getGuild();
        channel = (Channel) message.getChannel();
        author = message.getAuthor();
        guildID = message.getGuild().getID();
        notAllowed = "I'm Sorry " + author.getName() + " I'm afraid I can't do that.";
        guildConfigFile = "GuildConfigs/" + guildID + "_config.json";
        CCFile = "CommandLists/" + guildID + "_CustomCommands.json";
        charFile = "Characters/" + guildID + "_CharList.json";
        errorMessage = "You have Found an error, please Mention this bot or " + guild.getUserByID(Globals.creatorID).getName() + " to let them know of this error";


        if (author.getID().equals(Globals.creatorID)) {
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
    }

    public void setPOGOS(GuildConfig guildConfig,CustomCommands customCommands, Characters characters) {
        this.guildConfig = guildConfig;
        this.customCommands = customCommands;
        this.characters = characters;
    }

    public void flushFiles() {
        if (guildConfig.equals(null)){
            logger.error("Config Object is empty stopping flush");
        }else{
            handler.writetoJson(guildConfigFile, guildConfig);
        }
        if (customCommands.equals(null)){
            logger.error("Commands Object is empty stopping flush");
        }else {
            handler.writetoJson(CCFile, customCommands);
        }
        if (characters.equals(null)){
            logger.error("Commands Object is empty stopping flush");
        }else {
            handler.writetoJson(charFile, characters);
        }
    }

    public String channelNotInit(String channelType) {
        return "The " + channelType + " Channel has not been set up yet please have an admin perform the command `" + Globals.commandPrefix + channelType + "Here` in the appropriate channel";
    }

    public String wrongChannel(String channelID){
        return "Command must be performed in " + guild.getChannelByID(channelID).toString();
    }

    private String getDescription(String name){
        try {
            CommandAnnotation Ca = this.getClass().getMethod(name).getAnnotation(CommandAnnotation.class);
            return Globals.commandPrefix + Ca.name() + "\n" + Ca.description() + "\nUsage: `" + getUsage(name)+ "`";
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "Failed to Find Method";
    }

    private String getName(String name){
        try {
            CommandAnnotation Ca = this.getClass().getMethod(name).getAnnotation(CommandAnnotation.class);
            return Globals.commandPrefix + Ca.name();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "Failed to Find Method";
    }

    private String getUsage(String name){
        try {
            CommandAnnotation Ca = this.getClass().getMethod(name).getAnnotation(CommandAnnotation.class);
            return getName(name) + " " + Ca.usage();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "Failed to Find Method";
    }

    private String helpHandler(String type){
        Method[] methods = this.getClass().getMethods();
        StringBuilder builder = new StringBuilder();
        builder.append("Here are all of the "+ WordUtils.capitalize(type) + "Commands:");
        for (Method m : methods) {
            if (m.isAnnotationPresent(CommandAnnotation.class)) {
                CommandAnnotation anno = m.getAnnotation(CommandAnnotation.class);
                if (anno.type().equalsIgnoreCase(type)) {
                    builder.append("\n   " + Globals.commandPrefix + anno.name());
                }
            }
        }
        return builder.toString();
    }

    @AliasAnnotation(alias = {"Hi", "Hello", "Greeting", "Hai", "Hoi"})
    @CommandAnnotation(name = "Hello", description = "Says Hello")
    public String helloSail() {
        if (isCreator) {
            return "> Hello Creator";
        } else if (isOwner) {
            return "> Hello Server Owner";
        } else if (isAdmin) {
            return "> Hello Admin";
        } else if (isMod) {
            return "> Hello Moderator";
        } else if (isCF) {
            return "> Hello Chucklefish Staff Member";
        } else {
            String[] lock;

            String roles = "nothing";
            ArrayList<IRole> rolelist = (ArrayList) author.getRolesForGuild(guild);
            for (IRole r : rolelist) {
                if (r.getName().equalsIgnoreCase("apex")) roles = r.getName();
                if (r.getName().equalsIgnoreCase("avian")) roles = r.getName();
                if (r.getName().equalsIgnoreCase("floran")) roles = r.getName();
                if (r.getName().equalsIgnoreCase("glitch")) roles = r.getName();
                if (r.getName().equalsIgnoreCase("human")) roles = r.getName();
                if (r.getName().equalsIgnoreCase("hylotl")) roles = r.getName();
                if (r.getName().equalsIgnoreCase("novakid")) roles = r.getName();
                if (r.getName().equalsIgnoreCase("penguin")) roles = r.getName();
                if (r.getName().equalsIgnoreCase("agaran")) roles = r.getName();
                if (r.getName().equalsIgnoreCase("avali")) roles = r.getName();
                if (r.getName().equalsIgnoreCase("felin")) roles = r.getName();
            }
            Random r = new Random();
            int randomInt = r.nextInt(10);
            if (message.toString().length() > 10) {
                lock = message.toString().split(" ");
                randomInt = Integer.parseInt(lock[1]);
            }
            if (randomInt == 0) {
                return "> Hello " + author.getName() + ", How was your day?";
            } else if (randomInt == 1) {
                return "> Hello... " + author.getName();
            } else if (randomInt == 2) {
                return "> Greetings " + author.getName() + ", I am S.A.I.L";
            } else if (randomInt == 3) {
                return "> Hi " + author.getName() + ", That's an interesting name.";
            } else if (randomInt == 4) {
                return "> Goodbye " + author.getName();
            } else if (randomInt == 5) {
                return "> 0100 1000 0110 0101 0110 1100 0110 1100 0110 1111 0010 1110";
            } else if (randomInt == 6) {
                return "> Initiating greeting protocol... Hello " + author.getName();
            } else if (randomInt == 7) {
                return "> ERROR: CANNOT PARSE COMMAND 'HELLO'";
            } else if (randomInt == 8) {
                return "> Glad to see you escaped the black hole, " + author.getName();
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("apex")) {
                return "> Hello there, " + author.getName() + " , Miniknog Smiles on you today.";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("avian")) {
                return "> My, my, " + author.getName() + " , your plumage is looking extraordinary today.";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("floran")) {
                return "> Greetingsssss " + author.getName() + ", Does Floran want to go huntingsss later?";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("glitch")) {
                return "> Happy. Hello " + author.getName() + ", You seem especially well polished today.";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("human")) {
                return "> Another day alive, congratulations " + author.getName();
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("hylotl")) {
                return "> Have a peaceful day " + author.getName() + " may you experience tranquility.";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("novakid")) {
                return "> "+ author.getName() + "! the Sheriff has been shot, I need your... oooo~ whats that?";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("penguin")) {
                return "> Dreadwing will not find you here " + author.getName() + " you are safe.";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("agaran")) {
                return "> Hoom Shroom Nab Nab Agaran " + author.getName() + " Click Clack Flap Flap.";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("avali")) {
                return "> Did you get the get the new message from the Local network " + author.getName() + "?";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("felin")) {
                return "> All we are is cats in the wind " + author.getName();
            } else if ((randomInt == 9)) {
                return "> Ello " + author.getName() + "Ain't Seen you around much";
            } else {
                return "> How did you get this message " + author.getName();
            }
        }
    }

    @CommandAnnotation(name = "GeneralHere",type = Globals.typeAdmin,description = "Sets the current Channel as the Server's 'General' Channel")
    public String setGeneral() {
        if (isAdmin || isOwner) {
            guildConfig.setGeneralChannel(channel.getID());
            return "> This Channel is now the Server's 'General' Channel.";
        } else {
            return notAllowed;
        }
    }

    @CommandAnnotation(name = "ServersHere", type = Globals.typeAdmin,description = "Sets the current Channel as the Server's 'Servers' Channel")
    public String setServersChannel() {
        if (isAdmin || isOwner) {
            guildConfig.setServersChannel(channel.getID());
            return "> This Channel is now the Server's 'Servers' Channel.";
        } else {
            return notAllowed;
        }
    }

    @CommandAnnotation(name = "RaceSelectHere", type = Globals.typeAdmin, description = "Sets the current Channel as the Server's 'Race Select' Channel")
    public String setRaceSelect() {
        if (isAdmin || isOwner) {
            guildConfig.setRaceSelectChannel(channel.getID());
            return "> This Channel is now the Server's 'Race Select' Channel.";
        } else {
            return notAllowed;
        }
    }

    @CommandAnnotation(name = "Help", description = "Lists all of the Commands Sail can run",usage = "[Type]")
    public String sailHelp() {
        Method[] methods = this.getClass().getMethods();
        String[] splitMessage = message.toString().split(" ");
        StringBuilder commandList = new StringBuilder();
        ArrayList<String> types = new ArrayList<>();
        if (message.toString().length() == getName("sailHelp").length()){
            for (Method m : methods) {
                if (m.isAnnotationPresent(CommandAnnotation.class)) {
                    boolean typeFound = false;
                    CommandAnnotation anno = m.getAnnotation(CommandAnnotation.class);
                    for (String s : types){
                        if (s.equalsIgnoreCase(anno.type())){
                            typeFound = true;
                        }
                    }
                    if (!typeFound){
                        types.add(anno.type());
                    }
                }
            }
            commandList.append("Here are the command types you can search from:");
            for (String s : types){
                commandList.append("\n   " + s);
            }
            commandList.append("\nYou can search for commands with those types by doing \n`"+ getUsage("sailHelp") +"`");
            commandList.append("\nGitHub Page: <https://github.com/Vaerys-Dawn/S.A.I.L>");
        } else if (splitMessage[1].equalsIgnoreCase("Admin")){
            if (isMod || isAdmin) {
                commandList.append(helpHandler("Admin"));
            }else {
                return notAllowed;
            }
        } else if (splitMessage[1].equalsIgnoreCase(Globals.typeRace)){
            commandList.append(helpHandler(Globals.typeRace));
        } else if (splitMessage[1].equalsIgnoreCase(Globals.typeServers)){
            commandList.append(helpHandler(Globals.typeServers));
        } else if (splitMessage[1].equalsIgnoreCase(Globals.typeGeneral)){
            commandList.append(helpHandler(Globals.typeGeneral));
        } else if (splitMessage[1].equalsIgnoreCase(Globals.typeCC)){
            commandList.append(helpHandler(Globals.typeCC));
        } else {
            return getDescription("sailHelp");
        }
        return commandList.toString();
    }

    @CommandAnnotation(name = "Info", description = "Gives information about a specific command",usage = "[Command]")
    public String sailInfo() {
        Method[] methods = this.getClass().getMethods();
        String buildMessage = message.toString();
        if (message.toString().toLowerCase().equals("sail.info")) {
            return getDescription("sailInfo");
        }
        String[] splitMessage = buildMessage.split(" ");
        for (Method m : methods) {
            if (m.isAnnotationPresent(CommandAnnotation.class) && !splitMessage[1].equals("")) {
                CommandAnnotation commandAnno = m.getAnnotation(CommandAnnotation.class);
                String testMessage = splitMessage[1].toLowerCase();
                String testTo = commandAnno.name().toLowerCase();
                if ((testMessage.startsWith(testTo)) && (testMessage.length() == testTo.length())) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(getDescription(m.getName()));
                    if (m.isAnnotationPresent(AliasAnnotation.class)){
                        AliasAnnotation aliasAnno = m.getAnnotation(AliasAnnotation.class);
                        String[] alias = aliasAnno.alias();
                        builder.append("\nAliases: ");
                        for (int i = 0; i < alias.length;i++){
                            builder.append(alias[i] + ", ");
                        }
                    }
                    return builder.toString();
                }
            }
        }
        return "> That command does not exist.";
    }

    @CommandAnnotation(name = "doLoginMessage",type = Globals.typeAdmin, description = "Toggles the login mesaage")
    public String setLoginMessage() {
        if (isAdmin || isOwner) {
            if (guildConfig.getDoLoginMessage()) {
                guildConfig.setDoLoginMessage(false);
            } else {
                guildConfig.setDoLoginMessage(true);
            }
            return "> Toggled the Login Message";
        } else {
            return notAllowed;
        }
    }

    @AliasAnnotation(alias = {"ListRaces","Races","RaceList"})
    @CommandAnnotation(name = "ListRaces",type = "Race", channel = Globals.channelRaceSelect, description = "Lists the Available races that you can choose from")
    public String listRaces() {
        StringBuilder response = new StringBuilder();
        ArrayList<String> races = guildConfig.getRaces();
        response.append("> Here are the Races you can choose from:\n`");
        for (String s : races) {
            response.append(guild.getRoleByID(s).getName() + ", ");
        }
        response.append("`");
        return response.toString();
    }

    @CommandAnnotation(name = "AddRace",type = Globals.typeAdmin,channel = Globals.channelRaceSelect, description = "Adds role to selectable races",usage = "[Role name]")
    public String addRace() {
        if (isAdmin || isOwner || isMod) {
            String[] testMessage = message.toString().split(" ");
            if (message.toString().length() == getName("addRace").length() || testMessage[1].equals("")) {
                return "> Could not add race because you did not tell me which one you wanted to add. I'm a bot not a wizard.\n" + getUsage("addRace");
            }
            ArrayList<IRole> roles = (ArrayList) guild.getRoles();
            String raceID;
            for (IRole r : roles) {
                if (r.getName().toLowerCase().equals(testMessage[1].toLowerCase()) && !testMessage[1].equals("")) {
                    raceID = r.getID();
                    guildConfig.addRace(raceID);
                    return "> Race added.";
                }
            }
            return "> role not found";
        } else {
            return notAllowed;
        }
    }

    @CommandAnnotation(name = "RemoveRace",type = Globals.typeAdmin, channel = Globals.channelRaceSelect, description = "Removes the role from the selectable races",usage = "[Role name]")
    public String removeRace() {
        if (isAdmin || isOwner || isMod) {
            String[] testMessage = message.toString().split(" ");
            if (message.toString().length() == getName("removeRace").length() || testMessage[1].equals("")) {
                return "> ERROR: USER SPECIFIED NOTHING AS A PARAMETER, CANNOT REMOVE NOTHING FROM RACE LIST.\n" + getUsage("removeRace");
            }
            ArrayList<IRole> roles = (ArrayList) guild.getRoles();
            String raceID;
            for (IRole r : roles) {
                if (r.getName().toLowerCase().equals(testMessage[1].toLowerCase()) && !testMessage[1].equals("")) {
                    raceID = r.getName();
                    guildConfig.removeRace(raceID);
                    return "> Race removed.";
                }
            }
            return "> race not found";
        } else {
            return notAllowed;
        }
    }

    @AliasAnnotation(alias = {"Race","Iam","Role"})
    @CommandAnnotation(name = "Race",type = "Race", channel = Globals.channelRaceSelect, description = "Gives you a race.",usage = "[Race]")
    public String race() {
        List<IRole> roles = author.getRolesForGuild(guild);
        ArrayList<String> races = guildConfig.getRaces();
        List<IRole> guildRoles = guild.getRoles();
        String response = "";
        String newRace = "";
        for (int i = 0; i < roles.size(); i++) {
            for (String r : races) {
                if (roles.get(i).equals(guild.getRoleByID(r))) {
                    roles.remove(i);
                }
            }
        }
        boolean racefound = false;
        String[] newRole = message.toString().split(" ");

        if (message.toString().length() == getName("race").length()){
            return getDescription("race");
        }
        if (newRole[1].equalsIgnoreCase("remove")) {
            racefound = true;
            response = "> Your race was removed, Congrats Normie.";
        }
        for (String r : races) {
            if (newRole[1].equalsIgnoreCase(guild.getRoleByID(r).getName())) {
                roles.add(guild.getRoleByID(r));
                racefound = true;
                newRace = guild.getRoleByID(r).getName();
            }
        }
        IRole[] newRoleList;
        newRoleList = roles.stream().toArray(IRole[]::new);
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(message.toString());
            stringBuilder.delete(0, newRole[0].length());
            if (racefound) {
                guild.editUserRoles(author, newRoleList);
                if (response.equals("")) {
                    return "> You have selected race: **" + newRace + "**.";
                }
            } else {
                response = "> You cannot have the race:" + stringBuilder.toString() + " as that race does not exist";
            }
            return response;
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }
        return "> Error 502, Try again.";
    }

    @CommandAnnotation(name = "AddServer",type = Globals.typeServers, channel = Globals.channelServers, description = "Adds a sever to the server list.",usage = "[ServerName]")
    public String addServer() {
        String[] testMessage = message.toString().split(" ");
        boolean isUsed = false;
        if (testMessage.length == 2) {
            for (String[] sa : guildConfig.getServerList()) {
                if (sa[1].equalsIgnoreCase(testMessage[1])) {
                    isUsed = true;
                }
            }
            if (!isUsed) {
                guildConfig.addServer(author.getID(), testMessage[1]);
                return "> Server Added";
            } else {
                return "> Server name has already been used";
            }
        } else if (testMessage.length == 1) {
            return "> You must Specify a Server Name\n" + getUsage("addServer");
        } else {
            return "> Server name cannot have spaces";
        }
    }

    @AliasAnnotation(alias = {"ListServers","Servers","ServerList"})
    @CommandAnnotation(name = "ListServers",type = Globals.typeServers, channel = Globals.channelServers, description = "Lists all of the servers")
    public String listServers() {
        StringBuilder response = new StringBuilder();
        response.append("Here are the Servers Currently Saved to my list\n");
        for (String[] sa : guildConfig.getServerList()) {
            response.append("   " + sa[1] + "\n");
        }
        response.append("You can get the server information of each server by performing\n`" + getUsage("serverInfo") + "`");
        return response.toString();
    }

    @CommandAnnotation(name = "Server",type = Globals.typeServers, channel = Globals.channelServers, description = "Gives the server's Details",usage = "[ServerName]")
    public String serverInfo() {
        String[] testMessage = message.toString().split(" ");
        StringBuilder response = new StringBuilder();
        response.append("Server does not exist");
        if (message.toString().length() == getName("serverInfo").length()){
            return getDescription("serverInfo");
        }
        for (String[] sa : guildConfig.getServerList()) {
            if (testMessage[1].equalsIgnoreCase(sa[1])) {
                response.delete(0, response.length());
                response.append(sa[1]);
                response.append("\n**Listing Creator:** " + guild.getUserByID(sa[0]).getName());
                response.append("\n**Server IP:** " + sa[2] + " **Port:** " + sa[3]);
                response.append("\n" + sa[4]);
            }
        }
        return response.toString();
    }

    @CommandAnnotation(name = "EditServer",type = Globals.typeServers, channel = Globals.channelServers, description = "Edits the server",usage = "[ServerName] Ip, Port, or Desc")
    public String editServer() {
        String[] testMessage = message.toString().split(" ");
        if (guildConfig.getEditingServer()) {
            if (guildConfig.getServerEditor().equals(author.getID())) {
                for (String[] sa : guildConfig.getServerList()) {
                    if (sa[1].equalsIgnoreCase(guildConfig.getServerToEdit())) {
                        if (guildConfig.getServerEditingType().equalsIgnoreCase("IP")) {
                            sa[2] = testMessage[1];
                            stopServerEdit();
                            return "> IP Edited";
                        } else if (guildConfig.getServerEditingType().equalsIgnoreCase("Port")) {
                            sa[3] = testMessage[1];
                            stopServerEdit();
                            return "> Port Edited";
                        } else if (guildConfig.getServerEditingType().equalsIgnoreCase("Desc")) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(message.toString());
                            stringBuilder.delete(0, testMessage[0].length() + 1);
                            sa[4] = stringBuilder.toString();
                            stopServerEdit();
                            return "> Description Edited";
                        } else {
                            stopServerEdit();
                            return errorMessage;
                        }
                    }
                }
                stopServerEdit();
            } else {
                return "> A Server Listing is currently being edited.\n> An admin or Moderator will have to perform  "+ Globals.commandPrefix + "AbandonEdit` to allow for another edit.";
            }
        } else {
            if (testMessage.length == 3) {
                for (String[] sa : guildConfig.getServerList()) {
                    if (testMessage[1].equalsIgnoreCase(sa[1])) {
                        if (author.getID().equals(sa[0]) || isOwner || isAdmin) {
                            if (testMessage[2].equalsIgnoreCase("desc") || testMessage[2].equalsIgnoreCase("IP") || testMessage[2].equalsIgnoreCase("port")) {
                                guildConfig.setEditingServer(true);
                                guildConfig.setServerEditor(author.getID());
                                guildConfig.setServerEditingType(testMessage[2]);
                                guildConfig.setServerToEdit(testMessage[1]);
                                return "You are now Editing the Server " + testMessage[2] + "\nPerform `" + getName("editServer") + " [Contents]` to change the server properties,\nOr  "+ Globals.commandPrefix + "AbandonEdit` to cancel editing\n" +
                                        "❗*IMPORTANT: Please Surround links with* `<>`❗";
                            } else {
                                return "Cannot edit " + testMessage[2];
                            }
                        } else {
                            return "> You do not have permission to edit this server listing.";
                        }
                    }
                }
                return "> server does not exist";
            } else if (testMessage.length == 2) {
                return "> You must specify a channel, this channel is either IP, Port or Desc";
            } else if (testMessage.length == 1) {
                return "> You must specify a server to edit\n" + getUsage("editServer");
            } else {
                return "> Too many or too few arguments\n" + getUsage("editServer");
            }
        }
        return errorMessage;
    }

    @CommandAnnotation(name = "AbandonEdit",type = Globals.typeServers, channel = Globals.channelServers, description = "Stops the Server Listing Editing process")
    public String stopServerEdit() {
        if (isAdmin || isMod || isOwner || author.equals(guild.getUserByID(guildConfig.getServerEditor()))) {
            guildConfig.setEditingServer(false);
            guildConfig.setServerEditor("");
            guildConfig.setServerEditingType("");
            guildConfig.setServerToEdit("");
            return "> Stopped the Server Listing Editing process";
        } else {
            return notAllowed;
        }
    }

    @AliasAnnotation(alias = {"Wiki", "Starbounder", "SBWIKI","Search"})
    @CommandAnnotation(name = "Wiki", description = "Links a Page From Starbounder based on your message",usage = "[Search]")
    public String sailWiki(){
        if (message.toString().length() == getName("sailWiki").length()){
            return getUsage("sailWiki");
        }
        String[] splitMessage = message.toString().split(" ");
        StringBuilder response = new StringBuilder();
        StringBuilder newMessage = new StringBuilder();
        newMessage.append(message.toString());
        newMessage.delete(0,splitMessage[0].length() + 1);
        String regexedMessage = WordUtils.capitalize(newMessage.toString());
        regexedMessage = regexedMessage.replaceAll(" ", "_");
        response.append("> Here is Your Search\n<http://starbounder.org/Special:Search/");
        response.append(regexedMessage);
        response.append(">");
        return response.toString();
    }

    @AliasAnnotation(alias = {"NewCC","CCnew","CCmake","CreateCC","MakeCC"})
    @CommandAnnotation(name = "NewCC",type = Globals.typeCC, description = "Creates a custom command",usage = "[CommandName] [Message]")
    public String newCC(){
        if(message.toString().length() == getName("newCC").length()){
            return getUsage("newCC");
        }
        String[] splitString = message.toString().split(" ");
        StringBuilder command = new StringBuilder();
        command.append(message.toString());
        command.delete(0, (splitString[0].length() + splitString[1].length() + 2));
        return customCommands.createCommand(author.getID(),splitString[1],command.toString());
    }

    @AliasAnnotation(alias = {"DelCC", "CCDel","RemoveCC"})
    @CommandAnnotation(name = "DelCC",type = Globals.typeCC, description = "Removes the Command",usage = "[CommandName]")
    public String delCC(){
        if (message.toString().length() == getName("delCC").length()){
            return getUsage("delCC");
        }
        String[] splitString = message.toString().split(" ");
        return customCommands.removeCommand(isMod,author.getID(),splitString[1]);
    }

    @AliasAnnotation(alias = {"CClist","ListCCs"})
    @CommandAnnotation(name = "CCList",type = Globals.typeCC, description = "Lists the Server's Custom Commands", usage = "[Page number]")
    public String listCCs(){
        int args = 1;
        boolean isfound = false;
        String[] splitString = message.toString().split(" ");
        if ((splitString.length == 1) || splitString[1].equals("")){
            args = 1;
        }else{
            char[] chars = splitString[1].toCharArray();
            for (char c: chars){
                if (Character.isLetter(c)){
                    isfound = true;
                }
            }
            if (!isfound){
                args = Integer.parseInt(splitString[1]);
            }
        }
        if(isfound){
            return "> what are you doing, why are you trying to search for the " + splitString[1] + " page... \n" +
                    "> pretty sure you cant do that...";
        }else {
            return customCommands.listCommands(args);
        }
    }

    @CommandAnnotation(name = "WhoMadeCC", type = Globals.typeCC, description = "Tells you who made a command.", usage = "[Command]")
    public String whoDunit(){
        String[] splitString = message.toString().split(" ");
        if ((splitString.length == 1) || splitString[1].equals("")){
            return "> Missing Args [Command]";
        }
        String creator = customCommands.getCreator(splitString[1]);
        if (creator.equals("-1")){
            return "> Command not found";
        }
        if (creator.equals("locked")){
            return "> An Admin.";
        }
        return "> " + guild.getUserByID(creator).getDisplayName(guild);
    }

    @CommandAnnotation(name = "CCTags",type = Globals.typeCC, description = "List all of the tags available to use in a custom command")
    public String tagsCC(){
        return "You can add any of the following tags to a Custom command to \n" +
                "get a special responce:\n" +
                " #author# - replaces with the senders nickname\n" +
                " #author!# - replaces with the senders username\n" +
                " #args# - replaces with any text after the command";
    }

    @CommandAnnotation(name =  "isStreaming",type = Globals.typeAdmin,description = "Sets The Bot To streaming Mode",usage = "[StreamLink] [Message]", responseGeneral = true)
    public String isStreaming(){
        if (isAdmin || isMod || isOwner) {
            String streamLink;
            String[] splitMessage = message.toString().split(" ");
            StringBuilder builder = new StringBuilder();
            if (message.toString().length() == getName("isStreaming").length()) {
                message.getClient().changeStatus(Status.game("Starbound"));
                return "> Stream has stopped thank you all for coming";
            } else streamLink = splitMessage[1];
            builder.append(message.toString());
            builder.delete(0, splitMessage[0].length() + splitMessage[1].length() + 2);
            message.getClient().changeStatus(Status.stream(builder.toString(), splitMessage[1]));
            return "> Check out the stream at: " + streamLink;
        }
        return notAllowed;
    }

    @CommandAnnotation(name = "UpdateInfo",type = Globals.typeAdmin,description = "Posts the info channel contents")
    public String Thinger(){
        if (isOwner){
            InfoChannel infoChannel = new InfoChannel();
            infoChannel.updateInfo(channel,guild);
            return "";
        }else return notAllowed;
    }

    @CommandAnnotation(name = "SaveChar", channel = Globals.channelRaceSelect,description = "Saves character settings to your current user settings",usage = "[Character Name]")
    public String saveCharacter(){
        String[] splitString = message.toString().split(" ");
        if ((splitString.length == 1) || splitString[1].equals("")){
            return "> Missing Args [Character Name]";
        }
        return characters.saveChar(author,guild,splitString[1]);
    }

    @CommandAnnotation(name = "DelChar", channel = Globals.channelRaceSelect, description = "Deleted the selected character", usage = "[Character name]")
    public String deleteCharacter(){
        String[] splitString = message.toString().split(" ");
        if ((splitString.length == 1) || splitString[1].equals("")){
            return "> Missing Args [Character Name]";
        }
        return characters.delChar(author,splitString[1]);
    }

    @CommandAnnotation(name = "Char",channel = Globals.channelRaceSelect,description = "Sets you user settings to the character's Settings", usage = "[Character name]")
    public String selectcharacter(){
        String[] splitString = message.toString().split(" ");
        if ((splitString.length == 1) || splitString[1].equals("")){
            return "> Missing Args [Character Name]";
        }
        return characters.selChar(author,guild,splitString[1]);
    }
}


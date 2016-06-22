import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.impl.obj.Message;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
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

    String guildConfigFile;
    Guild guild;
    Channel channel;
    Message message;
    IUser author;
    String guildID;
    private GuildConfig guildConfig;
    FileHandler handler = new FileHandler();
    private boolean isOwner;
    private boolean isAdmin;
    private boolean isMod;
    private boolean isCF;
    private boolean isCreator;
    String notAllowed;
    String errorMessage;

    public Commands(Message message) {
        this.message = message;
        guild = (Guild) message.getGuild();
        channel = (Channel) message.getChannel();
        author = message.getAuthor();
        guildID = message.getGuild().getID();
        notAllowed = "I'm Sorry " + author.getName() + " I'm afraid I can't do that.";
        guildConfigFile = "GuildConfigs/" + guildID + "_config.json";
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

    public void setPOGOS(GuildConfig guildConfig) {
        this.guildConfig = guildConfig;
    }

    public void flushFiles() {
        handler.writetoJson(guildConfigFile, guildConfig);
    }

    public String channelNotInit(String channelType) {
        return "The " + channelType + " Channel has not been set up yet please have an admin perform the command `" + Globals.commandPrefix + channelType + "Here` in the appropriate channel";
    }

    public String wrongChannel(String channelID){
        return "Command must be performed in " + guild.getChannelByID(channelID).toString();
    }

    @AliasAnnotation(alias = {"Hi", "Hello", "Greeting", "Hai", "HiHi"})
    @CommandAnnotation(name = "Hello", description = "Says Hello")
    public String HelloSail() {
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
                return "Hello " + author.getName() + ", How was your day?";
            } else if (randomInt == 1) {
                return "Hello... " + author.getName();
            } else if (randomInt == 2) {
                return "Greetings " + author.getName() + ", I am S.A.I.L";
            } else if (randomInt == 3) {
                return "Hi " + author.getName() + ", That's an interesting name.";
            } else if (randomInt == 4) {
                return "Goodbye " + author.getName();
            } else if (randomInt == 5) {
                return "0100 1000 0110 0101 0110 1100 0110 1100 0110 1111 0010 1110";
            } else if (randomInt == 6) {
                return "Initiating greeting protocol... Hello " + author.getName();
            } else if (randomInt == 7) {
                return "ERROR: CANNOT PARSE COMMAND 'HELLO'";
            } else if (randomInt == 8) {
                return "Glad to see you escaped the black hole, " + author.getName();
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("apex")) {
                return "Hello there, " + author.getName() + " , Miniknog Smiles on you today.";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("avian")) {
                return "My, my, " + author.getName() + " , your plumage is looking extraordinary today.";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("floran")) {
                return "Greetingsssss " + author.getName() + ", Does Floran want to go huntingsss later?";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("glitch")) {
                return "Happy. Hello " + author.getName() + ", You seem especially well polished today.";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("human")) {
                return "Another day alive, congratulations " + author.getName();
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("hylotl")) {
                return "Have a Peaceful day " + author.getName() + " May you find tranquility.";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("novakid")) {
                return author.getName() + "! the Sheriff has been shot, I need your... oooo~ whats that?";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("penguin")) {
                return "Dreadwing will not find you here " + author.getName() + " you are safe.";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("agaran")) {
                return "Hoom Shroom Nab Nab Agaran " + author.getName() + " Click Clack Flap Flap.";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("avali")) {
                return "Did you get the get the new message from the Local network " + author.getName() + "?";
            } else if ((randomInt == 9) && roles.equalsIgnoreCase("felin")) {
                return "All we are is cats in the wind " + author.getName();
            } else if ((randomInt == 9)) {
                return "Ello " + author.getName() + "Ain't Seen you around much";
            } else {
                return "How did you get this message " + author.getName();
            }
        }
    }

    @AliasAnnotation(alias = {"Bye", "Goodbye", "Boi", "Bai"})
    @CommandAnnotation(name = "Goodbye", description = "Says Goodbye")
    public String goodbye() {
        return "Goodbye " + author.getName();
    }

    @CommandAnnotation(name = "GoodNight", description = "Says Goodbye")
    public String goodNight() {
        return "Goodnight " + author.getName();
    }

    @CommandAnnotation(name = "NightlyFAQ", description = "Posts a link to the nightly FAQ post on Reddit.")
    public String NightlyFAQ() {
        return "Here are the Frequently Asked Questions when it comes to the starbound nightly Build.\n" +
                "https://www.reddit.com/r/starbound/comments/4jnhjw/nightly_faq_so_you_want_to_play_nightly/#announce";
    }

    @CommandAnnotation(name = "NightlyFix", description = "Posts a link to the Nightly Fix mod")
    public String nightlyFix() {
        return "Add this to your Starbound - Unstable/mods folder\n" +
                "http://community.playstarbound.com/resources/cheerful-giraffe-nightly-fix.3397/";
    }

    @CommandAnnotation(name = "SetGeneral", description = "Sets the current Channel as the Server's 'General' Channel")
    public String setGeneral() {
        if (isAdmin || isOwner) {
            guildConfig.setGeneralChannel(channel.getID());
            return "This Channel is now the Server's 'General' Channel.";
        } else {
            return notAllowed;
        }
    }

    @CommandAnnotation(name = "ServersHere", description = "Sets the current Channel as the Server's 'Servers' Channel")
    public String setServersChannel() {
        if (isAdmin || isOwner) {
            guildConfig.setServersChannel(channel.getID());
            return "This Channel is now the Server's 'Servers' Channel.";
        } else {
            return notAllowed;
        }
    }

    @CommandAnnotation(name = "RaceSelectHere", description = "Sets the current Channel as the Server's 'Race Select' Channel")
    public String setRaceSelect() {
        if (isAdmin || isOwner) {
            guildConfig.setRaceSelectChannel(channel.getID());
            return "This Channel is now the Server's 'Race Select' Channel.";
        } else {
            return notAllowed;
        }
    }

    @CommandAnnotation(name = "Help", description = "Lists all of the Commands Sail can run")
    public String sailHelp() {
        Method[] methods = this.getClass().getMethods();
        StringBuilder commandList = new StringBuilder();
        commandList.append("Hello My Name is S.A.I.L\n" +
                "Here are the commands currently at my disposal:");
        for (Method m : methods) {
            if (m.isAnnotationPresent(CommandAnnotation.class)) {
                CommandAnnotation anno = m.getAnnotation(CommandAnnotation.class);
                commandList.append("\n   " + Globals.commandPrefix + anno.name());
            }
        }
        commandList.append("\nMy Author is Dawn Felstar and I am currently In Development\n" +
                "I am being written using Java with the Discord4J Libraries.\n" +
                "My avatar was created by HadronKalido,\n" +
                "if you have any feedback or issues send a mention at me to let my creator know.");
        return commandList.toString();
    }

    @CommandAnnotation(name = "Info", description = "Gives information about a specific command\nUsage: Sail.Info [Command]")
    public String sailInfo() {
        Method[] methods = this.getClass().getMethods();
        String buildMessage = message.toString();
        if (message.toString().toLowerCase().equals("sail.info")) {
            return "Sail.Info\nGives information about a specific command\nUsage: Sail.Info [Command]";
        }
        String[] splitMessage = buildMessage.split(" ");
        for (Method m : methods) {
            if (m.isAnnotationPresent(CommandAnnotation.class) && !splitMessage[1].equals("")) {
                CommandAnnotation commandAnno = m.getAnnotation(CommandAnnotation.class);
                String testMessage = splitMessage[1].toLowerCase();
                String testTo = commandAnno.name().toLowerCase();
                if ((testMessage.startsWith(testTo)) && (testMessage.length() == testTo.length())) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(Globals.commandPrefix + commandAnno.name() + "\n" + commandAnno.description() + "\n");
                    if (m.isAnnotationPresent(AliasAnnotation.class)){
                        AliasAnnotation aliasAnno = m.getAnnotation(AliasAnnotation.class);
                        String[] alias = aliasAnno.alias();
                        builder.append("Aliases: ");
                        for (int i = 0; i < alias.length;i++){
                            builder.append(alias[i] + ", ");
                        }
                    }
                    return builder.toString();
                }
            }
        }
        return "That command does not exist.";
    }

    @CommandAnnotation(name = "doLoginMessage", description = "Toggles the login mesaage")
    public String setLoginMessage() {
        if (isAdmin || isOwner) {
            if (guildConfig.getDoLoginMessage()) {
                guildConfig.setDoLoginMessage(false);
            } else {
                guildConfig.setDoLoginMessage(true);
            }
            return "Toggled the Login Message";
        } else {
            return notAllowed;
        }
    }

    @CommandAnnotation(name = "reboot", description = "sends a custom message")
    public String newMessage() {
        if (isOwner) {
            String newMessage = "Hello @everyone I am S.A.I.L, I am your Ship-based Artificial Intelligence Lattice," +
                    " I am now back up and running and ready to go. I currently have a small selection of commands that I can run, " +
                    "You can see these commands by running 'Sail.Help'.\n\nWith my new reboot I can now have commands created for me" +
                    " at a very fast rate, so if you would like to see a new command mention me with your ideas. I am still going through upgrades and soon" +
                    " I should be able to show a server list, change your race for you, upgrade your matter manipulator, and allow you to create custom commands." +
                    "/nLastly thank you HadronKalido for my new coat of pixels.";
            return newMessage;
        } else {
            return notAllowed;
        }
    }

    @CommandAnnotation(name = "ListRaces", channel = "RaceSelect", description = "Lists the Available races that you can choose from")
    public String listRaces() {
        StringBuilder response = new StringBuilder();
        ArrayList<String> races = guildConfig.getRaces();
        response.append("Here are the Races you can choose from:\n");
        for (String s : races) {
            response.append(guild.getRoleByID(s).getName() + ", ");
        }
        return response.toString();
    }

    @CommandAnnotation(name = "AddRace", channel = "RaceSelect", description = "Adds role to selectable races\nUsage: Sail.AddRace [Role name]")
    public String addRace() {
        if (isAdmin || isOwner || isMod) {
            String[] testMessage = message.toString().split(" ");
            if (message.toString().equalsIgnoreCase("Sail.AddRace") || testMessage[1].equals("")) {
                return "Could not add race because you did not tell me which one you wanted to add. I'm a bot not a wizard.\nUsage Sail.AddRace [role]";
            }
            ArrayList<IRole> roles = (ArrayList) guild.getRoles();
            String raceID;
            for (IRole r : roles) {
                if (r.getName().toLowerCase().equals(testMessage[1].toLowerCase()) && !testMessage[1].equals("")) {
                    raceID = r.getID();
                    guildConfig.addRace(raceID);
                    return "Race added.";
                }
            }
            return "role not found";
        } else {
            return notAllowed;
        }
    }

    @CommandAnnotation(name = "RemoveRace", channel = "RaceSelect", description = "Removes the role from the selectable races\nUsage: Sail.AddRace [Role name]")
    public String removeRace() {
        if (isAdmin || isOwner || isMod) {
            String[] testMessage = message.toString().split(" ");
            if (message.toString().equalsIgnoreCase("Sail.RemoveRace") || testMessage[1].equals("")) {
                return "ERROR: USER SPECIFIED NOTHING AS A PARAMETER, CANNOT REMOVE NOTHING FROM RACE LIST.\nUsage Sail.AddRace [role]";
            }
            ArrayList<IRole> roles = (ArrayList) guild.getRoles();
            String raceID;
            for (IRole r : roles) {
                if (r.getName().toLowerCase().equals(testMessage[1].toLowerCase()) && !testMessage[1].equals("")) {
                    raceID = r.getName();
                    guildConfig.removeRace(raceID);
                    return "Race removed.";
                }
            }
            return "race not found";
        } else {
            return notAllowed;
        }
    }

    @CommandAnnotation(name = "Race", channel = "RaceSelect", description = "Gives you a race.\nUsage: Sail.Race [Race]")
    public String race() {
        List<IRole> roles = author.getRolesForGuild(guild);
        ArrayList<String> races = guildConfig.getRaces();
        List<IRole> guildRoles = guild.getRoles();
        String response = "";
        for (int i = 0; i < roles.size(); i++) {
            for (String r : races) {
                if (roles.get(i).equals(guild.getRoleByID(r))) {
                    roles.remove(i);
                }
            }
        }
        boolean racefound = false;
        String[] newRole = message.toString().split(" ");

        if (newRole[1].equalsIgnoreCase("remove")) {
            racefound = true;
            response = "Your race was removed";
        }
        for (String r : races) {
            if (newRole[1].equalsIgnoreCase(guild.getRoleByID(r).getName())) {
                roles.add(guild.getRoleByID(r));
                racefound = true;
            }
        }
        IRole[] newRoleList;
        newRoleList = roles.stream().toArray(IRole[]::new);
        try {
            if (racefound) {
                guild.editUserRoles(author, newRoleList);
                if (response.equals("")) {
                    return "Your race has been updated";
                }
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(message.toString());
                stringBuilder.delete(0, newRole[0].length());
                response = "You cannot have the race:" + stringBuilder.toString() + " as that role does not exist";
            }
            return response;
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }
        return "failed to update race, if you are an admin S.A.I.L Cannot change your race, but you can do it manually, you lazy person.";
    }

    @CommandAnnotation(name = "AddServer", channel = "Servers", description = "Adds a sever to the server list.\nUsage: Sail.AddServer [ServerName]")
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
                return "Server Added";
            } else {
                return "Server name has already been used";
            }
        } else if (testMessage.length == 1) {
            return "You must Specify a Server Name\nSail.AddServer [ServerName]";
        } else {
            return "Server name cannot have spaces";
        }
    }

    @CommandAnnotation(name = "ListServers", channel = "Servers", description = "Lists all of the servers")
    public String listServers() {
        StringBuilder response = new StringBuilder();
        response.append("Here are the Servers Currently Saved to my list\n");
        for (String[] sa : guildConfig.getServerList()) {
            response.append("  " + sa[1] + "\n");
        }
        response.append("You can get the server information of each server by performing\n" +
                "Sail.Server [ServerName]");
        return response.toString();
    }

    @CommandAnnotation(name = "Server", channel = "Servers", description = "Gives the server's Details\nUsage: Sail.Server [ServerName]")
    public String serverInfo() {
        String[] testMessage = message.toString().split(" ");
        StringBuilder response = new StringBuilder();
        response.append("Server does not exist");
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

    @CommandAnnotation(name = "EditServer", channel = "Servers", description = "Edits the server\nUsage: Sail.EditServer [ServerName] Ip, Port, or Desc")
    public String editServer() {
        String[] testMessage = message.toString().split(" ");
        if (guildConfig.getEditingServer()) {
            if (guildConfig.getServerEditor().equals(author.getID())) {
                for (String[] sa : guildConfig.getServerList()) {
                    if (sa[1].equalsIgnoreCase(guildConfig.getServerToEdit())) {
                        if (guildConfig.getServerEditingType().equalsIgnoreCase("IP")) {
                            sa[2] = testMessage[1];
                            stopServerEdit();
                            return "IP Edited";
                        } else if (guildConfig.getServerEditingType().equalsIgnoreCase("Port")) {
                            sa[3] = testMessage[1];
                            stopServerEdit();
                            return "Port Edited";
                        } else if (guildConfig.getServerEditingType().equalsIgnoreCase("Desc")) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(message.toString());
                            stringBuilder.delete(0, testMessage[0].length() + 1);
                            sa[4] = stringBuilder.toString();
                            stopServerEdit();
                            return "Description Edited";
                        } else {
                            stopServerEdit();
                            return errorMessage;
                        }
                    }
                }
                stopServerEdit();
            } else {
                return "A Server Listing is currently being edited.\nAn admin or Moderator will have to perform Sail.AbandonEdit to allow for another edit.";
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
                                return "You are now Editing the Server " + testMessage[2] + "\nPerform Sail.EditServer [Contents] to change the server properties,\nOr Sail.AbandonEdit to cancel editing";
                            } else {
                                return "Cannot edit " + testMessage[2];
                            }
                        } else {
                            return "You do not have permission to edit this server listing.";
                        }
                    }
                }
                return "server does not exist";
            } else if (testMessage.length == 2) {
                return "You must specify a channel, this channel is either IP, Port or Desc";
            } else if (testMessage.length == 1) {
                return "You must specify a server to edit\n Sail.EditServer [ServerName] Ip, Port, or Desc";
            } else {
                return "Too many or too few arguments\n" +
                        "Sail.EditServer [ServerName] Ip, Port, or Desc";
            }
        }
        return errorMessage;
    }

    @CommandAnnotation(name = "AbandonEdit", channel = "Servers", description = "Stops the Server Listing Editing process")
    public String stopServerEdit() {
        if (isAdmin || isMod || isOwner || author.equals(guild.getUserByID(guildConfig.getServerEditor()))) {
            guildConfig.setEditingServer(false);
            guildConfig.setServerEditor("");
            guildConfig.setServerEditingType("");
            guildConfig.setServerToEdit("");
            return "Stopped the Server Listing Editing process";
        } else {
            return notAllowed;
        }
    }
}


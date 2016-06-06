import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.impl.obj.Message;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

import java.lang.reflect.Method;

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

    public Commands(Message message) {
        this.message = message;
        guild = (Guild) message.getGuild();
        channel = (Channel) message.getChannel();
        author = message.getAuthor();
        guildID = message.getGuild().getID();
        notAllowed = "I'm Sorry " + author.getName() + " I'm afraid I can't do that.";
        guildConfigFile = "GuildConfigs/" + guildID + "_config.json";

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
            if (role.getName().toLowerCase().contains("chucklefish")){
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
    @CommandAnnotation(name = "Hello", channel = "any", description = "Says Hello")
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
            return "Hello User";
        }
    }

    @CommandAnnotation(name ="NightlyFAQ", channel = "any", description = "Posts a link to the nightly FAQ post on Reddit.")
    public String NightlyFAQ() {
        return "Here are the Frequently Asked Questions when it comes to the starbound nightly Build.\n" +
                "https://www.reddit.com/r/starbound/comments/4jnhjw/nightly_faq_so_you_want_to_play_nightly/#announce";
    }

    @CommandAnnotation(name = "NightlyFix", channel = "any", description = "Posts a link to the Nightly Fix mod")
    public String nightlyFix() {
        return "Add this to your Starbound - Unstable/mods folder\n" +
                "http://community.playstarbound.com/resources/cheerful-giraffe-nightly-fix.3397/";
    }

    @CommandAnnotation(name = "SetGeneral", channel = "any", description = "Sets the current Channel as the Server's 'General' Channel")
    public String setGeneral(){
        if (isAdmin || isOwner){
            guildConfig.setGeneralChannel(channel.getID());
            return "This Channel is now the Server's 'General' Channel.";
        } else {
            return notAllowed;
        }
    }

    @CommandAnnotation(name = "setServersChannel", channel = "any",description = "Sets the current Channel as the Server's 'Servers' Channel")
    public String setServersChannel(){
        if (isAdmin || isOwner){
            guildConfig.setServersChannel(channel.getID());
            return "This Channel is now the Server's 'Servers' Channel.";
        } else {
            return notAllowed;
        }
    }

    @CommandAnnotation(name = "setRaceSelect", channel = "any", description = "Sets the current Channel as the Server's 'Race Select' Channel")
    public String setRaceSelect(){
        if (isAdmin || isOwner){
            guildConfig.setRaceSelectChannel(channel.getID());
            return "This Channel is now the Server's 'Race Select' Channel.";
        } else {
            return notAllowed;
        }
    }

    @CommandAnnotation(name = "Help", channel = "any",description = "Lists all of the Commands Sail can run")
    public String sailHelp(){
        Method[] methods = this.getClass().getMethods();
        StringBuilder commandList = new StringBuilder();
        commandList.append("Hello My Name is S.A.I.L\n" +
                "Here are the commandses currently at my disposal:");
        for (Method m : methods){
            if (m.isAnnotationPresent(CommandAnnotation.class)){
                CommandAnnotation anno = m.getAnnotation(CommandAnnotation.class);
                commandList.append("\n   " + Globals.commandPrefix + anno.name());
            }
        }
        commandList.append("\nMy Author is Dawn Felstar and I am currently In Development\n" +
                "I am being written using Java with the Discord4J Libraries.\n" +
                "My avatar was created by HadronKalido,\n" +
                "if you have any feedback or issues mention this bot to let my creator know.");
        return commandList.toString();
    }

    @CommandAnnotation(name = "Info", channel = "any", description = "Gives information about a specific command\nUsage: Sail.Info [Command]")
    public String sailInfo(){
        String response;
        Method[] methods = this.getClass().getMethods();
        String buildMessage = message.toString();
        String[] testMessage = buildMessage.split(" ");

        for (Method m: methods){
            if (m.isAnnotationPresent(CommandAnnotation.class)){
                CommandAnnotation anno = m.getAnnotation(CommandAnnotation.class);
                if (testMessage[1].toLowerCase().contains(anno.name().toLowerCase())){
                    return Globals.commandPrefix + anno.name() + "\n" + anno.description();
                }
            }
        }
        return "That command does not exist.";
    }

    @CommandAnnotation(name = "doLoginMessage",channel = "any", description = "Toggles the login mesaage")
    public String setLoginMessage(){
        if(isAdmin || isOwner) {
            if (guildConfig.getDoLoginMessage()) {
                guildConfig.setDoLoginMessage(false);
            } else {
                guildConfig.setDoLoginMessage(true);
            }
            return "Toggled the Login Message";
        }else {
            return notAllowed;
        }
    }
}


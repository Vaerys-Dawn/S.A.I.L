import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.obj.IUser;

/**
 * Created by Vaerys on 20/05/2016.
 */
public class AdminCommands {
    Channel channel;
    IUser user;
    Guild guild;

    public AdminCommands(Channel channel, IUser user, Guild guild) {
        this.channel = channel;
        this.user = user;
        this.guild = guild;
    }


    public String SailPlease(String userType) {

        if (userType.equals("Admin") || userType.equals("Moderator") || userType.equals("CFStaff")) {
            return "Your wish is my command.";
        } else {
            return "Im afraid i cant do that for you " + user + ".";
        }
    }
}

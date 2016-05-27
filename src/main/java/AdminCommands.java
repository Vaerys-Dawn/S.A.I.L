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


    public String SailPlease(boolean isAdmin, boolean isMod, boolean isCF, boolean isOwner) {

        if (isAdmin || isMod || isCF || isOwner) {
            return "Your wish is my command.";
        } else {
            return "Im afraid i cant do that for you " + user + ".";
        }
    }
}

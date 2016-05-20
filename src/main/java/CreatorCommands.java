import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.obj.IUser;

/**
 * Created by Vaerys on 20/05/2016.
 */
public class CreatorCommands {
    Channel channel;
    IUser user;
    Guild guild;

    public CreatorCommands(Channel channel, IUser user, Guild guild) {
        this.channel = channel;
        this.user = user;
        this.guild = guild;
    }
}

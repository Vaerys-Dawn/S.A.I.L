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

    public String botMessage(){
        return "Hello everyone, I am a Bot my Name is S.A.I.L. This command can only be run by my creator Dawn Felstar.";
    }
}

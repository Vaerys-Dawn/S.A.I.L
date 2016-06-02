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

//    if (message.toLowerCase().startsWith(Globals.ADMIN_COMMAND_PREFIX.toLowerCase())) {
//        if (isAdmin || isMod || isCF || isOwner) {
//            if (message.equalsIgnoreCase(please.getCommand())) {
//                System.out.println(message);
//                channel.sendMessage(adminCommands.SailPlease());
//            }
//            if (message.toLowerCase().startsWith(addRace.getCommand().toLowerCase())) {
//                channel.sendMessage(adminCommands.AddRace());
//            }
//        } else if (isAdmin || isOwner) {
//
//        } else {
//            channel.sendMessage("I'm afraid I cannot do that for you " + author.getName());
//        }
//    }
//    if (message.toLowerCase().startsWith(Globals.CREATOR_COMMAND_PREFIX.toLowerCase())) {
//        if (isCreator)
//            if (message.equalsIgnoreCase(botMessage.getCommand())) {
//                System.out.println(message);
//                channel.sendMessage(creatorCommands.botMessage());
//            }
//    }

    public String SailPlease() {
            return "Your wish is my command.";
    }

    public String AddRace() {
        return "Still in Testing Phase";
    }
}
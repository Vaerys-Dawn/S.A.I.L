import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.io.File;
import java.io.IOException;

/**
 * Created by Vaerys on 02/07/2016.
 */
public class InfoChannel {

    public InfoChannel(){

    }

    public void updateInfo(Channel channel, Guild guild) {
        try {
            File welcomeIcon = new File("Icons/Headers/Welcome_Icon.png");
            File comunityIcon = new File("Icons/Headers/Community_Icon.png");
            File welcomeMessage = new File("Icons/Text/Welcome_Message.png");
            File channelsText = new File("Icons/Text/Channels_Text.png");
            File spacer20 = new File("Icons/Text/Spacer_20.png");
            File spacer50 = new File("Icons/Text/Spacer_50.png");
            channel.sendFile(welcomeIcon);
            Thread.sleep(2000);
            channel.sendFile(welcomeMessage);
            Thread.sleep(2000);
            channel.sendMessage(
                    "We are a fairly new discord for a fairly old comunity and as such \n" +
                    "the admins here try our hardest to make this discord the best it \n" +
                    "can be so we hope that you enjoy your stay here.");
            channel.sendFile(spacer20);
            Thread.sleep(2000);
            channel.sendFile(comunityIcon);
            Thread.sleep(2000);
            channel.sendFile(channelsText);
            Thread.sleep(2000);
            channel.sendMessage(
                    "For our Discord server we have many differnt channels that you\n" +
                    "can talk in that each relate to a differnt part of the starbound \n" +
                    "community.\n" +
                    "\n" +
                    guild.getChannelByID("176662557199695883")+"\n" +
                    "This channel is for all things related to modding in Starbound,\n" +
                    "these things range from developing mods to just sharing them.\n" +
                    "\n" +
                    guild.getChannelByID("176667376157196288")+"\n" +
                    "This channel is there so that people can advertise their server to\n" +
                    "the community and get people playing together.\n");
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

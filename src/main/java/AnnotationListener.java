import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.obj.Role;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;

import java.util.ArrayList;

/**
 * Created by Vaerys on 19/05/2016.
 */
public class AnnotationListener {

    ArrayList<String> roles = new ArrayList<String>();
    @EventSubscriber
    public void onReadyEvent(ReadyEvent event){

    }

    public void onMessageRecivedEvent(MessageReceivedEvent event) throws HTTP429Exception, DiscordException, MissingPermissionsException {

        String message = event.getMessage().toString();
        ArrayList<String> roles = new ArrayList<String>();

        if (message.equalsIgnoreCase(">Help")){
            String guildID = event.getClient().getGuilds().toString();
        }
    }
}

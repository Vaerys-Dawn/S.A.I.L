import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;

/**
 * Created by Vaerys on 19/05/2016.
 */
public class AnnotationListener {

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event){
        System.out.println("connected");
    }

    @EventSubscriber
    public void onMessageRecivedEvent(MessageReceivedEvent event) {
        String text = event.getMessage().toString();
        System.out.println(text);
    }
}

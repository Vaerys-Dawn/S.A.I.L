import sx.blah.discord.api.EventDispatcher;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

/**
 * Created by Vaerys on 19/05/2016.
 */
public class Main {

    public static void main(String[] args) {
        String token = "MTgyNTAyOTY0NDA0MDI3Mzky.Ch7LCQ.vNliQTexzBQ-ZvqzpgcqoPKSCZI";
        try{
            IDiscordClient client = Client.getClient(token, true);
            EventDispatcher dispatcher = client.getDispatcher();
            dispatcher.registerListener(new InterfaceListener());
            dispatcher.registerListener(new AnnotationListener());
        }catch (DiscordException ex) {
            System.out.println(ex);
        }
    }
}

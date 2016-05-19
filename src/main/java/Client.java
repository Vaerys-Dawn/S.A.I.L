import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

/**
 * Created by Vaerys on 19/05/2016.
 */
public class Client {
    public static IDiscordClient getClient(String token,boolean login) throws DiscordException {
        ClientBuilder clientBuilder = new ClientBuilder();
        try {

        }catch (Exception ex){
        }

        clientBuilder.withToken(token);
        if(login){
            System.out.println("logging in");
            return clientBuilder.login();
        }else{
            return clientBuilder.build();
        }
    }
}

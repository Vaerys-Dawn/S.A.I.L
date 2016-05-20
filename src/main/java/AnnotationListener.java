import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;

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
        String message = event.getMessage().toString(); //gets the message converts it to a String
//        System.out.println(message); //prints out the message
        String roles = event.getMessage().getAuthor().getRolesForGuild(event.getMessage().getGuild()).toString().toLowerCase(); //gets the roles of the user and turns it into a string
        Guild guild = (Guild) event.getMessage().getGuild(); //gets the guild that the message was sent to
        Channel channel = (Channel) event.getMessage().getChannel(); //gets the channel the message was sent to
        IUser author = event.getMessage().getAuthor();

        //user type detectors
        boolean isCreator = author.getID().equals("153159020528533505"); //tests for the Bot's creator
        boolean isOwner = author.equals(guild.getOwner()); //test for the Guild owner
        boolean isAdmin = roles.contains("admin"); //tests for the Admin role
        boolean isMod = roles.contains("moderator"); //tests for the Moderator role
        boolean isCFStaff = roles.contains("chucklefish"); //tests for the Chucklefish role

        try {
            if (message.equalsIgnoreCase(">HelloSail")) {
                if (isCreator) {
                    channel.sendMessage("Hello Creator");
                }else if (isOwner) {
                    channel.sendMessage("Hello Server Owner");
                } else if (isAdmin){
                    channel.sendMessage("Hello Admin");
                } else if (isMod){
                    channel.sendMessage("Hello Moderator");
                } else if (isCFStaff){
                    channel.sendMessage("Hello Chucklefish Staff Member");
                } else {
                    channel.sendMessage("Hello User");
                }
            }else if (message.equalsIgnoreCase(">IamListening")) {
                channel.sendMessage("Hello all i am S.A.I.L and i will be managing \n" +
                        "your server soon, at the moment all i am doing is listening to\n" +
                        "your messages and waiting for a command, try >InfoSail.");
            }else if (message.equalsIgnoreCase(">InfoSail")){
                channel.sendMessage("I Currently have these commands at my disposal:\n" +
                        ">InfoSail\n" +
                        ">IamListening\n" +
                        "+SailPlease\n" +
                        ">HelloSail\n" +
                        "I am being built using Java and my creator is Dawn Felstar");
            }else if (message.equalsIgnoreCase("+SailPlease")){
                if (isAdmin || isMod || isCFStaff) {
                    channel.sendMessage("Your wish is my command.");
                } else{
                    channel.sendMessage("Im afraid i cant do that for you " + author + ".");
                }
            }
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (HTTP429Exception e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
    }

}

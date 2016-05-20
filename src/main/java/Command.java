/**
 * Created by Vaerys on 19/05/2016.
 */
public class Command {

    private String name;
    private String initiator;
    private String minperm;
    private String room;
    private String about;
    private String command;

    public Command(String name, String initiator, String command, String about, String minperm, String room) {
        this.name = name;
        this.initiator = initiator;
        this.command = command;
        this.about = about;
        this.minperm = minperm;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getMinperm() {
        return minperm;
    }

    public void setMinperm(String minperm) {
        this.minperm = minperm;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCommand() {
        return command;
    }
}


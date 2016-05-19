/**
 * Created by Vaerys on 19/05/2016.
 */
public class CommandGenerator {

    private String name;
    private String initiator;
    private String minRole;
    private String room;
    private String about;
    private String command;

    public CommandGenerator(String name, String initiator, String command, String about, String minRole, String room) {
        this.name = name;
        this.initiator = initiator;
        this.command = command;
        this.about = about;
        this.minRole = minRole;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getMinRole() {
        return minRole;
    }

    public void setMinRole(String minRole) {
        this.minRole = minRole;
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

    public void setCommand(String command) {
        this.command = command;
    }

    public void setName(String name) {
        this.name = name;

    }
}
//for things

import java.util.ArrayList;

/**
 * Created by Vaerys on 19/05/2016.
 */
public class Command {

    private String command;
    private String usage;
    private String description;
    private ArrayList<String> text;

    public Command(String command, String usage, String description) {
        this.command = command;
        this.usage = usage;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getText(int i) {
        return getText(i);
    }

    public void addText(String addText) {
        text.add(addText);
    }
}


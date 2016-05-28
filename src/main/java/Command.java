import java.util.ArrayList;

/**
 * Created by Vaerys on 19/05/2016.
 */
public class Command {

    private String command;
    private String usage;
    private String description;
    private String prefix;

    public Command(String prefix, String command, String usage, String description) {
        this.prefix = prefix;
        this.command = command;
        this.usage = usage;
        this.description = description;
    }

    public String getPrefix() {
        return prefix.toLowerCase();
    }

    public String getCommand() {
        return (prefix + command).toLowerCase();
    }

    public String getHelp() {
        return "Usage: "+ (prefix + command).toLowerCase() + usage + "\n" + description;
    }
}


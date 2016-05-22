import sx.blah.discord.handle.impl.obj.Channel;

/**
 * Created by Vaerys on 22/05/2016.
 */
public class GuildConfig {

    Channel raceSelect;
    Channel general;
    Channel servers;
    String storedGuildID;
    Boolean doLoginMessage;

    public Channel getRaceSelect() {
        return raceSelect;
    }

    public void setRaceSelect(Channel raceSelect) {
        this.raceSelect = raceSelect;
    }

    public Channel getGeneral() {
        return general;
    }

    public void setGeneral(Channel general) {
        this.general = general;
    }

    public Channel getServers() {
        return servers;
    }

    public void setServers(Channel servers) {
        this.servers = servers;
    }

    public String getStoredGuildID() {
        return storedGuildID;
    }

    public void setStoredGuildID(String storedGuildID) {
        this.storedGuildID = storedGuildID;
    }

    public Boolean getDoLoginMessage() {
        return doLoginMessage;
    }

    public void setDoLoginMessage(Boolean doLoginMessage) {
        this.doLoginMessage = doLoginMessage;
    }
}

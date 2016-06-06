/**
 * Created by Vaerys on 22/05/2016.
 */
public class GuildConfig {

    String raceSelectChannel = "";
    String generalChannel = "";
    String serversChannel = "";
    Boolean doLoginMessage = false;

    public GuildConfig() {
    }

    public String getRaceSelectChannel() {
        return raceSelectChannel;
    }

    public void setRaceSelectChannel(String raceSelect) {
        this.raceSelectChannel = raceSelect;
    }

    public String getGeneralChannel() {
        return generalChannel;
    }

    public void setGeneralChannel(String generalChannel) {
        this.generalChannel = generalChannel;
    }

    public String getServersChannel() {
        return serversChannel;
    }

    public void setServersChannel(String serversChannel) {
        this.serversChannel = serversChannel;
    }

    public Boolean getDoLoginMessage() {
        return doLoginMessage;
    }

    public void setDoLoginMessage(Boolean doLoginMessage) {
        this.doLoginMessage = doLoginMessage;
    }
}

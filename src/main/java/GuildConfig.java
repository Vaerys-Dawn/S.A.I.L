import java.util.ArrayList;

/**
 * Created by Vaerys on 22/05/2016.
 */
public class GuildConfig {

    String raceSelectChannel = "";
    String generalChannel = "";
    String serversChannel = "";
    Boolean doLoginMessage = false;
    ArrayList<String> races = new ArrayList<String>();
    ArrayList<String> serverList = new ArrayList<String>();

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

    public ArrayList<String> getRaces() {
        return races;
    }

    public void addRace(String race) {
        races.add(race);
    }

    public void removeRace(String race){
        for (int i = 0; i < races.size();i++){
            if (races.get(i).toLowerCase().equals(race.toLowerCase())){
                races.remove(i);
            }
        }
    }

}

import java.util.ArrayList;

/**
 * Created by Vaerys on 22/05/2016.
 */
public class GuildConfig {

    String raceSelectChannel = "";
    String generalChannel = "";
    String serversChannel = "";
    Boolean doLoginMessage = false;
    Boolean editingServer = false;
    String serverEditor = "";
    String serverEditingType = "";
    String ServerToEdit = "";
    ArrayList<String> races = new ArrayList<String>();
    ArrayList<String[]> serverList = new ArrayList<String[]>();
    /*first part of the array should be the User ID.
    the next part should be the server name no spaces allowed.
    then comes the server IP and port
    lastly the server description*/

    public String getServerToEdit() {
        return ServerToEdit;
    }

    public void setServerToEdit(String serverToEdit) {
        ServerToEdit = serverToEdit;
    }

    public String getServerEditingType() {
        return serverEditingType;
    }

    public void setServerEditingType(String serverEditingType) {
        this.serverEditingType = serverEditingType;
    }

    public String getServerEditor() {
        return serverEditor;
    }

    public void setServerEditor(String serverEditor) {
        this.serverEditor = serverEditor;
    }

    public Boolean getEditingServer() {
        return editingServer;
    }

    public void setEditingServer(Boolean editingServer) {
        this.editingServer = editingServer;
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

    public void addServer(String userID, String serverName){
        String[] newEntry = new String[5];
        newEntry[0] = userID;
        newEntry[1] = serverName;
        serverList.add(newEntry);
    }

    public ArrayList<String[]> getServerList(){
        return serverList;
    }

}

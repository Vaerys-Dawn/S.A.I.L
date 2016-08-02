import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vaerys on 27/07/2016.
 */
public class Characters {
    ArrayList<String[]> charList = new ArrayList<>();

    public String saveChar(IUser user, Guild guild, String args) {
        String guildID = guild.getID();
        String response = "> New Character added";
        String[] playerChar = new String[4];
        playerChar[0] = args;
        playerChar[1] = user.getID();
        playerChar[2] = user.getDisplayName(guild);

        //gets the race that the user has on the server;
        List<IRole> userRoles = user.getRolesForGuild(guild);
        GuildConfig guildConfig = new GuildConfig();
        FileHandler handler = new FileHandler();
        guildConfig = (GuildConfig) handler.readfromJson("GuildConfigs/" + guildID + "_config.json", GuildConfig.class);
        ArrayList<String> races = guildConfig.getRaces();
        StringBuilder builder = new StringBuilder();
        for (IRole r : userRoles) {
            boolean roleFound = false;
            for (String s : races) {
                if (r.getID().equals(s)) {
                    if (!roleFound) {
                        builder.append(r.getID() + ",");
                        roleFound = true;
                    }
                }
            }
        }
        builder.delete(builder.length() - 1, builder.length());
        playerChar[3] = builder.toString();
        //updates the charList
        boolean isFound = false;
        for (int i = 0; i < charList.size(); i++) {
            if (charList.get(i)[0].equalsIgnoreCase(args)) {
                if (charList.get(i)[1].equals(user.getID())) {
                    if (!isFound) {
                        response = "> Edited character.";
                        charList.set(i, playerChar);
                        isFound = true;
                    }
                }
            }
        }
        if (!isFound) {
            charList.add(playerChar);
        }
        return response;
    }

    public String delChar(IUser user, String args) {
        for (int i = 0; i < charList.size(); i++) {
            if (user.getID().equals(charList.get(i)[1]) && charList.get(i)[0].equalsIgnoreCase(args)) {
                charList.remove(i);
            }
        }
        return "> Character deleted.";
    }

    public String selChar(IUser user, Guild guild, String args) {
        try {

            FileHandler handler = new FileHandler();
            List<IRole> userRoles = user.getRolesForGuild(guild);
            GuildConfig guildConfig = (GuildConfig) handler.readfromJson("GuildConfigs/" + guild.getID() + "_config.json", GuildConfig.class);
            ArrayList<String> races = guildConfig.getRaces();
            boolean charfound = false;
            for (String[] sA : charList) {
                if (sA[0].equalsIgnoreCase(args) && user.equals(guild.getUserByID(sA[1]))) {
                    charfound = true;
                    String[] splitRoleIDs = sA[3].split(",");
                    if (!user.getDisplayName(guild).equals(sA[2])) {
                        guild.setUserNickname(user, sA[2]);
                    }
                    for (int i = 0; i < races.size(); i++) {
                        int holdInt = userRoles.size();
                        for (int r = 0;r < holdInt;r++) {
                            if (races.get(i).equals(userRoles.get(r).getID())) {
                                userRoles.remove(r);
                                holdInt = userRoles.size();
                            }
                        }
                    }
                    for (String s : splitRoleIDs) {
                        for (String r : races) {
                            if (s.equalsIgnoreCase(r)) {
                                userRoles.add(guild.getRoleByID(r));
                            }
                        }
                    }
                }
            }
            if (charfound) {
                IRole[] newRoleList;
                newRoleList = userRoles.stream().toArray(IRole[]::new);
                guild.editUserRoles(user, newRoleList);
                return "> Character Updated.";
            }else{
                return "> No character with that name found";
            }
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "502 Error Try again later.";
    }
}

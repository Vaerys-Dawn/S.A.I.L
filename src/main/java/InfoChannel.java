import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageList;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Vaerys on 02/07/2016.
 */
public class InfoChannel {

    public InfoChannel() {

    }

    // TODO: 04/07/2016  rules, staff, bots.

    public void updateInfo(Channel channel, Guild guild) {
        try {
            if (channel.getID().equals("176667531078139904")) {
                MessageList messages = channel.getMessages();
                if (messages.size() != 0) {
                    messages.bulkDelete(messages);
                    messages.purge();
                }
            }
            FileHandler handler = new FileHandler();
            handler.createDirectory("GuildInfo");
            ArrayList<String> infoContent = handler.readFromFile("GuildInfo/" + guild.getID() + "_Info.txt");
            StringBuilder finalBuilder = new StringBuilder();
            for (String s : infoContent) {
                if (s.contains("#image#")) {
                    Thread.sleep(2000);
                    if (!finalBuilder.toString().equals("")) {
                        channel.sendMessage(finalBuilder.toString());
                        finalBuilder.delete(0, finalBuilder.length());
                    }
                    String[] split = s.split(":");
                    File file = new File(split[1]);
                    channel.sendFile(file);
                } else if (s.contains("#mention#")) {
                    boolean inNext = false;
                    String mentionID;
                    String[] split = s.split("'");
                    ArrayList<String> line = new ArrayList<>();
                    StringBuilder builder = new StringBuilder();
                    for (String sP : split) {
                        if (inNext) {
                            mentionID = sP;
                            inNext = false;
                            sP = guild.getUserByID(mentionID).mention();
                            line.add(sP);
                        } else if (sP.contains("#mention#")) {
                            inNext = true;
                            sP = sP.replaceAll("#mention#", "");
                            line.add(sP);
                        } else {
                            line.add(sP);
                        }
                    }
                    for (String l : line) {
                        builder.append(l);
                    }
                    finalBuilder.append(builder.toString() + "\n");
                } else if (s.contains("#username#")) {
                    boolean inNext = false;
                    String UserID;
                    String[] split = s.split("'");
                    ArrayList<String> line = new ArrayList<>();
                    StringBuilder builder = new StringBuilder();
                    for (String sP : split) {
                        if (inNext) {
                            UserID = sP;
                            inNext = false;
                            sP = guild.getUserByID(UserID).getName();
                            line.add(sP);
                        } else if (sP.contains("#username#")) {
                            inNext = true;
                            sP = sP.replaceAll("#username#", "");
                            line.add(sP);
                        } else {
                            line.add(sP);
                        }
                    }
                    for (String l : line) {
                        builder.append(l);
                    }
                    finalBuilder.append(builder.toString() + "\n");
                } else if (s.contains("#channel#")) {
                    boolean inNext = false;
                    String channelID;
                    String[] split = s.split("'");
                    ArrayList<String> line = new ArrayList<>();
                    StringBuilder builder = new StringBuilder();
                    for (String sP : split) {
                        if (inNext) {
                            channelID = sP;
                            inNext = false;
                            sP = guild.getChannelByID(channelID).mention();
                            line.add(sP);
                        } else if (sP.contains("#channel#")) {
                            inNext = true;
                            sP = sP.replaceAll("#channel#", "");
                            line.add(sP);
                        } else {
                            line.add(sP);
                        }
                    }
                    for (String l : line) {
                        builder.append(l);
                    }
                    finalBuilder.append(builder.toString() + "\n");
                } else {
                    finalBuilder.append(s + "\n");
                }
            }
            Thread.sleep(2000);
            channel.sendMessage(finalBuilder.toString());
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/*
" +
"**@" + guild.getUserByID("177800413334994944").getName() + "**\n" +
"Helps out a bunch, knows their stuff.\n" +
"*/

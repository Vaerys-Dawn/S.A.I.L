import sx.blah.discord.handle.impl.obj.Channel;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageList;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.io.File;
import java.io.IOException;

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
            File welcomeIcon = new File("Icons/Headers/Welcome_Icon.png");
            File communityIcon = new File("Icons/Headers/Community_Icon.png");
            File rulesIcon = new File("Icons/Headers/Rules_Icon.png");
            File staffIcon = new File("Icons/Headers/Staff_Icon.png");
            File botsIcon = new File("Icons/Headers/Bots_Icon.png");
            File linksIcon = new File("Icons/Headers/Links_Icon.png");
            File welcomeMessage = new File("Icons/Text/Welcome_Message.png");
            File channelsText = new File("Icons/Text/Channels_Text.png");
            File racesText = new File("Icons/Text/Roles_Races_Text.png");
            File generalRulesText = new File("Icons/Text/General_Rules_Text.png");
            File voiceRulesText = new File("Icons/Text/Voice_Rules_Text.png");
            File importantLinksText = new File("Icons/Text/Important_Links_Text.png");
            File affiliatesText = new File("Icons/Text/Affiliates_text.png");
            File adminsText = new File("Icons/Text/Admins_Text.png");
            File modsText = new File("Icons/Text/Moderators_Text.png");
            File sailText = new File("Icons/Text/Sail_text.png");
            File nadekoText = new File("Icons/Text/Nadeko_text.png");
            File mee6Text = new File("Icons/Text/mee6_text.png");
            File spacer20 = new File("Icons/Text/Spacer_20.png");
            File vanillaRaces = new File("Icons/Races/Vanilla_races.png");
            File moddedRaces = new File("Icons/Races/Modded_races.png");
            channel.sendFile(welcomeIcon);
            Thread.sleep(2000);
            channel.sendFile(welcomeMessage);
            Thread.sleep(2000);
            channel.sendMessage(
                    "We are a fairly new Discord for a fairly old community and as such \n" +
                            "the admins here try our hardest to make this Discord the best it \n" +
                            "can be so we hope that you enjoy your stay here.");
            channel.sendFile(spacer20);
            Thread.sleep(2000);
            channel.sendFile(communityIcon);
            Thread.sleep(2000);
            channel.sendFile(channelsText);
            Thread.sleep(2000);
            channel.sendMessage(
                    "For our Discord server we have many different channels that you\n" +
                            "can talk in that each relate to a different part of the Starbound \n" +
                            "community.\n" +
                            "\n" +
                            guild.getChannelByID("176662557199695883") + "\n" +
                            "This channel is for all things related to modding in Starbound,\n" +
                            "these things range from developing mods to just sharing them.\n" +
                            "\n" +
                            guild.getChannelByID("176667376157196288") + "\n" +
                            "This channel is there so that people can advertise their server to\n" +
                            "the community and get people playing together.\n" +
                            "\n" +
                            guild.getChannelByID("176667422500061184") + "\n" +
                            "This channel is for all of the artistic people in the community\n" +
                            "that are good at art or building so that they can show their skill.\n" +
                            "\n" +
                            guild.getChannelByID("177093464696029184") + "\n" +
                            "This channel is for all things to do with the Nightly Starbound updates\n" +
                            "we have a good community there and they are always there to help.\n" +
                            "\n" +
                            guild.getChannelByID("177089535576899584") + "\n" +
                            "This channel is for anything non Starbound related. Almost anything\n" +
                            "goes on in there so sometimes it can get a bit crazy.\n" +
                            "\n" +
                            guild.getChannelByID("176667679778668544") + "\n" +
                            "This channel is for suggestions relating to the Starbound Discord \n" +
                            "Server if you have any feedback this is the place to go."
            );
            Thread.sleep(2000);
            channel.sendFile(racesText);
            Thread.sleep(2000);
            channel.sendFile(vanillaRaces);
            Thread.sleep(2000);
            channel.sendFile(moddedRaces);
            Thread.sleep(2000);
            channel.sendMessage(
                    "You can choose any of the Above Races by doing the\n" +
                            "command `Sail.Race [Race]`, the race you choose will give you\n" +
                            "a custom colour and access to one of the many race specific\n" +
                            "channels which you can find below the " + guild.getChannelByID("176667358599970816") + " channel.");
            Thread.sleep(2000);
            channel.sendFile(spacer20);
            Thread.sleep(2000);
            channel.sendFile(rulesIcon);
            Thread.sleep(2000);
            channel.sendFile(generalRulesText);
            channel.sendMessage(
                    "**1** - Please Be kind to the other people on the server.\n" +
                            "**2** - Swearing is Allowed but we generally prefer if you don't.\n" +
                            "**3** - This Server is PG only Mods have the right to remove any\n" +
                            "      content we deem not safe for work with no warning. If you \n" +
                            "      repeatedly post NSFW content we will have to mute you.\n" +
                            "**4** - Please keep chat spam to a minimum, this includes links,\n" +
                            "      images and bot commands.\n" +
                            "**5** - we would love it if you kept to the channels that fit the topic and\n" +
                            "      we generally wont do anything about that unless you are \n" +
                            "      excessive.\n" +
                            "**6** - please keep all nightly spoilers in the " + guild.getChannelByID("177093464696029184") + " channel.\n" +
                            "**7** - respect the staff and they will respect you, this one is common\n" +
                            "      sense.\n" +
                            "**8** - Have fun!");
            Thread.sleep(2000);
            channel.sendFile(spacer20);
            Thread.sleep(2000);
            channel.sendFile(staffIcon);
            Thread.sleep(2000);
            channel.sendFile(adminsText);
            Thread.sleep(2000);
            channel.sendMessage(
                    "**@" + guild.getUserByID("153159020528533505").getName() + " (aka @" + guild.getUserByID("159186011455225856").getName() + ")** - Server Owner\n" +
                            "Does everything in her power to make this Discord great.\n" +
                            "\n" +
                            "**@" + guild.getUserByID("153044547880878080").getName() + "**\n" +
                            "The original owner.\n" +
                            "\n" +
                            "**@" + guild.getUserByID("139974256535535616").getName() + "**\n" +
                            "A helper who goes the extra lightyear.\n" +
                            "\n" +
                            "**@" + guild.getUserByID("142642744836358145").getName() + "**\n" +
                            guild.getUserByID("142642744836358145").mention() + " Message @Dawn Felstar with a quote.\n" +
                            "\n" +
                            "**@" + guild.getUserByID("178354254464024576").getName() + "**\n" +
                            "Makes some cool mods, always on the Subreddit to help!.");
            Thread.sleep(2000);
            channel.sendFile(modsText);
            Thread.sleep(2000);
            channel.sendMessage(
                    "**@" + guild.getUserByID("83984886494400512").getName() + "**\n" +
                            "Avali Mod dev and overall a cool guy.\n" +
                            "\n" +
                            "**@" + guild.getUserByID("138839301483003904").getName() + "**\n" +
                            guild.getUserByID("138839301483003904").mention() + " Message @Dawn Felstar with a quote.");
            Thread.sleep(2000);
            channel.sendFile(spacer20);
            Thread.sleep(2000);
            channel.sendFile(botsIcon);
            Thread.sleep(2000);
            channel.sendFile(sailText);
            Thread.sleep(2000);
            channel.sendMessage("GitHub - <https://github.com/Vaerys-Dawn/S.A.I.L>\n" +
                    "This bot is a dedicated bot for the Starbound Discord.\n" +
                    "Help command: `Sail.Help`");
            Thread.sleep(2000);
            channel.sendFile(nadekoText);
            Thread.sleep(2000);
            channel.sendMessage("GitHub - <https://github.com/Kwoth/NadekoBot>\n" +
                    "This is an all rounder bot that was added to add a bit of fun\n" +
                    "to the Discord.\n" +
                    "Help command: `-help`");
            Thread.sleep(2000);
            channel.sendFile(mee6Text);
            Thread.sleep(2000);
            channel.sendMessage("Website - <https://mee6.xyz/>\n" +
                    "Level up! this bot tracks your message count and levels you up\n" +
                    "you can get some cool rewards at certain levels.\n" +
                    "Help command `!help`");
            channel.sendFile(spacer20);
            Thread.sleep(2000);
            channel.sendFile(linksIcon);
            Thread.sleep(2000);
            channel.sendFile(importantLinksText);
            Thread.sleep(2000);
            channel.sendMessage(
                    "Home page: <http://playstarbound.com/>\n" +
                            "Wiki: <http://starbounder.org/Starbound_Wiki>\n" +
                            "Forums: <http://community.playstarbound.com/>\n" +
                            "Blog: <http://playstarbound.com/blog/>\n" +
                            "Subreddit: <https://www.reddit.com/r/starbound>\n" +
                            "Steam Page: <http://store.steampowered.com/app/211820/>");
            Thread.sleep(2000);
            channel.sendFile(affiliatesText);
            Thread.sleep(2000);
            channel.sendMessage("https://discord.gg/stardewvalley\n" +
                    "http://discord.gg/4vMVUm8");
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

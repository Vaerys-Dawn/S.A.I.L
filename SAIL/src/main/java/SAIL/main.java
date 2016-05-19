package SAIL;

import sx.blah.discord.api.internal.DiscordClientImpl;
import sx.blah.discord.util.DiscordException;

public class main {

	public static void main (final String args[]) {

		String token = "";

		DiscordClientImpl clientImpl = new DiscordClientImpl(token, 200, 10, false);
		
		try {
			clientImpl.isBot();
			clientImpl.login();
		} catch (DiscordException e) {
			e.printStackTrace();
		}
	}
}

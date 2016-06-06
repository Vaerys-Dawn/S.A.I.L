Welcome To S.A.I.L

Developer - Vaerys Dawn

Personal Notes

- this is meant to be a bot for starbound

Commands

	>ListRaces
		- Lists all avalible races that users can select
		* Vanilla Races to choose from:
		  - #raceName#, ~repeat for all vanilla races~.
		  Modded races to choose from:
		  - #raceName#, ~repeat for all modded races~.
		@everyone #raceselect
	>Race #raceName#
		- Asigns ths race removes any other races
		* @User@ Your Race is now #raceName#.
		@everyone #raceselect
	>RaceStats
		- Lists all of the race roles and the amount of people in each role
		* #raceName# #userCount# ~repeat for all roles on a new line~
		@everyone #raceselect
	+AddRace #raceName# #isModded#
		- adds the Race to the list of races that can be assigned
		* #raceName# has been added to list of avalible races.
		@Admin #bot_commands
	+RemoveRace #raceName#
		removes the Race from the list of races that can be selected
		* #raceName# has been removed from the list of avalible races.
		@Admin #bot_commands
	>Version
		- Displays the most current version of Starbound
		* The most current version of Starbound is #Version# it was released on #versionDate#.
		@everyone #any
	>Servers
		- Lists serversChannel
		* Multiplayer serversChannel:
		  - #serverName# ~Repeat on all in list on new line~
		  More information can be gotten by doing >ServerInfo #serverName#
		@everyone #serversChannel
	>ServerInfo #serverName#
		- Lists information of the server
		* #serverName#
		  Server IP: #serverIP# #port#
		  #MotD#
		@everyone #serversChannel
	+AddServer #serverName# #serverIP# #port# #MotD#
		- Adds a server to the list of serversChannel
		* #serverName# has been added to list of multiplayer serversChannel.
		@Moderator @Admin #bot_commands
	+RemoveServer #serverName#
		- Removes the server from the list of serversChannel
		* #serverName# has been removed from the list of multiplayer serversChannel.
		@Admin #bot_commands
	>Help
		- Displays Commands that can be run on the server
		* List of available commandses on this server:
			#command# ~repeat for all commandses~
		@everyone #any
	>Help #command#
		- Shows command description and usage
		* #command#
		#description#
		Usage #usage#
		@everyone #any
	

	
		
		
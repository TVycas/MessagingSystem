package client;

import util.Report;
import util.Strings;

import java.io.*;

public class ClientSender extends Thread {

	private PrintStream toServer;
	BufferedReader fromServer;
	public boolean breakTheLoop = false;
	private boolean showHelp = true;

	ClientSender(PrintStream toServer, BufferedReader fromServer) {
		this.toServer = toServer;
		this.fromServer = fromServer;
	}

	public void run() {
		// So that we can use the method readLine:
		BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

		try {
			// Then loop forever sending messages to recipients via the server:
			String command;
			while (true) {
				// The command to send to the server
				command = user.readLine();
				// Check if client.ClientReceiver changed the value and if so stop the thread by
				// breaking the while loop
				if (breakTheLoop)
					break;

				// based on command wait for more information and send it over to server.ServerReceiver
				if (command.equals(Strings.send)) {
					if(showHelp) {
						Report.behaviour(
								"Usage:\n" +
								"Recipient\n" +
								"server.Message\n" +
								"(To toggle help messages write \"help\")");
					}
					String recipient = user.readLine();
					String text = user.readLine();
					toServer.println(command);
					toServer.println(recipient);
					toServer.println(text);
				} else if (command.equals(Strings.createGroup)) {
					if(showHelp) {
						Report.behaviour(
								"Usage:\n" +
								"server.Group name\n" +
								"Member names in one line");
					}
					String groupName = user.readLine();
					String recipientsNames = user.readLine();
					toServer.println(command);
					toServer.println(groupName);
					toServer.println(recipientsNames);

				} else if (command.equals(Strings.group)) {
					if(showHelp) {
						Report.behaviour(
								"Usage:\n" +
								"server.Group name\n" +
								"server.Message");
					}
					String recipient = user.readLine();
					String text = user.readLine();
					toServer.println(command);
					toServer.println(recipient);
					toServer.println(text);

				} else if (command.equals(Strings.exitGroup)) {
					if(showHelp) {
						Report.behaviour(
								"Usage:\n" +
								"server.Group name");
					}
					String groupName = user.readLine();
					toServer.println(command);
					toServer.println(groupName);

				} else if (command.equals(Strings.addGroupMem)) {
					if(showHelp) {
						Report.behaviour(
								"Usage:\n" +
								"server.Group name\n" +
								"Member to add");
					}
					String groupName = user.readLine();
					String member = user.readLine();
					toServer.println(command);
					toServer.println(groupName);
					toServer.println(member);

				} else if (command.equals(Strings.delete) || command.equals(Strings.next)
						|| command.equals(Strings.previous)) {
					toServer.println(command);

				} else if(command.equals(Strings.help)) {
					showHelp = !showHelp;

				}else if (command.equals(Strings.quit)) {
					toServer.println(command);

				} else if (command.equals(Strings.logout)) {
					toServer.println(command);
					break;
				} else {
					Report.error("Unknown command");
				}
			}
		} catch (IOException e) {
			Report.errorAndGiveUp("Communication broke in client.ClientSender" + e.getMessage());
		}
	}
}

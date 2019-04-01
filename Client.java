
// Usage:
//        java Client user-nickname server-hostname
//
// After initializing and opening appropriate sockets, we start two
// client threads, one to send messages, and another one to get
// messages.


import java.io.*;
import java.net.*;

class Client {

	private static String username = "";
	private static PrintStream toServer = null;
	private static BufferedReader fromServer = null;
	private static Socket server = null;

	public static void main(String[] args) {

		// Check correct usage:
		if (args.length != 1) {
			Report.errorAndGiveUp("Usage: java Client server-hostname");
		}

		// Initialize information:
		String hostname = args[0];

		// Open sockets:

		try {
			server = new Socket(hostname, Port.number);
			toServer = new PrintStream(server.getOutputStream());
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
		} catch (UnknownHostException e) {
			Report.errorAndGiveUp("Unknown host: " + hostname);
		} catch (IOException e) {
			Report.errorAndGiveUp("The server doesn't seem to be running " + e.getMessage());
		}

		// Start the client initialising
		membInit();

		// Create two client threads of a different nature:
		ClientSender sender = new ClientSender(toServer, fromServer);
		ClientReceiver receiver = new ClientReceiver(fromServer, sender);

		// Run them in parallel:
		sender.start();
		receiver.start();
		// Wait for them to end and close sockets.
		try {
			sender.join();
			receiver.join();
			toServer.close();
			fromServer.close();
			server.close();
		} catch (IOException e) {
			Report.errorAndGiveUp("Something wrong " + e.getMessage());
		} catch (InterruptedException e) {
			Report.errorAndGiveUp("Unexpected interruption " + e.getMessage());
		}

	}

	// Speaks with ClientInit thread to register and login the user
	private static void membInit() {
		String userCommand = "";
		boolean logedIn = false;
		BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

		// If the user logs in, the while stops
		while (!logedIn) {
			try {
				// Only register and login are available as commands
				while (!(userCommand.equals("register") || userCommand.equals("login"))) {
					Report.behaviour("Please type register or login and type your username in the next line.");
					userCommand = user.readLine();
				}

				Report.behaviour("Please enter your username.");
				username = user.readLine();

			} catch (IOException e) {
				Report.errorAndGiveUp(e.getMessage());
			}

			if (userCommand.equals(Strings.register)) {
				toServer.println("register");
				toServer.println(username);

				// Based on the answer from the ClientInit, send the correct response
				try {
					if (fromServer.readLine().equals("exists")) {
						Report.error("A client with this username already exists.");
						username = "";
						userCommand = "";
					} else {
						Report.behaviour("You have successfully registered. Please login if you wish to continue.");
						username = "";
						userCommand = "";
					}
				} catch (IOException e) {
					Report.errorAndGiveUp("Communication with the server broke" + e.getMessage());
				}

			} else if (userCommand.equals(Strings.login)) {
				toServer.println("login");
				toServer.println(username);

				// If login is successful, stop the while loop
				try {
					if (fromServer.readLine().equals("exists")) {
						logedIn = true;
						Report.behaviour("You have successfully logged in.");
						Report.behaviour("Usage of the program:\n" +
								"send - send a message to any registered user\n" +
								"create group - create a group-chat with other users\n" +
								"delete - delete the current message\n" +
								"previous - see previous message\n" +
								"next - see next message\n" +
								"quit - exit the program and remove user data\n" +
								"logout - logout of the account and exit the program");
					} else {
						Report.behaviour("There is no client with this name. Have you registered?");
						username = "";
						userCommand = "";
					}
				} catch (IOException e) {
					Report.errorAndGiveUp("Communication with the server broke" + e.getMessage());
				}
			}
		}
	}
}

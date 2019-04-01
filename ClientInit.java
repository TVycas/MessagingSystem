import java.io.BufferedReader;

import java.io.IOException;

import java.io.PrintStream;


public class ClientInit extends Thread {

	ClientTable clientTable;

	BufferedReader fromClient;

	PrintStream toClient;



	ClientInit(ClientTable clientTable, BufferedReader fromClient, PrintStream toClient) {

		this.clientTable = clientTable;

		this.fromClient = fromClient;

		this.toClient = toClient;

	}

	
	public void run() {

		boolean logedIn = false;

		String clientCommand = "";

		String clientName = "";
	
		String realClientName = "";

	

			while (!logedIn) {

			try {

				clientCommand = fromClient.readLine();

				clientName = fromClient.readLine();

			} catch (IOException e) {

				Report.error("IO error " + e.getMessage());

			}

			if (clientCommand.equals("register")) {

				if (clientTable.exists(clientName)) {

					Report.error("A client with this username already exists.");

					toClient.println("exists");

				} else {
					// We add the client to the table:

					toClient.println("New user");

					clientTable.addMem(clientName);
					// Sets the username of the client

					clientTable.getUserInfo(clientName).setUsername(clientName);

					Report.behaviour("new user registred");

				}
			
				}else if (clientCommand.equals("login")) {


				if (clientTable.exists(clientName)) {

					Report.behaviour(clientName + " connected");
			// reports to the Client class that the user has been logged in

					toClient.println("exists");
					// real name is the name with the index of the login number added to the end of

					// the username

				realClientName = clientName + clientTable.getUserInfo(clientName).incNumbOfActiveLogins();

					clientTable.login(realClientName);

					logedIn = true;

				} else {

					toClient.println("User does not exist");

				}

			} else {

				Report.error("Something is wrong with the username.");

			}

		}


		// We create and start a new thread to write to the client:

		(new ServerSender(clientTable, realClientName, clientName, toClient)).start();


		// We create and start a new thread to read from the client:

		(new ServerReceiver(realClientName, clientName, fromClient, clientTable)).start();


	}
}

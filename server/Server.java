package server;
// Usage:
//        java server.Server
//
// There is no provision for ending the server gracefully.  It will
// end if (and only if) something exceptional happens.

import client.ClientInit;
import util.Port;
import util.Report;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        // This table will be shared by the server threads:
        ClientTable clientTable = new ClientTable();

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(Port.number);
        } catch (IOException e) {
            Report.errorAndGiveUp("Couldn't listen on port " + Port.number);
        }

        try {
            // We loop for ever, as servers usually do.
            while (true) {
                // Listen to the socket, accepting connections from new clients:
                Socket socket = serverSocket.accept();

                // This is so that we can use readLine():
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream toClient = new PrintStream(socket.getOutputStream());

                // Start to user initialisation process (registering and logging in) by starting
                // UserInit thread
                ClientInit clientInit = new ClientInit(clientTable, fromClient, toClient);
                clientInit.start();

            }
        } catch (IOException e) {
            Report.error("IO error " + e.getMessage());

        }
    }
}

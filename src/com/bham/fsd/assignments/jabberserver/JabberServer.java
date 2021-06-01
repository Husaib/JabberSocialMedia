package com.bham.fsd.assignments.jabberserver;
import java.io.IOException;
import java.net.*;

public class JabberServer implements Runnable {

    private static ServerSocket serverSocket;
    private static final int PORT_NUM = 44444;
    private Socket clientSocket;
    private static JabberDatabase jdb = new JabberDatabase();

    public JabberServer() {
        new Thread(this).start();
    }

    public static void main(String[] args) throws Exception {

        JabberServer server = new JabberServer();

    }

    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(PORT_NUM);
            //serverSocket.setSoTimeout(300);
            serverSocket.setReuseAddress(true);
            while (true) {

                clientSocket = serverSocket.accept();
                ClientConnection client = new ClientConnection(clientSocket, jdb);
                new Thread(client).start();
                Thread.sleep(100);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}


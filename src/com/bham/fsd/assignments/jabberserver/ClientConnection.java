package com.bham.fsd.assignments.jabberserver;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class ClientConnection implements Runnable {

    private Socket socket;
    private JabberDatabase jdb;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String username;

    public ClientConnection(Socket socket, JabberDatabase jdb) {
        this.socket = socket;
        this.jdb = jdb;
    }

    @Override
    public void run() {

        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            JabberMessage request = (JabberMessage) ois.readObject();
            JabberMessage jm = null;
            while (true) {

                if (request.getMessage().startsWith("signin")) {
                    username = request.getMessage().split(" ")[1];
                    if (jdb.getUserID(username) == -1) {
                        jm = new JabberMessage("unknown-user");
                    }
                    else {
                        jm = new JabberMessage("signedin");
                    }
                }
                else if (request.getMessage().startsWith("register")) {
                    username = request.getMessage().split(" ")[1];
                    if (jdb.getUserID(username) == -1) {
                        jdb.addUser(username, username + "@gmail.com");
                        jm = new JabberMessage("signedin");
                    }
                }
                else if (request.getMessage().equals("signout")) {
                    socket.close();
                    break;
                }
                else if (request.getMessage().equals("timeline")) {
                    jm = new JabberMessage("timeline", jdb.getTimelineOfUserEx(username));
                }
                else if (request.getMessage().equals("users")) {
                    jm = new JabberMessage("users", jdb.getUsersNotFollowed(jdb.getUserID(username)));
                }
                else if (request.getMessage().startsWith("post")) {
                    jdb.addJab(username, request.getMessage().replaceFirst("post ", ""));
                    jm = new JabberMessage("posted");
                }
                else if (request.getMessage().startsWith("like")) {
                    jdb.addLike(jdb.getUserID(username), Integer.parseInt(request.getMessage().split(" ")[1]));
                    jm = new JabberMessage("posted");
                }
                else if (request.getMessage().startsWith("follow")) {
                    jdb.addFollower(jdb.getUserID(username), request.getMessage().split(" ")[1]);
                    jm = new JabberMessage("posted");
                }
                else {
                    continue;
                }
                oos.writeObject(jm);
                oos.flush();
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }

}






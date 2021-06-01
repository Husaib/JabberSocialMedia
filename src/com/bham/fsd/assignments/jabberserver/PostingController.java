package com.bham.fsd.assignments.jabberserver;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PostingController implements Initializable{
	@FXML private TextField T2;
	@FXML private Button B6;
	public int PORT = 44444;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	public void sendJabToServer(ActionEvent event) {
		String jab = "post " + T2.getText();
		JabberMessage jm = new JabberMessage(jab);

		try {
			
			
			//writing signin username protocol to server
			BufferedReader inFromServer = null;
			Socket socket = new Socket("localhost", PORT);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(jm);
			//System.out.println(jm.getMessage());
			oos.reset();
			
			
            // reading from server 
         // inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			
          

	        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
	        JabberMessage request = (JabberMessage) ois.readObject();
	        String MsgReceived = request.getMessage();
	        //System.out.println(MsgReceived);
          //Receive Msg from the server 

          //String MsgReceived = inFromServer.readLine(); 
          
	}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void disconnectClient(ActionEvent event) {
		String message = "signout";
		JabberMessage jm = new JabberMessage(message);
		try {
			
			//writing signout protocol to server
			Socket socket = new Socket("localhost", PORT);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(jm);
			oos.flush();
			socket.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);

	}

}

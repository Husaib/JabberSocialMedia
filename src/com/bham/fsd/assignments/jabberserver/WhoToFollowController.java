package com.bham.fsd.assignments.jabberserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WhoToFollowController implements Initializable{
	public int PORT = 44444;
	@FXML private Label listOfUsers;
	@FXML private VBox vbox;
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Socket socket = new Socket("localhost", PORT);
	  	    String requestWhoToFollow = "users";
			JabberMessage jm1 = new JabberMessage(requestWhoToFollow);
			ObjectOutputStream oos1 = new ObjectOutputStream(socket.getOutputStream());
			oos1.writeObject(jm1);
			oos1.reset();
			jm1.getData();
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			JabberMessage request2 = (JabberMessage) ois.readObject();
			String MsgReceived1 = request2.getMessage();
			//System.out.println("hi " + request2.getData());
			
	  	  	//VBox vbox = new VBox();
	  	  	vbox.setSpacing(5);
	  	  	ArrayList<ArrayList<String>> jah = request2.getData();
	  	  	
	  	  	Image plus = new Image(getClass().getResourceAsStream("plus.png"));
	  	  	ImageView imageView = new ImageView(plus);
	  	  	//Button bt1 = new Button("", imageView);
			
			for (int i = 0; i < jah.size(); i++) {
				//vbox.getChildren().add(new Label(jah.get(i).get(0), new Button("", new ImageView(plus))));
				Button btn = new Button();
				btn.setGraphic(imageView);
			    btn.setOnAction((ActionEvent)->{
			    	try {
				  	    String followUser = "follow ";
						JabberMessage jm2 = new JabberMessage(followUser);
						oos1.writeObject(jm2);
						oos1.reset();
						jm1.getData();
						JabberMessage request3 = (JabberMessage) ois.readObject();
						String MsgReceived2 = request3.getMessage();
			    	}
			    	catch(Exception e) {
			    		e.printStackTrace();
			    	}
			        
			    });
			    vbox.getChildren().add(new Label(jah.get(i).get(0), btn));

			}
			
		
	}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void followUser(ActionEvent event) {
		
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

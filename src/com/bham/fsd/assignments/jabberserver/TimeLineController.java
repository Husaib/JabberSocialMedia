package com.bham.fsd.assignments.jabberserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TimeLineController implements Initializable{
	public int PORT = 44444;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			Socket socket = new Socket("localhost", PORT);
	  	    String requestTimeLine = "timeline";
			JabberMessage jm1 = new JabberMessage(requestTimeLine);
			ObjectOutputStream oos1 = new ObjectOutputStream(socket.getOutputStream());
			oos1.writeObject(jm1);
			oos1.reset();
			jm1.getData();
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			JabberMessage request2 = (JabberMessage) ois.readObject();
			String MsgReceived1 = request2.getMessage();
			//System.out.println("hi " + MsgReceived1);
			//System.out.println("jj" + request2.getData());
	  	  
	  	  
	  	  	VBox vbox = new VBox();
	  	  	vbox.setSpacing(5);
	  	  
	  	  //root.getChildren().add(button);
	  	  
	  	  
	          
	  	  	//System.out.println(request2.getData().get(0).get(0));
	  	  	ArrayList<ArrayList<String>> jah = request2.getData();
	  	  	ArrayList<ArrayList<String>> arrayList = new ArrayList<ArrayList<String>>();
	  	  	ArrayList<String> a1 = new ArrayList<String>();
	  	  	a1.add("Hello");
	  	  	a1.add("Joe");
	  	  	//System.out.println(jah.get(0).get(0));
	  	  	//a1.add(jm1.getData().get(0).get(0));
	  	  	arrayList.add(a1);
	  	  	vbox.getChildren().add(new Label(arrayList.get(0).get(0)));
	  	  	vbox.getChildren().add(new Label(arrayList.get(0).get(1)));
			  //vbox.getChildren().add(new Label(arrayList.get(0).get(2)));
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

package com.bham.fsd.assignments.jabberserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainController {
	@FXML private Label successLogin;
	@FXML private Label timeline;
	@FXML private Button B1;
	@FXML private Button B2;
	@FXML private Button B3;
	@FXML private Button likeJab;
	@FXML private TextField T1;
	@FXML private Pane P1;
	//@FXML private VBox vbox;
	
	public int PORT = 44444;
	
	
	public void signInUser(ActionEvent event) {
		String username = "signin " + T1.getText();
		JabberMessage jm = new JabberMessage(username);

		try {
			
			//writing signin username protocol to server
			BufferedReader inFromServer = null;
			Socket socket = new Socket("localhost", PORT);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(jm);
			oos.reset();
			
            // reading from server 
         // inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			
          

	        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
	        JabberMessage request = (JabberMessage) ois.readObject();
	        String MsgReceived = request.getMessage();
          //Receive Msg from the server 

          //String MsgReceived = inFromServer.readLine(); 
          
          if (MsgReceived.equals("signedin")) {
        	  successLogin.setText("User has successfully logged in");
        	  
			  Stage stage = new Stage();
			  FXMLLoader loader = new FXMLLoader();		//need a new FXML loader
			  Pane root = loader.load(getClass().getResource("PostingJab.FXML").openStream());		//no longer a parent, so change to Pane
			  PostingController pc = (PostingController)loader.getController();		//we have to cast the userController to a new instance
			  Scene scene = new Scene(root, 600, 600);
			  scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			  stage.setScene(scene);
			  stage.show();
        	
        	  //System.out.println("1" + MsgReceived);
        	  
			  Stage stage2 = new Stage();
			  FXMLLoader loader2 = new FXMLLoader();		//need a new FXML loader
			  Pane root2 = loader2.load(getClass().getResource("Timeline.FXML").openStream());		//no longer a parent, so change to Pane
			  TimeLineController tc = (TimeLineController)loader2.getController();		//we have to cast the userController to a new instance
			  Scene scene2 = new Scene(root2, 600, 600);
			  scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			  stage2.setScene(scene2);
			  stage2.show();
			  
			  Stage stage3 = new Stage();
			  FXMLLoader loader3 = new FXMLLoader();		//need a new FXML loader
			  Pane root3 = loader3.load(getClass().getResource("WhoToFollow.FXML").openStream());		//no longer a parent, so change to Pane
			  WhoToFollowController wtfc = (WhoToFollowController)loader3.getController();		//we have to cast the userController to a new instance
			  Scene scene3 = new Scene(root3, 600, 600);
			  scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			  stage3.setScene(scene3);
			  stage3.show();
        	  
        	  
        	  
	  		  //comment this out

	  		  
	  		  /*for (int i = 0; i < arrayList.size(); i++) {
	  			  vbox.getChildren().add(new Label(arrayList.get(i).get(0)));	//to add the name of the user to the vbox
	  			  vbox.getChildren().add(new Label(arrayList.get(i).get(1)));		//to add the text of the jab to the vbox
	  			  vbox.getChildren().add(new Label(arrayList.get(i).get(3)));	//to add the number of likes next to the button
	  			  vbox.getChildren().add(new Button ());     //to add the like button here with image included
	  		  }*/
          }
          if (MsgReceived.equals("unknown-user")) {
      		Alert E1 = new Alert(AlertType.INFORMATION);
    		E1.setTitle("Login Unsuccessful");
    		E1.setHeaderText(null);
    		E1.setContentText("The username either doesn't exist or is incorrect. Try again");

    		E1.showAndWait();
        	  
          }
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public void unSuccessUsername(ActionEvent event) {
		Alert E1 = new Alert(AlertType.INFORMATION);
		E1.setTitle("Login Unsuccessful");
		E1.setHeaderText(null);
		E1.setContentText("The username either doesn't exist or is incorrect. Try again");

		E1.showAndWait();
	}
	
	//RQ4 when B3 is pressed, call this method to remove client and close application.
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
	
	public void registerUser(ActionEvent event) {
		Alert E1 = new Alert(AlertType.INFORMATION);
		E1.setTitle("Registered");
		E1.setHeaderText(null);
		E1.setContentText("Successfully registered the user: " + T1.getText());

		E1.showAndWait();
		
		String username = "register " + T1.getText();
		JabberMessage jm = new JabberMessage(username);

		try {
			
			//writing register username protocol to server
			BufferedReader inFromServer = null;
			Socket socket = new Socket("localhost", PORT);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(jm);
			oos.flush();
			
	        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
	        JabberMessage request = (JabberMessage) ois.readObject();
	        String MsgReceived = request.getMessage();
          //Receive Msg from the server 

          //String MsgReceived = inFromServer.readLine(); 
          
          if (MsgReceived.equals("signedin")) {
      		try {
  			  Stage stage = new Stage();
  			  FXMLLoader loader = new FXMLLoader();		//need a new FXML loader
  			  Pane root = loader.load(getClass().getResource("PostingJab.FXML").openStream());		//no longer a parent, so change to Pane
  			  PostingController pc = (PostingController)loader.getController();		//we have to cast the userController to a new instance
  			  Scene scene = new Scene(root, 600, 600);
  			  scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
  			  stage.setScene(scene);
  			  stage.show();
          	
          	  //System.out.println("1" + MsgReceived);
          	  
  			  Stage stage2 = new Stage();
  			  FXMLLoader loader2 = new FXMLLoader();		//need a new FXML loader
  			  Pane root2 = loader2.load(getClass().getResource("Timeline.FXML").openStream());		//no longer a parent, so change to Pane
  			  TimeLineController tc = (TimeLineController)loader2.getController();		//we have to cast the userController to a new instance
  			  Scene scene2 = new Scene(root2, 600, 600);
  			  scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
  			  stage2.setScene(scene2);
  			  stage2.show();
  			  
  			  Stage stage3 = new Stage();
  			  FXMLLoader loader3 = new FXMLLoader();		//need a new FXML loader
  			  Pane root3 = loader3.load(getClass().getResource("WhoToFollow.FXML").openStream());		//no longer a parent, so change to Pane
  			  WhoToFollowController wtfc = (WhoToFollowController)loader3.getController();		//we have to cast the userController to a new instance
  			  Scene scene3 = new Scene(root3, 600, 600);
  			  scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
  			  stage3.setScene(scene3);
  			  stage3.show();
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
        	  
          }
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		

	}

}

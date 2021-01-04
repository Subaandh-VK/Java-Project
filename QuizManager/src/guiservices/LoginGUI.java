package guiservices;

import java.sql.SQLException;

import GuiLauncher.InitializeGUI;
import datamodel.Quiz;
import guiservices.LoginGUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import services.AuthenticationService;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LoginGUI{


	public static Scene login() {
		Button button = new Button();
		TextField username = new TextField();
		PasswordField password = new PasswordField();
		Text result = new Text();
		Label user = new Label("Username:");
		Label pass = new Label("Password:");

		
		System.out.println("**********");
		button.setText("Login as Student only");
		AuthenticationService auth = new AuthenticationService();
		button.setOnAction(e-> {
		    try {
				if (!(username.getText().isEmpty() || password.getText().isEmpty()) && (auth.authenticate(username.getText(), password.getText(), "STUDENT"))) {
				    result.setFill(Color.GREEN);
				    result.setText("Login succesfull");
				    InitializeGUI.switchScenes("login", username.getText());
				} else {
				    result.setFill(Color.RED);
				    if (username.getText().isEmpty()) {
				        result.setText("Please enter a username");
				    } else if (password.getText().isEmpty()){
				        result.setText("Please enter a password");
				    } else {
				    	result.setText("Invalid credentials");
				    }
				}
			} catch (SQLException e1) {
				System.out.println("Exception in LoginGUI");
				e1.printStackTrace();
			}
		});

		VBox root = new VBox(20);
		root.getChildren().addAll(user, username, pass, password, button, result);
		Scene scene = new Scene(root, 500, 500);
		
		return scene;
	}
}

package GuiLauncher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import dao.QuestionsDAO;
import dao.StudentDAO;
import datamodel.Quiz;
import datamodel.Student;
import guiservices.LoginGUI;
import guiservices.QuizGUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import logging.FileOperations;
import services.AuthenticationService;
import services.QuizServices;
import services.StudentServices;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class InitializeGUI extends Application {
	private static Stage window;
	private static Scene login, topic, difficulty, quiz, marks;
	private static Student student;
	private static Quiz selectedquiz;
	private static String loggedname , selectedtopic;
	private static int selecteddifficulty;
	private static int score = 0;
	
	@Override
	public void start(Stage stage) throws SQLException {
		
		try {
			if (!FileOperations.readConfig()) {
				System.out.println("Please give details in the config.properties file");
				System.out.println("Give proper values for postgrespath, postgresuser ,postgrespass");
				System.exit(0);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		window = stage;		        
		login = LoginGUI.login();
		topic = QuizGUI.topic();
		difficulty = QuizGUI.difficulty();
		
		window.setTitle("Quiz Manager");
		window.setScene(login);
		window.show();
	}

	public static void switchScenes(String event, String args) throws SQLException {
		StudentDAO sdao = new StudentDAO();
		QuestionsDAO qdao = new QuestionsDAO();
		
		if (event.equals("login")) {
			loggedname = args;
			student = StudentServices.getStudentDetails(sdao, loggedname);
			window.setScene(topic);
		} else if(event.equals("topic")) {
			selectedtopic = args;
			window.setScene(difficulty);
		} else if (event.equals("difficulty")) {
			selecteddifficulty = Integer.parseInt(args);
			selectedquiz = QuizServices.buildQuiz(qdao, selectedtopic, selecteddifficulty);
			student.setQuiz(selectedquiz);
			System.out.println("SETTING QUIZ SUCCESSFUL");
			quiz = QuizGUI.buildQuiz(student);
			window.setScene(quiz);
		} else if (event.equals("score")) {
			score++;
		} else if (event.equals("finish")) {
			marks = QuizGUI.displayResult(score);
			window.setScene(marks);
			System.out.println("Test Completed");
		}
	}

	public static void main (String[] args) {
		launch(args);
	}
}

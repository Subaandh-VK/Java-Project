package guiservices;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;

import GuiLauncher.InitializeGUI;
import dao.QuestionsDAO;
import datamodel.Questions;
import datamodel.Quiz;
import datamodel.Student;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import services.QuizServices;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ScrollPane;

public class QuizGUI {

	public static Scene displayResult(int score) throws SQLException {
		Text res = new Text("Your Score is " + score);

		VBox root = new VBox(20);
		root.getChildren().add(res);
		Scene scene = new Scene(root, 300, 300);

		return scene;

	}

	public static Scene buildQuiz(Student student) throws SQLException {
		ArrayList<MCQquestion> mcqlist = new ArrayList<>();
		ArrayList<OpenQuestions> openlist = new ArrayList<>();
		Button finish = new Button("Finish Test");
		Quiz quiz = student.getQuiz();

		System.out.println("QUIZ LENGTH " + quiz.getQuestions().size());
		for (Questions q : quiz.getQuestions()) {
			System.out.println("QUESTION AND TYPE" + q.getQuestions() + q.getType());
			if (q.getType().equalsIgnoreCase("mcq") && q.getChoices() != null) {
				MCQquestion mcq = new MCQquestion();
				ArrayList<RadioButton> radios = new ArrayList<>();
				ToggleGroup toggler = new ToggleGroup();
				Button button = new Button("Submit");
				Label qn = new Label(q.getQuestions());
				Text res = new Text();

				button.setDisable(true);

				for (String choice : q.getChoices()) {
					RadioButton option = new RadioButton(choice);
					option.setToggleGroup(toggler);
					option.setOnAction(e -> button.setDisable(false));
					radios.add(option);
				}

				button.setOnAction(e -> {
					RadioButton value = (RadioButton) toggler.getSelectedToggle();
					if (value.getText().equalsIgnoreCase(q.getAnswer())) {
						res.setFill(Color.GREEN);
						res.setText("Correct answer");
						button.setDisable(true);
						try {
							InitializeGUI.switchScenes("score", null);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} else {
						res.setFill(Color.RED);
						res.setText("Correct answer: " + q.getAnswer());
						button.setDisable(true);
					}
				});

				mcq.setButton(button);
				mcq.setAnswer(q.getAnswer());
				mcq.setRadios(radios);
				mcq.setQuestion(qn);
				mcq.setToggler(toggler);
				mcq.setResult(res);

				// Adding everything to the object
				mcqlist.add(mcq);
			} else {
				System.out.println("IN ELSE PART");
				OpenQuestions open = new OpenQuestions();

				Label qn = new Label(q.getQuestions());
				TextField response = new TextField();
				Button button = new Button("Submit");
				Text res = new Text();

				button.setOnAction(e -> {

					if (response.getText().equalsIgnoreCase(q.getAnswer())) {
						res.setFill(Color.GREEN);
						res.setText("Correct answer");
						button.setDisable(true);
						try {
							InitializeGUI.switchScenes("score", null);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						res.setFill(Color.RED);
						res.setText("Correct answer: " + q.getAnswer());
						button.setDisable(true);
					}
				});

				open.setQuestion(qn);
				open.setResponse(response);
				open.setResult(res);
				open.setButton(button);
				open.setAnswer(q.getAnswer());

				openlist.add(open);
			}
		}

		VBox root = new VBox(20);
		for (MCQquestion m : mcqlist) {
			root.getChildren().add(m.getQuestion());
			for (RadioButton b : m.getRadios()) {
				root.getChildren().add(b);
			}
			root.getChildren().add(m.getButton());
			root.getChildren().add(m.getResult());
		}

		for (OpenQuestions o : openlist) {
			System.out.println("OPENLIST ITERATING");
			root.getChildren().add(o.getQuestion());
			root.getChildren().add(o.getResponse());
			root.getChildren().add(o.getButton());
			root.getChildren().add(o.getResult());
		}

		finish.setOnAction(e -> {
			try {
				InitializeGUI.switchScenes("finish", null);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		root.getChildren().add(finish);
		ScrollPane scroll = new ScrollPane();
		scroll.setContent(root);
		scroll.setPannable(true);

		Scene scene = new Scene(scroll, 300, 300);

		return scene;
	}

	public static Scene difficulty() throws SQLException {
		ToggleGroup toggler = new ToggleGroup();
		RadioButton easy = new RadioButton("Easy");
		RadioButton medium = new RadioButton("Medium");
		RadioButton hard = new RadioButton("Hard");
		Button button = new Button();
		Text result = new Text();

		easy.setToggleGroup(toggler);
		medium.setToggleGroup(toggler);
		hard.setToggleGroup(toggler);
		toggler.selectToggle(easy);

		button.setText("Confirm");
		button.setOnAction(e -> {
			RadioButton value = (RadioButton) toggler.getSelectedToggle();
			System.out.println(value.getText());
			if (value.getText() != null) {
				result.setFill(Color.GREEN);
				result.setText("Difficulty Selected");
				System.out.println("DIFFICULTY - Success");
				try {
					if (value.getText().equals("hard")) {
						InitializeGUI.switchScenes("difficulty", "3");
					} else if (value.getText().equals("medium")) {
						InitializeGUI.switchScenes("difficulty", "2");
					} else {
						InitializeGUI.switchScenes("difficulty", "1");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				System.out.println("DIFFICULTY - Failed");
			}

		});

		VBox root = new VBox(20);
		Scene scene = new Scene(root, 500, 500);
		root.getChildren().addAll(easy, medium, hard, button);

		return scene;
	}

	public static Scene topic() throws SQLException {
		QuestionsDAO qdao = new QuestionsDAO();

		TreeSet<String> topics = QuizServices.getTopics(qdao);
		ArrayList<RadioButton> radioboxes = new ArrayList<>();
		ToggleGroup toggler = new ToggleGroup();
		Text result = new Text();

		for (String topic : topics) {
			RadioButton radio = new RadioButton(topic);
			radio.setToggleGroup(toggler);
			radioboxes.add(radio);
			toggler.selectToggle(radio);
		}

		Button button = new Button();
		button.setText("Confirm");

		button.setOnAction(e -> {
			RadioButton value = (RadioButton) toggler.getSelectedToggle();
			System.out.println(value.getText());
			if (value.getText() != null) {
				result.setFill(Color.GREEN);
				result.setText("Topic Selected");
				System.out.println("TOPIC - Success");
				try {
					InitializeGUI.switchScenes("topic", value.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				System.out.println("TOPIC - Failed");
			}
		});

		VBox root = new VBox(20);

		for (RadioButton box : radioboxes) {
			root.getChildren().add(box);
		}
		root.getChildren().add(button);

		Scene scene = new Scene(root, 500, 500);

		return scene;
	}
}

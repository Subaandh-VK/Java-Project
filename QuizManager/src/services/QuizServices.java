package services;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.TreeSet;

import dao.QuestionsDAO;
import datamodel.Questions;
import datamodel.Quiz;
import datamodel.Student;

public class QuizServices {

	private static Scanner in = new Scanner(System.in);
	
	public static String checkTopic(TreeSet<String> topics) {	
		System.out.println("Select a topic");
		String userTopic = in.nextLine();
		for (String topic : topics) {
			if (!userTopic.equalsIgnoreCase(topic))
				continue;
			
			System.out.println("Selected topic is: "+topic);
			return topic;
		}
		return null;
	}
	public static Quiz buildQuiz(QuestionsDAO qdao, String topic) throws SQLException {
		Quiz quiz = new Quiz();
		int difficulty = 0;

		System.out.println("Enter difficulty level easy/medium/hard");
		String diffchoice = in.nextLine();
		if (diffchoice.equalsIgnoreCase("easy") || diffchoice.equalsIgnoreCase("medium") || diffchoice.equalsIgnoreCase("hard")) {
			if (diffchoice.equalsIgnoreCase("easy")) {
				difficulty = 1;
			} else if (diffchoice.equalsIgnoreCase("medium")) {
				difficulty = 2;
			} else {
				difficulty = 3;
			}
		}

		quiz.setTitle(topic + " quiz");
		quiz.setQuestions(qdao.search(topic, difficulty, null));

		return quiz;
	}
	
	public static int startQuiz(Student student) {
		Quiz quiz = student.getQuiz();
		int score = 0;

		for (Questions q: quiz.getQuestions()) {
			System.out.println(q.getQuestions());
			if (q.getType().equalsIgnoreCase("mcq") && q.getChoices() != null) {
				int i = 97;
				for (String choice : q.getChoices()) {
					System.out.println((char)i+") "+choice);
					i++;
				}
			}
			
			String answer = in.nextLine();

			if (answer.equalsIgnoreCase(q.getAnswer())) {
				score++;
			}

			System.out.println("Correct Answer: "+q.getAnswer());
		}
		
		quiz.setScore(score);
		return quiz.getScore();
	}
	public static TreeSet<String> getTopics(QuestionsDAO qdao) throws SQLException {
		return qdao.getTopics();
	}
	public static void generateRandomQuiz() {
		//TODO
	}
}

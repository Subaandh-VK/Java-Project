package services;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.TreeSet;

import dao.QuestionsDAO;
import datamodel.Questions;
import datamodel.Quiz;
import datamodel.Student;
import logging.IamLog;

public class QuizServices {

	private static Scanner in = new Scanner(System.in);

	/**
	 * Student enters the topic and the function checks whether the selected topic
	 * is valid or not
	 * 
	 * @param topics The entered topic by user
	 * @return True if topic is valid else false
	 */
	public static String checkTopic(TreeSet<String> topics) {
		System.out.println("Select a topic");
		String userTopic = in.nextLine();
		for (String topic : topics) {
			if (!userTopic.equalsIgnoreCase(topic))
				continue;

			System.out.println("Selected topic is: " + topic);
			return topic;
		}
		return null;
	}

	/**
	 * To check if entered difficult is available
	 * 
	 * @return Return the selected difficulty by user
	 */
	public static int checkDifficulty() {
		int difficulty = 0;

		System.out.println("Enter difficulty level easy/medium/hard");
		String diffchoice = in.nextLine();
		if (diffchoice.equalsIgnoreCase("easy") || diffchoice.equalsIgnoreCase("medium")
				|| diffchoice.equalsIgnoreCase("hard")) {
			if (diffchoice.equalsIgnoreCase("easy")) {
				difficulty = 1;
			} else if (diffchoice.equalsIgnoreCase("medium")) {
				difficulty = 2;
			} else {
				difficulty = 3;
			}
		}

		return difficulty;
	}

	/**
	 * Build a quiz based on the input provided by the student
	 * 
	 * @param qdao The DAO object
	 * @param topic Selected topic
	 * @param difficulty Selected difficulty
	 * @return Quiz object is returned
	 * @throws SQLException handles exception
	 */
	public static Quiz buildQuiz(QuestionsDAO qdao, String topic, int difficulty) throws SQLException {
		Quiz quiz = new Quiz();

		quiz.setTitle(topic + " quiz");
		quiz.setQuestions(qdao.search(topic, difficulty, null));

		return quiz;
	}

	/**
	 * Displays the questions and verifies the answers
	 * 
	 * @param student The Student object with all parameters updated
	 * @param log To save the quiz to a file
	 * @return The score of the student
	 */
	public static int startQuiz(Student student, IamLog log) {
		Quiz quiz = student.getQuiz();
		int score = 0, num = 1;

		for (Questions q : quiz.getQuestions()) {
			log.save(num + ") " + q.getQuestions());
			if (q.getType().equalsIgnoreCase("mcq") && q.getChoices() != null) {
				int i = 97;
				for (String choice : q.getChoices()) {
					log.save((char) i + ")" + choice);
					i++;
				}
			}

			log.save("Enter your Answer: ");
			String answer = in.nextLine();

			if (answer.equalsIgnoreCase(q.getAnswer())) {
				score++;
			}

			num++;
			log.save("Correct Answer:" + q.getAnswer());
			log.save("\n");
		}
		quiz.setScore(score);
		return quiz.getScore();
	}

	/**
	 * Get available topic to display it to the user.
	 * 
	 * @param qdao The DAO object
	 * @return The Set of topics
	 * @throws SQLException handles exception
	 */
	public static TreeSet<String> getTopics(QuestionsDAO qdao) throws SQLException {
		return qdao.getTopics();
	}

}

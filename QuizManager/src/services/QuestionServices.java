package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import dao.QuestionsDAO;
import datamodel.Questions;

public class QuestionServices {
	private static Scanner in;

	public static void addQuestion(QuestionsDAO qdao) throws SQLException {
		in = new Scanner(System.in);
		Questions question = new Questions();

		System.out.println("Enter Question(What is ...): ");
		String qn = in.nextLine();
		System.out.println("Question type(MCQ/Open/Associative): ");
		String type = in.nextLine();
		System.out.println("Question topic(eg: java, programming): ");
		String topic = in.nextLine();
		System.out.println("Question difficulty(Integers only) 1-easy 2-medium 3-hard: ");
		int difficulty = in.nextInt();
		in.nextLine();
		System.out.println("Enter answer: ");
		String answer = in.nextLine();
		
		if (type.equalsIgnoreCase("MCQ")) {
			System.out.println("Enter 4 choices only");
			String[] choices = { in.nextLine(), in.nextLine(), in.nextLine(),in.nextLine() };
			question.setChoices(choices);
		} else {
			question.setChoices(null);
		}

		question.setQuestions(qn);
		question.setDifficulty(difficulty);
		question.setTopic(topic);
		question.setType(type);
		question.setAnswers(answer);
		
		if (qdao.create(question) == 1)
			System.out.println("CREATE Successful");
		else
			System.out.println("CREATE Failed");

	}

	public static void searchQuestion(QuestionsDAO qdao) throws SQLException {
		in = new Scanner(System.in);
		String topic = null, type = null;
		int difficulty = 0;

		System.out.println("Would you like to enter topic Y/N");
		String choice = "Y";
		
		if (choice.equalsIgnoreCase(in.nextLine())) {
			System.out.println("Enter Topic(java, programming");
			topic = in.nextLine();
		}

		System.out.println("Would you like to enter difficulty Y/N");
		if (choice.equalsIgnoreCase(in.nextLine())) {
			System.out.println("Enter Difficulty (1\2\3)");
			difficulty = in.nextInt();
			in.nextLine();
		}
		
		System.out.println("Would you like to enter type Y/N");
		if (choice.equalsIgnoreCase(in.nextLine())) {
			System.out.println("Enter Question type (MCQ, open, associative)");
			type = in.nextLine();
		}
		
		ArrayList<Questions> questions  = qdao.search(topic, difficulty, type);
		
		for (Questions q : questions) {
			System.out.println(q.toString());
		}
	}
	
	public static void updateQuestion(QuestionsDAO qdao) throws SQLException {
		in = new Scanner(System.in);
		
		ArrayList<Questions> questions = qdao.search(null, 0, null);
		
		for (Questions q: questions) {
			System.out.println(q.toString());
		}
		
		System.out.println("Enter question ID to UPDATE");
		int id = in.nextInt();
		in.nextLine();

		for (Questions q: questions) {
			if (id == q.getId()) {
				System.out.println("Selected Question: \n"+q.toString());
				
				System.out.println("Would you like to update question Y/N");
				String choice = "Y";
				
				if (choice.equalsIgnoreCase(in.nextLine())) {
					System.out.println("Enter Question");
					String question = in.nextLine();
					q.setQuestions(question);
				}
				
				System.out.println("update answers Y/N");
				if (choice.equalsIgnoreCase(in.nextLine())) {
					System.out.println("Enter answer:");
					String answer = in.nextLine();
					q.setAnswers(answer);
				}
				
				System.out.println("update topic Y/N");
				
				if (choice.equalsIgnoreCase(in.nextLine())) {
					System.out.println("Enter Topic(java, programming");
					String topic = in.nextLine();
					q.setTopic(topic);
				}

				System.out.println("update difficulty Y/N");
				if (choice.equalsIgnoreCase(in.nextLine())) {
					System.out.println("Enter Difficulty (1,2,3)");
					int difficulty = in.nextInt();
					in.nextLine();
					q.setDifficulty(difficulty);
				}
				
				System.out.println("update type Y/N");
				if (choice.equalsIgnoreCase(in.nextLine())) {
					System.out.println("Enter Question type (MCQ, open, associative)");
					String type = in.nextLine();
					q.setType(type);
				}
				
				if (q.getType().equalsIgnoreCase("MCQ")) {
					System.out.println("update choices Y/N");
					if (choice.equalsIgnoreCase(in.nextLine())) {
						System.out.println("Enter 3 choices");
						String[] choices = {in.nextLine(), in.nextLine(), in.nextLine()};
						q.setChoices(choices);
					}
				}
				
				System.out.println("To be UPDATED as: "+q.toString());
				if (qdao.update(q) == 1)
					System.out.println("UPDATE Successful");
				else
					System.out.println("UPDATE Failed");
			}
		}
	}
	
	public static void deleteQuestion(QuestionsDAO qdao) throws SQLException {
		in = new Scanner(System.in);
		
		ArrayList<Questions> questions = qdao.search(null, 0, null);
		
		for (Questions q: questions) {
			System.out.println(q.toString());
		}
		
		System.out.println("Enter question ID to DELETE");
		int id = in.nextInt();
		
		if (qdao.delete(id) == 1)
			System.out.println("DELETE Successful");
		else
			System.out.println("DELETE Failed");
	}
	
}

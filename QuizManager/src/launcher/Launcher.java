package launcher;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeSet;

import dao.QuestionsDAO;
import dao.StudentDAO;
import datamodel.Quiz;
import datamodel.Student;
import services.AuthenticationService;
import services.QuestionServices;
import services.QuizServices;
import services.StudentServices;

public class Launcher {

	public static void main(String[] args) throws SQLException {
		Scanner in = new Scanner(System.in);

		while (true) {
			System.out.println("Welcome to Quiz Manager");
			System.out.println("Login as Student PRESS 1 \nLogin as Admin PRESS 2 \nExit PRESS 3 ");

			int choice = 0;
			String username, password, user;
			try {
				choice = in.nextInt();
				in.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input " + e);
			}

			if (choice == 1 || choice == 2) {
				System.out.println("Enter UserName and Password");
				username = in.nextLine();
				password = in.nextLine();

				if (choice == 1) {
					user = "STUDENT";
				} else {
					user = "ADMIN";
				}
			} else {
				System.out.println("Exiting");
				break;
			}

			AuthenticationService auth = new AuthenticationService();
			
			// Authentication module
			if (!auth.authenticate(username, password, user)) {
				System.out.println("Invalid credentials");
				continue;
			}

			QuestionsDAO qdao = new QuestionsDAO();
			StudentDAO sdao = new StudentDAO();

			if (user.equals("ADMIN")) {
				System.out.println("PRESS \n1.To perform CRUD operations on Questions \n2.To perform CRUD operations on Student details");
				
				choice = 0;
				choice = in.nextInt();
				in.nextLine();
				if (choice == 1 || choice == 2) {
					System.out.println("PRESS 1.Create 2.Search 3.Update 4. Delete");
					int operation = in.nextInt();

					switch (operation) {
					case 1: // Create Operations
						if (choice == 1)
							QuestionServices.addQuestion(qdao);
						else
							StudentServices.addStudent(sdao);
						
						break;
					case 2: // Read/Search operations
						if (choice == 1)
							QuestionServices.searchQuestion(qdao);
						else
							StudentServices.searchStudent(sdao);
						break;
					case 3: // Update Operations
						if (choice == 1)
							QuestionServices.updateQuestion(qdao);
						else
							StudentServices.updateStudent(sdao);
						break;
					case 4: // Delete Operations
						if (choice == 1)
							QuestionServices.deleteQuestion(qdao);
						else
							StudentServices.deleteStudent(sdao);
						break;
					}
				} else {
					System.out.println("Invalid choice");
					continue;
				}
			} else {
				Student student = StudentServices.getStudentDetails(sdao, username);


				System.out.println("******Welcome "+student.getName()+"*********");
				
				TreeSet<String> topics = QuizServices.getTopics(qdao);
				System.out.println("* Availabe Topics *");
				for (String topic : topics)
					System.out.println(topic);
				
				String usertopic = QuizServices.checkTopic(topics);
				if (usertopic == null) {
					System.out.println("Invalid Topic");
					continue;
				}
				
				Quiz quiz = QuizServices.buildQuiz(qdao, usertopic);

				student.setQuiz(quiz);
				QuizServices.startQuiz(student);
				System.out.println("Quiz Ended Your Score is: "+student.getQuiz().getScore());
					
			}
		}
		in.close();
	}
}

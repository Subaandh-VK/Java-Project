package launcher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeSet;

import dao.QuestionsDAO;
import dao.StudentDAO;
import datamodel.Quiz;
import datamodel.Student;
import logging.FileOperations;
import logging.IamLog;
import services.AuthenticationService;
import services.QuestionServices;
import services.QuizServices;
import services.StudentServices;

public class Launcher {

	public static void main(String[] args) throws SQLException {
		Scanner in = new Scanner(System.in);

		QuestionsDAO qdao = new QuestionsDAO();
		StudentDAO sdao = new StudentDAO();
		
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
		System.out.println("Database Path and Credentials: "+ qdao.getConnectionPath()+" "+ qdao.getConnectionUser()+
				" "+qdao.getConnectionPass());
		System.out.println("Result Writepath: " + FileOperations.getWritepath());
		
		
		while (true) {
			System.out.println("\n\n**********Welcome to Quiz Manager************");
			System.out.println("PRESS 1.To Login as Student \n2.To Login as Admin \n3.To Exit");


			int choice = 0;
			String username = null, password = null, user = null;
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
				}
			} else {
				Student student = StudentServices.getStudentDetails(sdao, username);
				IamLog log = new IamLog("");

				System.out.println("******Welcome "+student.getName()+"*********");
				
				TreeSet<String> topics = QuizServices.getTopics(qdao);
				System.out.println("** Availabe Topics **");
				for (String topic : topics)
					System.out.println(topic);
				
				String usertopic = QuizServices.checkTopic(topics);
				if (usertopic == null) {
					System.out.println("Invalid Topic");
					continue;
				}
				
				int difficulty = QuizServices.checkDifficulty();
				Quiz quiz = QuizServices.buildQuiz(qdao, usertopic, difficulty);

				student.setQuiz(quiz);
				QuizServices.startQuiz(student, log);
				log.save("Quiz Ended Your Score is: "+student.getQuiz().getScore());
			}
		}
		in.close();
	}
}
	

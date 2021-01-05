package services;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.QuestionsDAO;
import dao.StudentDAO;
import datamodel.Questions;
import datamodel.Student;

public class TestServices {
	public static void runTests(QuestionsDAO qdao, StudentDAO sdao) throws SQLException {
		System.out.println("******** Starting basic functionality test*********");
		if (sdao == null || qdao == null) {
			System.out.println("The DAO values are Null Run the tests again");
			return;
		}

		System.out.println("The Data Access objects created Successfully");
		System.out.println("Testing QuestionDAO functionality......");
		System.out.println("Doing search Operation on questions");

		/**
		 * The search is done with null as it is a auto generated test if the user can
		 * also do a manual search
		 */
		ArrayList<Questions> questions = qdao.search(null, 0, null);

		if (questions.size() == 0) {
			System.out.println("The Question List is empty Run test again");
		}

		for (Questions q : questions) {
			System.out.println(q.toString());
		}

		ArrayList<Student> student = sdao.search(null, null, 0);

		if (questions.size() == 0) {
			System.out.println("The Student List is empty Run test again");
		}

		for (Student s : student) {
			System.out.println(s.toString());
		}

		System.out.println("All Tests passed Successfully");
	}
}

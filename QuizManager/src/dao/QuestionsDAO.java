package dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;

import datamodel.Questions;

public class QuestionsDAO {
	public static String connectionPath;
	public static String connectionUser;
	public static String connectionPass;

	/**
	 * Create a question and add it in the database
	 * 
	 * @param question object which contains values to be created
	 * @return status of execute query
	 * @throws SQLException handles SQL exception
	 */
	public int create(Questions question) throws SQLException {
		Connection connection;
		String query;
		int ret;

		connection = DriverManager.getConnection(connectionPath, connectionUser, connectionPass);
		query = "INSERT INTO public.\"QUESTIONS\"(question, type, difficulty, topic, answer, choices) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement prepareStatement = connection.prepareStatement(query);
		prepareStatement.setString(1, question.getQuestions());
		prepareStatement.setString(2, question.getType());
		prepareStatement.setInt(3, question.getDifficulty());
		prepareStatement.setString(4, question.getTopic());
		prepareStatement.setString(5, question.getAnswer());

		if (question.getType().equals("MCQ")) {
			Array array = connection.createArrayOf("VARCHAR", question.getChoices());
			prepareStatement.setArray(6, array);
		} else {
			prepareStatement.setArray(6, null);
		}

		ret = prepareStatement.executeUpdate();
		connection.close();

		return ret;
	}

	/**
	 * @param topic The topic to be searched
	 * @param difficulty the difficulty level of the questions
	 * @param type the type of question like MCQ or open
	 * @return status of execute query
	 * @throws SQLException handles sql exception
	 */
	public ArrayList<Questions> search(String topic, int difficulty, String type) throws SQLException {
		ArrayList<Questions> questionList = new ArrayList<>();
		Connection connection;
		String query;
		int searchFields = 0;

		connection = DriverManager.getConnection(connectionPath, connectionUser, connectionPass);

		query = "select * from \"QUESTIONS\""; // topic = ? and difficulty = ?

		/*
		 * Alter the query based on the search values provided For example : If user
		 * searches questions with difficulty 1 The query is --> select * from
		 * \"QUESTIONS\ WHERE difficulty = ?
		 * 
		 * If the user wants multiple to search with multiple variables the and part is
		 * appended For example: If user searches with topic java and difficulty 1 The
		 * query is --> select * from \"QUESTIONS\ WHERE topic = ? and difficulty = ?
		 * 
		 */
		if (topic != null) {
			query += " WHERE topic = ?";
			searchFields++;
		}
		if (difficulty != 0) {
			if (searchFields == 0) {
				query += " WHERE difficulty = ?";
				searchFields++;
			} else {
				query += " and difficulty = ?";
				searchFields++;
			}
		}
		if (type != null) {
			if (searchFields == 0) {
				query += " WHERE type = ?";
				searchFields++;
			} else {
				query += " and type = ?";
				searchFields++;
			}
		}

		PreparedStatement prepareStatement = connection.prepareStatement(query);

		// These condition checks are to set values for the ? in the query
		if (type != null) {
			prepareStatement.setString(searchFields, type);
			searchFields--;
		}
		if (difficulty != 0) {
			prepareStatement.setInt(searchFields, difficulty);
			searchFields--;
		}
		if (topic != null) {
			prepareStatement.setString(searchFields, topic);
		}

		ResultSet results = prepareStatement.executeQuery();
		while (results.next()) {
			Questions q = new Questions();

			Array array = results.getArray("choices");
			if (array != null) {
				String[] choices = (String[]) array.getArray();
				q.setChoices(choices);
			}

			q.setDifficulty(results.getInt("difficulty"));
			q.setId(results.getInt("Id"));
			q.setQuestions(results.getString("question"));
			q.setTopic(results.getString("topic"));
			q.setType(results.getString("type"));
			q.setAnswers(results.getString("answer"));
			questionList.add(q);
		}

		connection.close();
		return questionList;
	}

	/**
	 * Update the selected question in the database based on the input given
	 * 
	 * @param question the question object to be updated
	 * @return status of execute query
	 * @throws SQLException handle sql exception
	 */
	public int update(Questions question) throws SQLException {
		Connection connection;
		String query;
		int ret, updateFields = 0;

		connection = DriverManager.getConnection(connectionPath, connectionUser, connectionPass);
		query = "UPDATE public.\"QUESTIONS\"";

		if (question.getTopic() != null) {
			query += " SET topic = ?";
			updateFields++;
		}
		if (question.getDifficulty() != 0) {
			if (updateFields == 0) {
				query += " SET difficulty = ?";
				updateFields++;
			} else {
				query += ", difficulty = ?";
				updateFields++;
			}
		}
		if (question.getType() != null) {
			if (updateFields == 0) {
				query += " SET type = ?";
				updateFields++;
			} else {
				query += ", type = ?";
				updateFields++;
			}
		}

		if (question.getQuestions() != null) {
			if (updateFields == 0) {
				query += " SET question = ?";
				updateFields++;
			} else {
				query += ", question = ?";
				updateFields++;
			}
		}
		if (question.getAnswer() != null) {
			if (updateFields == 0) {
				query += " SET answer = ?";
				updateFields++;
			} else {
				query += ", answer = ?";
				updateFields++;
			}
		}
		if (question.getChoices() != null) {
			if (updateFields == 0) {
				query += " SET choices = ?";
				updateFields++;
			} else {
				query += ", choices = ?";
				updateFields++;
			}
		}

		query += " WHERE id = ?";
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		prepareStatement.setInt(updateFields + 1, question.getId());

		if (question.getChoices() != null) {
			if (question.getType().equals("MCQ")) {
				Array array = connection.createArrayOf("VARCHAR", question.getChoices());
				prepareStatement.setArray(updateFields, array);
			} else {
				prepareStatement.setArray(updateFields, null);
			}

			updateFields--;
		}

		if (question.getAnswer() != null) {
			prepareStatement.setString(updateFields, question.getAnswer());
			updateFields--;
		}

		if (question.getQuestions() != null) {
			prepareStatement.setString(updateFields, question.getQuestions());
			updateFields--;
		}

		if (question.getType() != null) {
			prepareStatement.setString(updateFields, question.getType());
			updateFields--;
		}
		if (question.getDifficulty() != 0) {
			prepareStatement.setInt(updateFields, question.getDifficulty());
			updateFields--;
		}
		if (question.getTopic() != null) {
			prepareStatement.setString(updateFields, question.getTopic());
			updateFields--;
		}

		ret = prepareStatement.executeUpdate();

		connection.close();
		return ret;
	}

	/**
	 * Delete a question from the database based on the id
	 * 
	 * @param id the question id to delete
	 * @return status of execute query
	 * @throws SQLException handle exception
	 */
	public int delete(int id) throws SQLException {
		Connection connection;
		String query;
		int ret;

		connection = DriverManager.getConnection(connectionPath, connectionUser, connectionPass);
		query = "DELETE FROM public.\"QUESTIONS\" WHERE id = ?";

		PreparedStatement prepareStatement = connection.prepareStatement(query);
		prepareStatement.setInt(1, id);

		ret = prepareStatement.executeUpdate();

		connection.close();
		return ret;
	}

	/**
	 * Returns the list of unique topics as a TreeSet to display it to the student
	 * 
	 * @return treeset of topics
	 * @throws SQLException handles exception 
	 */
	public TreeSet<String> getTopics() throws SQLException {
		TreeSet<String> topics = new TreeSet<>();
		Connection connection;
		String query;

		connection = DriverManager.getConnection(connectionPath, connectionUser, connectionPass);
		query = "select * from \"QUESTIONS\"";

		PreparedStatement prepareStatement = connection.prepareStatement(query);

		ResultSet results = prepareStatement.executeQuery();
		while (results.next()) {
			topics.add(results.getString("topic"));
		}

		connection.close();
		return topics;
	}

	public static String getConnectionPath() {
		return connectionPath;
	}

	public static void setConnectionPath(String connectionPath) {
		QuestionsDAO.connectionPath = connectionPath;
	}

	public static String getConnectionUser() {
		return connectionUser;
	}

	public static void setConnectionUser(String connectionUser) {
		QuestionsDAO.connectionUser = connectionUser;
	}

	public static String getConnectionPass() {
		return connectionPass;
	}

	public static void setConnectionPass(String connectionPass) {
		QuestionsDAO.connectionPass = connectionPass;
	}

}

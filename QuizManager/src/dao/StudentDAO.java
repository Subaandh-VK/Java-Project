package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import datamodel.Student;

public class StudentDAO {
	public static String connectionPath;
	public static String connectionUsername;
	public static String connectionPassword;

	/**
	 * Create a student entry in the database
	 * 
	 * @param student The student object with filled values
	 * @return status of execute query
	 * @throws SQLException handle exception
	 */
	public int create(Student student) throws SQLException {
		Connection connection;
		String query;
		int ret;

		connection = DriverManager.getConnection(connectionPath, connectionUsername, connectionPassword);

		query = "INSERT INTO public.\"STUDENT\" (username, password, name, age, sex, address) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		prepareStatement.setString(1, student.getUsername());
		prepareStatement.setString(2, student.getPassword());
		prepareStatement.setString(3, student.getName());
		prepareStatement.setInt(4, student.getAge());
		prepareStatement.setString(5, student.getSex());
		prepareStatement.setString(6, student.getAddress());

		ret = prepareStatement.executeUpdate();

		connection.close();
		return ret;
	}

	/**
	 * Search for a student in the database based on the below parameters if nothing
	 * is given it returns all the students
	 * 
	 * @param username username of student
	 * @param name name of the student
	 * @param id id of the student
	 * @return return the student based on search
	 * @throws SQLException handle exception
	 */
	public ArrayList<Student> search(String username, String name, int id) throws SQLException {
		ArrayList<Student> studentList = new ArrayList<>();
		Connection connection;
		String query;
		int search = 0;

		connection = DriverManager.getConnection(connectionPath, connectionUsername, connectionPassword);

		query = "select * from \"STUDENT\" ";

		if (username != null) {
			query += "WHERE username = ?";
			search++;
		}
		if (name != null) {
			if (search == 0)
				query += "WHERE name = ?";
			else
				query += " and name = ?";

			search++;
		}
		if (id != 0) {
			if (search == 0)
				query += "WHERE id = ?";
			else
				query += "and id = ?";

			search++;
		}

		PreparedStatement prepareStatement = connection.prepareStatement(query);

		if (id != 0) {
			prepareStatement.setInt(search, id);
			search--;
		}
		if (name != null) {
			prepareStatement.setString(search, name);
			search--;
		}
		if (username != null) {
			prepareStatement.setString(search, username);
			search--;
		}

		ResultSet results = prepareStatement.executeQuery();
		while (results.next()) {
			Student student = new Student();

			student.setUsername(results.getString("username"));
			student.setPassword(results.getString("password"));
			student.setName(results.getString("name"));
			student.setAge(results.getInt("age"));
			student.setSex(results.getString("sex"));
			student.setAddress(results.getString("address"));
			student.setId(results.getInt("id"));

			studentList.add(student);
		}

		connection.close();
		return studentList;
	}

	/**
	 * This is a call used to verify student credentials when logging in.
	 * 
	 * @param username username of student
	 * @param password password of student
	 * @return status of authentication
	 * @throws SQLException handles exception
	 */
	public boolean authenticationSearch(String username, String password) throws SQLException {
		boolean found = false;
		Connection connection;
		String query;

		connection = DriverManager.getConnection(connectionPath, connectionUsername, connectionPassword);

		query = "select * from \"STUDENT\" WHERE username = ? and password = ?";
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		prepareStatement.setString(1, username);
		prepareStatement.setString(2, password);
		ResultSet results = prepareStatement.executeQuery();

		while (results.next()) {
			String name = results.getString("name");
			int age = results.getInt("age");
			String sex = results.getString("sex");
			String address = results.getString("address");

			found = true;
		}

		connection.close();
		return found;
	}

	/**
	 * Update the student details based on admin/user input
	 * 
	 * @param student Student object to be updated
	 * @return status of execute query
	 * @throws SQLException handles exception
	 */
	public int update(Student student) throws SQLException {
		Connection connection;
		String query;
		int ret = 0;

		connection = DriverManager.getConnection(connectionPath, connectionUsername, connectionPassword);
		query = "UPDATE public.\"STUDENT\" SET username=?, password=?, name=?, age=?, sex=?, address=? WHERE id = ?";

		PreparedStatement prepareStatement = connection.prepareStatement(query);
		prepareStatement.setString(1, student.getUsername());
		prepareStatement.setString(2, student.getPassword());
		prepareStatement.setString(3, student.getName());
		prepareStatement.setInt(4, student.getAge());
		prepareStatement.setString(5, student.getSex());
		prepareStatement.setString(6, student.getAddress());
		prepareStatement.setInt(7, student.getId());

		ret = prepareStatement.executeUpdate();

		connection.close();
		return ret;
	}

	/**
	 * Delete a student record from the database
	 * 
	 * @param student
	 * @return
	 * @throws SQLException
	 */
	public int delete(Student student) throws SQLException {
		Connection connection;
		String query;
		int ret = 0;

		connection = DriverManager.getConnection(connectionPath, connectionUsername, connectionPassword);
		query = "DELETE FROM public.\"STUDENT\" WHERE id = ?";

		PreparedStatement prepareStatement = connection.prepareStatement(query);

		prepareStatement.setInt(1, student.getId());

		ret = prepareStatement.executeUpdate();

		connection.close();
		return ret;
	}

	public static String getConnectionPath() {
		return connectionPath;
	}

	public static void setConnectionPath(String connectionPath) {
		StudentDAO.connectionPath = connectionPath;
	}

	public static String getConnectionUsername() {
		return connectionUsername;
	}

	public static void setConnectionUsername(String connectionUsername) {
		StudentDAO.connectionUsername = connectionUsername;
	}

	public static String getConnectionPassword() {
		return connectionPassword;
	}

	public static void setConnectionPassword(String connectionPassword) {
		StudentDAO.connectionPassword = connectionPassword;
	}
}

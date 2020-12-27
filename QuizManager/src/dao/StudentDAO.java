package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import datamodel.Student;

public class StudentDAO {
	public String connectionPath = "jdbc:postgresql://localhost:5432/Java-quiz";
	public String connectionUsername = "postgres";
	public String connectionPassword = "123";

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
		System.out.println("Status of create: " + ret);

		connection.close();
		return ret;
	}

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

			System.out.println("Student name : " + name + age + sex + address);
			found = true;
		}

		connection.close();
		return found;
	}

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
		System.out.println("Status of update" + ret);

		connection.close();
		return ret;
	}

	public int delete(Student student) throws SQLException {
		Connection connection;
		String query;
		int ret = 0;

		connection = DriverManager.getConnection(connectionPath, connectionUsername, connectionPassword);
		query = "DELETE FROM public.\"STUDENT\" WHERE id = ?";

		PreparedStatement prepareStatement = connection.prepareStatement(query);

		prepareStatement.setInt(1, student.getId());

		ret = prepareStatement.executeUpdate();
		System.out.println("Status of update: " + ret);

		connection.close();
		return ret;
	}
}

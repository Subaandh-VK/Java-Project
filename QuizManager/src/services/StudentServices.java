package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import dao.StudentDAO;
import datamodel.Student;

public class StudentServices {
	private static Scanner in;

	public static void addStudent(StudentDAO sdao) throws SQLException {
		in = new Scanner(System.in);
		Student student = new Student();

		System.out.println("Enter Student name");
		String name = in.nextLine();
		student.setName(name);

		System.out.println("Enter username");
		String username = in.nextLine();
		student.setUsername(username);

		System.out.println("Enter password");
		String password = in.nextLine();
		student.setPassword(password);

		System.out.println("Enter Student sex (M/F)");
		String sex = in.nextLine();
		student.setSex(sex);

		System.out.println("Enter Student Address");
		String address = in.nextLine();
		student.setAddress(address);

		System.out.println("Enter Age");
		int age = in.nextInt();
		in.nextLine();
		student.setAge(age);

		if (sdao.create(student) == 1)
			System.out.println("CREATE Successful");
		else
			System.out.println("CREATE Failed");
	}

	public static void searchStudent(StudentDAO sdao) throws SQLException {
		in = new Scanner(System.in);
		String username = null, name = null;
		int id = 0;
		
		System.out.println("Would you like to Update username Y/N");
		String choice = "Y";
		
		if (choice.equalsIgnoreCase(in.nextLine())) {
			System.out.println("Enter Username");
			username = in.nextLine();
		}
		
		System.out.println("Update Name Y/N");
		if (choice.equalsIgnoreCase(in.nextLine())) {
			System.out.println("Enter Name");
			name = in.nextLine();
		}
		
		System.out.println("Update Id Y/N");
		if (choice.equalsIgnoreCase(in.nextLine())) {
			System.out.println("Enter Id");
			id = in.nextInt();
			in.nextLine();
		}
		
		ArrayList<Student> student = sdao.search(username, name, id);
		
		for (Student s : student) {
			System.out.println(s.toString());
		}
	}

	public static void updateStudent(StudentDAO sdao) throws SQLException {
		in = new Scanner(System.in);
		ArrayList<Student> studentList = sdao.search(null, null, 0);
		
		for (Student s : studentList) {
			System.out.println(s.toString());
		}
		
		System.out.println("Select ID to update");
		int id = in.nextInt();
		in.nextLine();
		
		
		for (Student s: studentList) {
			if (id == s.getId()) {
				System.out.println("Selected Question: \n"+s.toString());
				
				System.out.println("Would you like to update username Y/N");
				String choice = "Y";
				
				if (choice.equalsIgnoreCase(in.nextLine())) {
					System.out.println("Enter username");
					String username = in.nextLine();
					s.setUsername(username);
				}
				
				System.out.println("update password Y/N");
				if (choice.equalsIgnoreCase(in.nextLine())) {
					System.out.println("Enter password:");
					String password = in.nextLine();
					s.setUsername(password);
				}
				
				System.out.println("update name Y/N");
				
				if (choice.equalsIgnoreCase(in.nextLine())) {
					System.out.println("Enter name");
					String name = in.nextLine();
					s.setName(name);
				}

				System.out.println("update age Y/N");
				if (choice.equalsIgnoreCase(in.nextLine())) {
					System.out.println("Enter age");
					int age = in.nextInt();
					in.nextLine();
					s.setAge(age);
				}
				
				System.out.println("update address Y/N");
				if (choice.equalsIgnoreCase(in.nextLine())) {
					System.out.println("Enter Address");
					String address = in.nextLine();
					s.setAddress(address);
				}	
				if (sdao.update(s) == 1)
					System.out.println("UPDATE Successful");
				else
					System.out.println("UPDATE Failed");
					
				break;
			}
		}
	}
	
	public static void deleteStudent(StudentDAO sdao) throws SQLException {
		in = new Scanner(System.in);
		ArrayList<Student> studentList = sdao.search(null, null, 0);
		
		for (Student s : studentList) {
			System.out.println(s.toString());
		}
		
		System.out.println("Select ID to Delete");
		int id = in.nextInt();
		in.nextLine();
		
		for (Student s: studentList) {
			if (id == s.getId()) {
				System.out.println("Student to be deleted "+s.toString());
				if (sdao.delete(s) == 1)
					System.out.println("DELETE Successful");
				else
					System.out.println("DELETE Failed");
				break;
			}
		}
	}
}

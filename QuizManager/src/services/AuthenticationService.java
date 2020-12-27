package services;

import java.sql.SQLException;

import dao.StudentDAO;

public class AuthenticationService {
	public boolean authenticate(String username, String password, String user) throws SQLException {
		
		if (user.equals("STUDENT")) {
			StudentDAO sdao = new StudentDAO();
			if (!sdao.authenticationSearch(username, password))
				return false;
		} else if (user.equals("ADMIN")) {
			if (!(username.equals("admin") && password.equals("admin")))
				return false;
		}
		
		System.out.println("Successfully authenticated");
		return true;
	}
}

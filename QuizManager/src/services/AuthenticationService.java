package services;

import java.sql.SQLException;

import dao.StudentDAO;

/**
 * @author Subaandh
 *
 */

public class AuthenticationService {

	private static final String ADMIN_USER = "admin";
	private static final String ADMIN_PWD = "admin";

	/**
	 * Authenticate the user based on whether he is a student or a admin
	 * 
	 * @param username The entered username by user
	 * @param password The entered password by user
	 * @param user The type of user
	 * @return status of authentication true/false
	 * @throws SQLException handles exception
	 */
	public boolean authenticate(String username, String password, String user) throws SQLException {

		if (user.equals("STUDENT")) {
			StudentDAO sdao = new StudentDAO();
			if (!sdao.authenticationSearch(username, password))
				return false;
		} else if (user.equals("ADMIN")) {
			if (!(username.equals(ADMIN_USER) && password.equals(ADMIN_PWD)))
				return false;
		} else {
			return false;
		}

		System.out.println("Successfully authenticated");
		return true;
	}
}

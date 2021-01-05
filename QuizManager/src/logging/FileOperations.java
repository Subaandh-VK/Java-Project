package logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import dao.QuestionsDAO;
import dao.StudentDAO;

public class FileOperations {
	private static LocalDateTime now = LocalDateTime.now();
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
	private static String writepath = System.getProperty("user.dir") ;

	/**
	 * Write the quiz output into the existing directory
	 * @param message
	 * @throws IOException
	 */
	public static void write(String message) throws IOException {
		String nowString = now.format(dateTimeFormatter);
		String filename = writepath + "//Results"+nowString+".txt";

		File file = new File(filename);

		boolean fileexists = file.exists();
		if (!fileexists) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter fileWriter = new FileWriter(file, true);

		fileWriter.append(message+"\n");
		fileWriter.close();
	}

	/**
	 * Read the config parameters from the config.properties file
	 * @return
	 * @throws IOException
	 */
	public static boolean readConfig() throws IOException {
		InputStream stream = new FileInputStream("src/config.properties");
		Properties property = new Properties();
		
		
		property.load(stream);
		String path, user, pass, resultpath;
		path = property.getProperty("postgrespath");
		user = property.getProperty("postgresuser");
		pass = property.getProperty("postgrespass");

		if (path == null || user == null || pass == null)
			return false;
		
		QuestionsDAO.setConnectionPath(path);
		StudentDAO.setConnectionPath(path);
		
		QuestionsDAO.setConnectionUser(user);
		StudentDAO.setConnectionUsername(user);
		
		QuestionsDAO.setConnectionPass(pass);
		StudentDAO.setConnectionPassword(pass);


		return true;
	}
	public static String getWritepath() {
		return writepath;
	}

	public static void setWritepath(String writepath) {
		FileOperations.writepath = writepath;
	}
}

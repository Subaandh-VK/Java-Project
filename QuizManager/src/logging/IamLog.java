package logging;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IamLog {
	private LocalDateTime now = LocalDateTime.now();
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm:ss.SSS");
	private String loggingEntity;

	public IamLog(String loggingEntity) {
		this.setLoggingEntity(loggingEntity);
	}

	private void log(String message, String level){	
		String writeMsg = "";
		if (level != null) {
			String nowAsString = now.format(dateTimeFormatter);
			writeMsg = this.loggingEntity + " " + nowAsString + " " + message;
		} else {
			writeMsg = message;
		}

		System.out.println(writeMsg); 
		try {
			FileOperations.write(writeMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void info(String message) {
		log(message, "INFO");
	}
	
	public void warn(String message) {
		log(message, "WARN");
	}

	public void error(String message) {
		log(message, "ERROR");
	}
	public void debug(String message) {
		log(message, "DEBUG");
	}

	public void save(String message) {
		log(message, null);
	}
	public String getLoggingEntity() {
		return loggingEntity;
	}

	public void setLoggingEntity(String loggingEntity) {
		this.loggingEntity = loggingEntity;
	}

}

package logging;

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
		String nowAsString = now.format(dateTimeFormatter);
		String writeMsg = this.loggingEntity + " " + nowAsString + " " + message;
		System.out.println(writeMsg); 
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

	public String getLoggingEntity() {
		return loggingEntity;
	}

	public void setLoggingEntity(String loggingEntity) {
		this.loggingEntity = loggingEntity;
	}

}

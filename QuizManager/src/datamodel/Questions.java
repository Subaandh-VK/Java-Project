package datamodel;

import java.util.Arrays;

public class Questions {
	private String Questions;
	private String topic;
	private int Id;
	private int difficulty;
	private String type;
	private String answer;
	private String[] choices;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setQuestions(String Questions) {
		this.Questions = Questions;
	}

	public void setId(int Id) {
		this.Id = Id;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public String getQuestions() {
		return Questions;
	}

	public int getId() {
		return Id;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswers(String answer) {
		this.answer = answer;
	}

	public String[] getChoices() {
		return choices;
	}

	public void setChoices(String[] choices) {
		this.choices = choices;
	}

	@Override
	public String toString() {
		return "Questions [Questions=" + Questions + ", topic=" + topic + ", Id=" + Id + ", difficulty=" + difficulty
				+ ", type=" + type + ", answer=" + answer + ", choices=" + Arrays.toString(choices) + "]";
	}

}

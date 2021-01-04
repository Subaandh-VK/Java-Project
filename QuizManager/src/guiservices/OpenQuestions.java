package guiservices;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class OpenQuestions {
	private Label question;
	private TextField response;
	private Text result;
	private String answer;
	private Button button;
	
	public Text getResult() {
		return result;
	}
	public void setResult(Text result) {
		this.result = result;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Button getButton() {
		return button;
	}
	public void setButton(Button button) {
		this.button = button;
	}
	public Label getQuestion() {
		return question;
	}
	public void setQuestion(Label question) {
		this.question = question;
	}
	public TextField getResponse() {
		return response;
	}
	public void setResponse(TextField response) {
		this.response = response;
	}
}

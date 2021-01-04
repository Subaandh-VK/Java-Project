package guiservices;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

public class MCQquestion {
	private Label question;
	private ToggleGroup toggler;
	private ArrayList<RadioButton> radios;
	private Button button;
	private String answer;
	private Text result;

	public ToggleGroup getToggler() {
		return toggler;
	}
	public void setToggler(ToggleGroup toggler) {
		this.toggler = toggler;
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
	public Text getResult() {
		return result;
	}
	public void setResult(Text result) {
		this.result = result;
	}
	public ArrayList<RadioButton> getRadios() {
		return radios;
	}
	public void setRadios(ArrayList<RadioButton> radios) {
		this.radios = radios;
	}
	
}

package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HomeController{

	private Boolean choseWrangler = null;
	
	//FXML Components and initialisation
	@FXML
	private Button wranglerButton;

	@FXML
	private Button xmlButton;

	@FXML
	private Label chosenLabel;

	@FXML
	public void initialize(){}


	//Action methods

	/**
	* If user clicked wranglerButton.
	*/
	@FXML
	private void chooseWrangler(){
		choseWrangler = new Boolean(true);
		chosenLabel.setText("Data wrangling");
	}

	/**
	* If user clicked xmlButton.
	*/
	@FXML
	private void chooseXML(){
		choseWrangler = new Boolean(false);
		chosenLabel.setText("XML generation");
	}

	/**
	* Returns what the user has chosen, to do XML generation or data wrangling.
	* Returns true if user chose wrangling, false if XML.
	*/
	public Boolean getChoseWrangler(){
		return choseWrangler;
	}

}
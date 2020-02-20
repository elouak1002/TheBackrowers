package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController{

	private boolean choseWrangler = true;
	
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
		choseWrangler = true;
		chosenLabel.setText("Data wrangling");
	}

	/**
	* If user clicked xmlButton.
	*/
	@FXML
	private void chooseXML(){
		choseWrangler = false;
		chosenLabel.setText("XML generation");
	}

	/**
	* Returns what the user has chosen, to do XML generation or data wrangling.
	* Returns true if user chose wrangling, false if XML.
	*/
	public boolean getChoseWrangler(){
		return choseWrangler;
	}

}
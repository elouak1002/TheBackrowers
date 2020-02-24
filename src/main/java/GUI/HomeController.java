package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HomeController{
	
	//FXML Components and initialisation
	@FXML
	private Button wranglerButton;

	@FXML
	private Button xmlButton;

	@FXML
	public void initialize(){}

	/**
	* Returns the XML option button.
	* This method is to be called from the above layer, to assign functionality.
	*/
	public Button getXMLButton(){
		return xmlButton;
	}

	/**
	* Returns the wrangler option button.
	* This method is to be called from the above layer, to assign functionality.
	*/
	public Button getWranglerButton(){
		return wranglerButton;
	}

}
package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;


public class OutputController {
    @FXML private Button saveButton = new Button();
    @FXML private Button clearButton = new Button();
    @FXML private TextArea outputText = new TextArea();
    @FXML private TextField fileSaved = new TextField();
    private int count = 1;                   //new file created every time


    public OutputController () {
    }
    //This method iterates over the text area and writes the contents into a .txt file
    @FXML
    public void saveFile() {
        ObservableList<CharSequence> paragraph = outputText.getParagraphs();
        Iterator<CharSequence> iter = paragraph.iterator();

        try
        { //"textFile.txt"
            BufferedWriter bf = new BufferedWriter(new FileWriter(new File("textFile" + count + ".txt")));
            count++;
            while(iter.hasNext())
            {
                CharSequence seq = iter.next();
                bf.append(seq);
                bf.newLine();
            }
            bf.flush();
            bf.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        fileIsSaved();
    }


    @FXML
    private void clearTextField() {
        outputText.clear();
        fileSaved.clear();
    }

    @FXML
    private void fileIsSaved() {
        fileSaved.setText("Text saved as a .txt file!");
    }
}

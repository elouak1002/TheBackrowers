package XML;

import datastructures.Node;
import parser.Parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class XMLCreator{
    private List<String> dataFromGUI ;
    private List<String> finalXMLData = new ArrayList<>();

    public XMLCreator(List<String> dataFromGUI){
        this.dataFromGUI = dataFromGUI;

    }

    public List<String> createXMLFile(List<String> dataFromGUI) throws IOException {
     createHeader(finalXMLData);
     createNodeContent(dataFromGUI);
     createFooter(dataFromGUI);

     return finalXMLData;
    }

    private void createFooter(List<String> finalXMLData) {
        String footer = "</MappinData>";
        this.finalXMLData.add(footer);
    }

    private void createNodeContent(List<String> dataFromGUI) throws IOException {
        Parser parser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        TreeMap<String, Node> nodes = parser.getNodes();
        for(String nodename: nodes.keySet()){
            addXMLentry(nodes.get(nodename));
        }

    }

    private void addXMLentry(Node node) {
        finalXMLData.add(" <"+node.getType().toLowerCase()+" id="+ "\""+node.getId()+"\""
        +" x="+ "\""+node.getX()+"\"" + " y="+ "\""+node.getY()+"\""
                +">");
        finalXMLData.add(" </"+node.getType().toLowerCase() +">");



    }

    private void createHeader(List<String> finalXMLData) {
        this.finalXMLData.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(new Date());
        LocalTime timeObj= LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = formatter.format(timeObj);
        df=new SimpleDateFormat("YYYY");
        String documentTag = "<MappinData version=\"1.0\" created=\""+date+" "+time+ "\" copyright=\"Mappin Technologies LTD\" "+ df.format(new Date())+">";
        this.finalXMLData.add(documentTag);
    }

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        XMLCreator xmlc = new XMLCreator(parser.getAllLines());
        List<String> data  = xmlc.createXMLFile(parser.getAllLines());
        for(String line : data){
            System.out.println(line);
        }

    }
}
package parsertest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import parser.Parser;
import datastructures.Node;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

public class ParserTest {

    private Parser parser;
    private List<String> filteredData;
    private List<String> rawData;

    @BeforeEach
    public void setup(){
        parser = new Parser(Paths.get("src/test/resources/testData.txt"));
        filteredData = Arrays.asList("Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 );",
                "Room HenRaph_04_476_264 = new Room( 47.614590f , 26.463207f , GuysHeights.HenRaph_04 , \"HR 4.2\" );",
                "Toilet HenRaph_04_374_347 = new Toilet( 37.426018f , 34.716671f , GuysHeights.HenRaph_04 , ToiletType.Female );",
                "FloorChanger HenRaph_04_418_357 = new FloorChanger( 41.841064f , 35.724461f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );",
                "FloorChanger HenRaph_04_419_365 = new FloorChanger( 41.976913f , 36.541119f , GuysHeights.HenRaph_04 , FloorChangerType.Lift ); // LAJ2");
        rawData = Arrays.asList("// Data of Mappin Technologies LTD 2019",
                "// Thu Aug  1 11:48:39 2019", "", "",
                "Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 );",
                "Room HenRaph_04_476_264 = new Room( 47.614590f , 26.463207f , GuysHeights.HenRaph_04 , \"HR 4.2\" );",
                "Toilet HenRaph_04_374_347 = new Toilet( 37.426018f , 34.716671f , GuysHeights.HenRaph_04 , ToiletType.Female );",
                "FloorChanger HenRaph_04_418_357 = new FloorChanger( 41.841064f , 35.724461f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );",
                "FloorChanger HenRaph_04_419_365 = new FloorChanger( 41.976913f , 36.541119f , GuysHeights.HenRaph_04 , FloorChangerType.Lift ); // LAJ2",
                "", "HenRaph_04_493_264.addAllNeighbours( new List<Node>{ HenRaph_04_476_264 , HenRaph_04_493_276 , HenRaph_04_491_243 } );",
                "HenRaph_04_439_365.addAllNeighbours( new List<Node>{ HenRaph_04_439_357 , HenRaph_04_491_365 , HenRaph_04_419_365 , HenRaph_04_442_369 } );",
                "HenRaph_04_621_365.addAllNeighbours( new List<Node>{ HenRaph_04_581_365 , HenRaph_04_621_354 } );", "",
                "====== NODE LISTS =======", "", "// Nodes:",
                " , HenRaph_04_493_264 , HenRaph_04_493_276 , HenRaph_04_493_346 , HenRaph_04_438_346 , HenRaph_04_439_357 , HenRaph_04_439_365");
    }

    @Test
    public void emptyPathTest() throws IOException {
        assertThrows(IOException.class, () ->{
            Parser parser = new Parser(Paths.get("/"));
            parser.getLines(); 
        });
    }

    @Test
    public void getLinesTest() throws IOException {
        List<String> data = parser.getAllLines();
        assertEquals(rawData, data);
    }

    @Test
    public void getFilteredLinesTest() throws IOException {
        List<String> data = parser.getLines();
        assertEquals(filteredData, data);
    }

    @Test
    public void getNeighboursLinesTest() throws IOException {
        List<String> data = parser.getNeighboursLines();
        List<String> neighboursData = Arrays.asList(
            "HenRaph_04_493_264.addAllNeighbours( new List<Node>{ HenRaph_04_476_264 , HenRaph_04_493_276 , HenRaph_04_491_243 } );",
            "HenRaph_04_439_365.addAllNeighbours( new List<Node>{ HenRaph_04_439_357 , HenRaph_04_491_365 , HenRaph_04_419_365 , HenRaph_04_442_369 } );",
            "HenRaph_04_621_365.addAllNeighbours( new List<Node>{ HenRaph_04_581_365 , HenRaph_04_621_354 } );"
            );
        assertEquals(neighboursData, data);
    }

    @Test
    public void extractDataTest(){
        String line = "Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 );";
        Pair<Float, Float> expected = new Pair<>(49.312683f,  26.463207f );
        Pair<Float, Float> data = parser.extractData(line);
        assertEquals(expected, data);
    }

    @Test
    public void extractNameTest(){
        String line = "Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 );";
        String expected = "HenRaph_04_493_264";
        String name = parser.extractName(line);
        assertEquals(expected, name);
    }

    @Test
    public void ParserCreatesCorrectNodes() throws IOException {
        TreeMap<String, Node> expectedOutcome = new TreeMap<>();
        Node node1 = new Node("HenRaph_04_493_264",49.312683f,26.463207f);
        Node node2 = new Node("HenRaph_04_476_264",47.614590f,26.463207f);
        Node node3 = new Node("HenRaph_04_374_347",37.426018f,34.716671f);
        Node node4 = new Node("HenRaph_04_418_357",41.841064f,35.724461f);
        Node node5 = new Node("HenRaph_04_419_365",41.976913f,36.541119f);

        expectedOutcome.put("HenRaph_04_493_264",node1);
        expectedOutcome.put("HenRaph_04_476_264",node2);
        expectedOutcome.put("HenRaph_04_374_347",node3);
        expectedOutcome.put("HenRaph_04_418_357",node4);
        expectedOutcome.put("HenRaph_04_419_365",node5);
        
        TreeMap<String, Node> actualOutcome = parser.getNodes();

        for (String nodeName : actualOutcome.keySet()) {
            assertThat(actualOutcome.get(nodeName).toString(), equalTo(expectedOutcome.get(nodeName).toString()));
        }
    }

    @Test
    public void setNeighborsTest() throws IOException {
        Parser neighbourParser = new Parser(Paths.get("src/test/resources/testNeighbourData.txt"));

        TreeMap<String, Node> expectedOutcome = new TreeMap<>();
        Node node1 = new Node("HenRaph_04_493_264",10f,10f);
        Node node2 = new Node("HenRaph_04_476_264",10f,10f);
        Node node3 = new Node("HenRaph_04_374_347",10f,10f);
        Node node4 = new Node("HenRaph_04_418_357",10f,10f);
        Node node5 = new Node("HenRaph_04_419_365",10f,10f);

        node1.setNeighbours(Arrays.asList(node1,node4,node3));
        node2.setNeighbours(Arrays.asList(node3,node4));
        node3.setNeighbours(Arrays.asList(node1,node2));

        expectedOutcome.put("HenRaph_04_493_264",node1);
        expectedOutcome.put("HenRaph_04_476_264",node2);
        expectedOutcome.put("HenRaph_04_374_347",node3);
        expectedOutcome.put("HenRaph_04_418_357",node4);
        expectedOutcome.put("HenRaph_04_419_365",node5);

        
        TreeMap<String, Node> actualOutcome = neighbourParser.getNodes();

        for (String nodeName : actualOutcome.keySet()) {
            assertThat(actualOutcome.get(nodeName).getNeighbours().toString(), equalTo(expectedOutcome.get(nodeName).getNeighbours().toString()));
        }
    }


    @Test
    public void beginDataLinesTest() throws IOException {
        Parser fullInputParser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        assertEquals(fullInputParser.beginOfDataLines(),4);
    }

    @Test
    public void endDataLinesTest() throws IOException {
        Parser fullInputParser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        assertEquals(fullInputParser.endOfDataLines(),25);
    }

    @Test
    public void beginNeighbourLinesTest() throws IOException {
        Parser fullInputParser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        assertEquals(fullInputParser.beginOfNeighbourLines(),27);
    }

    @Test
    public void endNeighbourLinesTest() throws IOException {
        Parser fullInputParser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        assertEquals(fullInputParser.endOfNeighbourLines(),48);
    }

    @Test
    public void whenLogFileIsEmptyIdStartsFromZero() throws IOException{
        Parser parser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        String empty="";
        Files.write(Paths.get("src/test/resources/testIdLog.txt"),empty.getBytes());

        int shouldBeZero = parser.getLastUsedID(Paths.get("src/test/resources/testIdLog.txt"));
        //this test should only pass when log file is empty
        assertEquals(shouldBeZero+1,0);
    }

    @Test
    public void getsCorrectLastSeenId() throws IOException {
        Parser parser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        List<String> numbers = new ArrayList<>();
        numbers=Arrays.asList("1","2","3");
        Files.write(Paths.get("src/test/resources/testIdLog.txt"),numbers.toString().getBytes());
        int shouldBe3 = parser.getLastUsedID(Paths.get("src/test/resources/testIdLog.txt"));

        assertEquals(shouldBe3,3);
    }

    @Test
    public void generatesUniqueIds() throws IOException {
        Parser parser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        Parser secondParser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        int shouldBeFirst = parser.generateNodeId(Paths.get("src/test/resources/testIdLog.txt"));
        int shouldBeSecond = secondParser.generateNodeId(Paths.get("src/test/resources/testIdLog.txt"));

        assertEquals(shouldBeFirst,shouldBeSecond-1);
    }
}

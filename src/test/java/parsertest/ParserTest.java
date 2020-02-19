package parsertest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import parser.Parser;
import datastructures.Node;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
    public void extractDataTest(){
        String line = "Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 );";
        Pair<Float, Float> expected = new Pair<>(49.31f, 26.46f);
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
        String testInput  =  "Node MajorProject = new Node( 67.040802f , 67.040802f , KCL );";
        String testInput2 = "Room BushHouseRoom = new Node( 127.040802f , 335.411697f , KCLBH.09 );";
        List<String> dataToMatch = Arrays.asList(testInput,testInput2);

        HashMap<String, Node> expectedOutcomes = new HashMap<>();
        expectedOutcomes.put("MajorProject",new Node("Node MajorProject",67.040802f , 67.040802f));
        expectedOutcomes.put("BushHouseRoom",new Node("Room BushHouseRoom",127.040802f , 335.411697f));

        assertEquals(expectedOutcomes.keySet(), parser.createNodes(dataToMatch).keySet());
    }

    @Test
    public void beginDataLinesTest() throws IOException {
        Parser fullInputParser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        assertEquals(fullInputParser.beginOfDataLines(fullInputParser.getAllLines(), fullInputParser.getLines()),4);
    }

    @Test
    public void endDataLinesTest() throws IOException {
        Parser fullInputParser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        assertEquals(fullInputParser.endOfDataLines(fullInputParser.getAllLines(), fullInputParser.getLines()),25);
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
        int shouldBeSecond = parser.generateNodeId(Paths.get("src/test/resources/testIdLog.txt"));

        assertEquals(shouldBeFirst,shouldBeSecond-1);
    }
}

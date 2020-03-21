package parsertest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.NodeCreator;
import parser.XMLParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XMLParserTest {
    private XMLParser parser;
    private NodeCreator nodeCreator;
    private List<String> nodeLines;
    private List<String> allLines;
    private List<String> neighbourLines;
    private List<String> paths = Arrays.asList("src/test/resources/XMLParserTestData1.txt", "src/test/resources/XMLParserTestData2.txt");
    private List<String> dataOutput = Arrays.asList("");

    @BeforeEach
    public void setup() throws IOException{
        parser = new XMLParser(paths);
        nodeCreator = new NodeCreator(parser.getLines(), parser.getNeighboursLines());
        nodeLines = Arrays.asList("Node HenRaph_04_491_365 = new Node( 49.176838f , 36.575871f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_581_365 = new Node( 58.142784f , 36.593246f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_621_365 = new Node( 62.150284f , 36.593246f , GuysHeights.HenRaph_04 );",
                "Room HenRaph_04_476_264 = new Room( 47.614590f , 26.463207f , GuysHeights.HenRaph_04 , \"HR 4.2\" );",
                "Room HenRaph_04_476_277 = new Room( 47.614590f , 27.766386f , GuysHeights.HenRaph_04 , \"HR 4.1\" );",
                "FloorChanger HenRaph_04_418_357 = new FloorChanger( 41.841064f , 35.724461f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );",
                "FloorChanger HenRaph_04_696_341 = new FloorChanger( 69.689827f , 34.195396f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );",
                "FloorChanger HenRaph_04_419_365 = new FloorChanger( 41.976913f , 36.541119f , GuysHeights.HenRaph_04 , FloorChangerType.Lift ); // LAJ2",
                "Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_493_276 = new Node( 49.312683f , 27.696882f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_493_346 = new Node( 49.312683f , 34.664543f , GuysHeights.HenRaph_04 );",
                "Room HenRaph_04_530_359 = new Room( 53.048496f , 35.932968f , GuysHeights.HenRaph_04 , \"HR 4.11\" );",
                "Room HenRaph_04_579_358 = new Room( 57.939011f , 35.846092f , GuysHeights.HenRaph_04 , \"HR 4.13\" );",
                "Toilet HenRaph_04_374_347 = new Toilet( 37.426018f , 34.716671f , GuysHeights.HenRaph_04 , ToiletType.Female );");
        neighbourLines = Arrays.asList("HenRaph_04_581_365.addAllNeighbours( new List<Node>{ HenRaph_04_579_358 , HenRaph_04_530_365 , HenRaph_04_621_365, HenRaph_04_584_372 } );",
                "HenRaph_04_621_365.addAllNeighbours( new List<Node>{ HenRaph_04_581_365 , HenRaph_04_621_354 } );",
                "HenRaph_04_621_354.addAllNeighbours( new List<Node>{ HenRaph_04_621_365 , HenRaph_04_670_354 , HenRaph_04_617_370 } );",
                "HenRaph_04_374_347.addAllNeighbours( new List<Node>{ HenRaph_04_438_346 } );",
                "HenRaph_04_418_357.addAllNeighbours( new List<Node>{ HenRaph_04_439_357 , HenRaph_03_367_371 } );",
                "HenRaph_04_696_341.addAllNeighbours( new List<Node>{ HenRaph_04_702_348 , HenRaph_03_664_344 } );",
                "HenRaph_04_419_365.addAllNeighbours( new List<Node>{ HenRaph_04_439_365 , HenRaph_03_422_368 } );",
                "HenRaph_04_493_264.addAllNeighbours( new List<Node>{ HenRaph_04_476_264 , HenRaph_04_493_276 , HenRaph_04_491_243 } );",
                "HenRaph_04_493_276.addAllNeighbours( new List<Node>{ HenRaph_04_476_277 , HenRaph_04_493_264 , HenRaph_04_493_346 } );",
                "HenRaph_04_530_365.addAllNeighbours( new List<Node>{ HenRaph_04_530_359 , HenRaph_04_491_365 , HenRaph_04_581_365 , HenRaph_04_523_372 } );",
                "HenRaph_04_491_365.addAllNeighbours( new List<Node>{ HenRaph_04_530_365 , HenRaph_04_439_365 , HenRaph_04_494_373 } );");
        allLines = Arrays.asList("Node HenRaph_04_491_365 = new Node( 49.176838f , 36.575871f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_581_365 = new Node( 58.142784f , 36.593246f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_621_365 = new Node( 62.150284f , 36.593246f , GuysHeights.HenRaph_04 );",
                "Room HenRaph_04_476_264 = new Room( 47.614590f , 26.463207f , GuysHeights.HenRaph_04 , \"HR 4.2\" );",
                "Room HenRaph_04_476_277 = new Room( 47.614590f , 27.766386f , GuysHeights.HenRaph_04 , \"HR 4.1\" );",
                "FloorChanger HenRaph_04_418_357 = new FloorChanger( 41.841064f , 35.724461f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );",
                "FloorChanger HenRaph_04_696_341 = new FloorChanger( 69.689827f , 34.195396f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );",
                "FloorChanger HenRaph_04_419_365 = new FloorChanger( 41.976913f , 36.541119f , GuysHeights.HenRaph_04 , FloorChangerType.Lift ); // LAJ2",
                "Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_493_276 = new Node( 49.312683f , 27.696882f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_493_346 = new Node( 49.312683f , 34.664543f , GuysHeights.HenRaph_04 );",
                "Room HenRaph_04_530_359 = new Room( 53.048496f , 35.932968f , GuysHeights.HenRaph_04 , \"HR 4.11\" );",
                "Room HenRaph_04_579_358 = new Room( 57.939011f , 35.846092f , GuysHeights.HenRaph_04 , \"HR 4.13\" );",
                "Toilet HenRaph_04_374_347 = new Toilet( 37.426018f , 34.716671f , GuysHeights.HenRaph_04 , ToiletType.Female );",
                "#",
                "HenRaph_04_581_365.addAllNeighbours( new List<Node>{ HenRaph_04_579_358 , HenRaph_04_530_365 , HenRaph_04_621_365, HenRaph_04_584_372 } );",
                "HenRaph_04_621_365.addAllNeighbours( new List<Node>{ HenRaph_04_581_365 , HenRaph_04_621_354 } );",
                "HenRaph_04_621_354.addAllNeighbours( new List<Node>{ HenRaph_04_621_365 , HenRaph_04_670_354 , HenRaph_04_617_370 } );",
                "HenRaph_04_374_347.addAllNeighbours( new List<Node>{ HenRaph_04_438_346 } );",
                "HenRaph_04_418_357.addAllNeighbours( new List<Node>{ HenRaph_04_439_357 , HenRaph_03_367_371 } );",
                "HenRaph_04_696_341.addAllNeighbours( new List<Node>{ HenRaph_04_702_348 , HenRaph_03_664_344 } );",
                "HenRaph_04_419_365.addAllNeighbours( new List<Node>{ HenRaph_04_439_365 , HenRaph_03_422_368 } );",
                "HenRaph_04_493_264.addAllNeighbours( new List<Node>{ HenRaph_04_476_264 , HenRaph_04_493_276 , HenRaph_04_491_243 } );",
                "HenRaph_04_493_276.addAllNeighbours( new List<Node>{ HenRaph_04_476_277 , HenRaph_04_493_264 , HenRaph_04_493_346 } );",
                "HenRaph_04_530_365.addAllNeighbours( new List<Node>{ HenRaph_04_530_359 , HenRaph_04_491_365 , HenRaph_04_581_365 , HenRaph_04_523_372 } );",
                "HenRaph_04_491_365.addAllNeighbours( new List<Node>{ HenRaph_04_530_365 , HenRaph_04_439_365 , HenRaph_04_494_373 } );");
    }

    @Test
    public void getAllLinesTest() throws IOException {
        List<String> data = parser.getAllLines();
        for (String line: data) {
            assertTrue(allLines.contains(line));
        }
    }

    @Test
    public void getNodeLinesTest() throws IOException {
        List<String> data = parser.getLines();
        for (String line: data) {
            assertTrue(nodeLines.contains(line));
        }
    }

    @Test
    public void getNeighbourLinesTest() throws IOException {
        List<String> data = parser.getNeighboursLines();
        for (String line: data) {
            assertTrue(neighbourLines.contains(line));
        }
    }
}

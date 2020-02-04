import ALG.Node;
import ALG.Parser;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ALGNodeCreationTests {
    Parser parser = new Parser(Paths.get("src/main/resources/index.txt"));

    @Test
    public void ShouldReturnFilteredLinesOnlyIncludingData() throws IOException {
        List<String> data = parser.getLines() ;
        List<String> dataToMatch = Arrays.asList("Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_493_276 = new Node( 49.312683f , 27.696882f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_493_346 = new Node( 49.312683f , 34.664543f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_438_346 = new Node( 43.810856f , 34.664543f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_439_357 = new Node( 43.946705f , 35.793964f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_439_365 = new Node( 43.946705f , 36.575871f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_530_365 = new Node( 53.048496f , 36.593246f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_491_365 = new Node( 49.176838f , 36.575871f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_581_365 = new Node( 58.142784f , 36.593246f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_621_365 = new Node( 62.150284f , 36.593246f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_621_354 = new Node( 62.150284f , 35.411697f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_670_354 = new Node( 67.040802f , 35.411697f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_670_348 = new Node( 67.040802f , 34.803547f , GuysHeights.HenRaph_04 );",
                "Node HenRaph_04_702_348 = new Node( 70.233215f , 34.803547f , GuysHeights.HenRaph_04 );",
                "Room HenRaph_04_476_264 = new Room( 47.614590f , 26.463207f , GuysHeights.HenRaph_04 , \"HR 4.2\" );",
                "Room HenRaph_04_476_277 = new Room( 47.614590f , 27.766386f , GuysHeights.HenRaph_04 , \"HR 4.1\" );",
                "Room HenRaph_04_530_359 = new Room( 53.048496f , 35.932968f , GuysHeights.HenRaph_04 , \"HR 4.11\" );",
                "Room HenRaph_04_579_358 = new Room( 57.939011f , 35.846092f , GuysHeights.HenRaph_04 , \"HR 4.13\" );",
                "Toilet HenRaph_04_374_347 = new Toilet( 37.426018f , 34.716671f , GuysHeights.HenRaph_04 , ToiletType.Female );",
                "FloorChanger HenRaph_04_418_357 = new FloorChanger( 41.841064f , 35.724461f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );",
                "FloorChanger HenRaph_04_696_341 = new FloorChanger( 69.689827f , 34.195396f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );");

             assertEquals(data,dataToMatch);

    }
    @Test
    public void ParserCreatesCorrectNodes(){
        String testInput  =  "Node MajorProject = new Node( 67.040802f , 67.040802f , KCL );";
        String testInput2 = "Room BushHouseRoom = new Node( 127.040802f , 335.411697f , KCLBH.09 );";
        List<String> dataToMatch = Arrays.asList(testInput,testInput2);

        HashMap<String, Node> expectedOutcomes = new HashMap<>();
        expectedOutcomes.put("Node MajorProject",new Node("Node MajorProject",67.040802f , 67.040802f));
        expectedOutcomes.put("Room BushHouseRoom",new Node("Room BushHouseRoom",127.040802f , 335.411697f));

        assertEquals(parser.createNodes(dataToMatch).keySet(),expectedOutcomes.keySet());
    }


}

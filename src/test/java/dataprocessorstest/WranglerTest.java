package dataprocessorstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;


import datastructures.Node;
import dataprocessors.Wrangler;

public class WranglerTest{

    static TreeMap<String, TreeMap<String, Node>> resultSet;
    Wrangler wrangler;

//---------------------------------Test Methods-----------------------------------------------

    @BeforeEach
    public void setup(){
        resultSet = setTestData();
        wrangler = new Wrangler(resultSet.get("input"));
    }

    @Test
    public void testNoTransformations(){
        TreeMap<String, Node> output = new TreeMap<>();
        Node pivot = resultSet.get("input").get("HenRaph_04_493_264");

        //test no rotation no scale and no shift
        output = wrangler.runTransformations(0,1,1,pivot.getX(), pivot.getY(), pivot);
        assertEquals(true, equalTreeMaps(output, resultSet.get("Nodes at same location")));
      
        //test rotation 360* no scale and no shift
        wrangler = new Wrangler(resultSet.get("input"));
        output = wrangler.runTransformations(360,1,1,pivot.getX(), pivot.getY(), pivot);
        assertEquals(true, equalTreeMaps(output, resultSet.get("Nodes at same location")));
    }
    
    @Test
    public void testRotation(){
        TreeMap<String, Node> output = new TreeMap<>();
        Node pivot = resultSet.get("input").get("HenRaph_04_493_264");

        //test rotation by 90*
        output = wrangler.runTransformations(90,1,1,pivot.getX(), pivot.getY(), pivot);
        assertEquals(true, equalTreeMaps(output, resultSet.get("Rotate 90")));
        
        //test rotation by 60*
        wrangler = new Wrangler(resultSet.get("input"));
        output = wrangler.runTransformations(60,1,1,pivot.getX(), pivot.getY(), pivot);
        assertEquals(true, equalTreeMaps(output, resultSet.get("Rotate 60")));

        //test rotation by 45*
        wrangler = new Wrangler(resultSet.get("input"));
        output = wrangler.runTransformations(45,1,1,pivot.getX(), pivot.getY(), pivot);
        assertEquals(true, equalTreeMaps(output, resultSet.get("Rotate 45")));
    }

    @Test
    public void testScale(){
        TreeMap<String, Node> output = new TreeMap<>();
        Node pivot = resultSet.get("input").get("HenRaph_04_493_264");

        //test scale by 0.25, 0.25
        output = wrangler.runTransformations(0,0.25f,0.25f,pivot.getX(), pivot.getY(), pivot);
        assertEquals(true, equalTreeMaps(output, resultSet.get("Scale 0.25")));

        //test scale by 2.75, 2.75
        wrangler = new Wrangler(resultSet.get("input"));
        output = wrangler.runTransformations(0,2.75f,2.75f,pivot.getX(), pivot.getY(), pivot);
        assertEquals(true, equalTreeMaps(output, resultSet.get("Scale 2.75")));
    }

    @Test
    public void testShift(){
        TreeMap<String, Node> output = new TreeMap<>();
        Node pivot = resultSet.get("input").get("HenRaph_04_493_264");

        //test Shift to 0, 0
        output = wrangler.runTransformations(0,1,1,0, 0, pivot);
        assertEquals(true, equalTreeMaps(output, resultSet.get("Shift to 0, 0")));

        //test Shift to -432.145, -846.637
        wrangler = new Wrangler(resultSet.get("input"));
        output = wrangler.runTransformations(0,1,1,-432.145f, -846.637f, pivot);
        assertEquals(true, equalTreeMaps(output, resultSet.get("Shift 1")));

    }

    @Test
    public void testIndependentShift(){
        TreeMap<String, Node> output = new TreeMap<>();
        
        //test No transformations done
        output = wrangler.runTransformations(0,1,1,0, 0, null);
        assertEquals(true, equalTreeMaps(output, resultSet.get("Nodes at same location")));
        
        wrangler = new Wrangler(resultSet.get("input"));
        output = wrangler.runTransformations(90,1,1,0, 0, null);
        assertEquals(true, equalTreeMaps(output, resultSet.get("Independent rotation 90")));
        
        wrangler = new Wrangler(resultSet.get("input"));
        output = wrangler.runTransformations(25,1,1,0, 0, null);
        assertEquals(true, equalTreeMaps(output, resultSet.get("Independent rotation 25")));
        
        wrangler = new Wrangler(resultSet.get("input"));
        output = wrangler.runTransformations(0,0.75f,2.15f,0, 0, null);
        assertEquals(true, equalTreeMaps(output, resultSet.get("Independent scale")));
        
        wrangler = new Wrangler(resultSet.get("input"));
        output = wrangler.runTransformations(0,1,1,20, 15, null);
        assertEquals(true, equalTreeMaps(output, resultSet.get("Independent shift")));
    }


    //---------------------------------Helper Methods---------------------------------------------

    /**
     * Method for comparing the values inside 2 nodes to determine if
     * they are equal
     * @param actual
     * @param expected
     * @return
     */
    private Boolean samePropertyValues(Node actual, Node expected) {
        if(actual.getX() == expected.getX() && actual.getY() == expected.getY())
        return true;
        return false;
    }

    /**
     * Method to compare 2 TreeMaps.
     * Makes calls to samePropertyValues to compare Nodes
     * @param actual
     * @param expected
     * @return a bool matching the equality of the maps
     */
    private Boolean equalTreeMaps(TreeMap<String, Node> actual, TreeMap<String, Node> expected) {
        if(actual.size() == expected.size()){
            for(Map.Entry<String,Node> entry : actual.entrySet()) {
                String key = entry.getKey();
                Node value = entry.getValue();
                if(!samePropertyValues(value, expected.get(key))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Method creates the expected results for the test cases
     * @return  returns a TreeMap containing various instances
     *          of the original list
     */
    public TreeMap<String, TreeMap<String, Node>> setTestData() {

        TreeMap<String, TreeMap<String, Node>> resoults = new TreeMap<>();
        TreeMap<String, Node> nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 37.426018f, 34.71667f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 41.841064f, 35.72446f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 41.976913f, 36.54112f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 43.810856f, 34.664543f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 43.946705f, 35.793964f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 43.946705f, 36.57587f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 47.61459f, 26.463207f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 47.61459f, 27.766386f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 49.176838f, 36.57587f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 49.312683f, 26.463207f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 49.312683f, 27.696882f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 49.312683f, 34.664543f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 53.048496f, 35.93297f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 53.048496f, 36.593246f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 57.93901f, 35.846092f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 58.142784f, 36.593246f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 62.150284f, 35.411697f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 62.150284f, 36.593246f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 67.0408f, 34.803547f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 67.0408f, 35.411697f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 69.68983f, 34.195396f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 70.233215f, 34.803547f));

        resoults.put("input", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 37.43f, 34.72f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 41.84f, 35.72f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 41.98f, 36.54f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 43.81f, 34.66f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 43.95f, 35.79f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 43.95f, 36.58f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 47.61f, 26.46f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 47.61f, 27.77f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 49.18f, 36.58f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 49.31f, 26.46f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 49.31f, 27.7f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 49.31f, 34.66f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 53.05f, 35.93f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 53.05f, 36.59f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 57.94f, 35.85f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 58.14f, 36.59f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 62.15f, 35.41f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 62.15f, 36.59f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 67.04f, 34.8f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 67.04f, 35.41f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 69.69f, 34.2f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 70.23f, 34.8f));

        resoults.put("Nodes at same location", nodes);

        nodes = new TreeMap<>();        
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", -11.89f, 8.25f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", -7.47f, 9.26f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", -7.34f, 10.08f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", -5.5f, 8.2f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", -5.37f, 9.33f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", -5.37f, 10.11f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", -1.7f, 0.0f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", -1.7f, 1.3f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", -0.14f, 10.11f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 0.0f, 0.0f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 0.0f, 1.23f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 0.0f, 8.2f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 3.74f, 9.47f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 3.74f, 10.13f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 8.63f, 9.38f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 8.83f, 10.13f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 12.84f, 8.95f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 12.84f, 10.13f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 17.73f, 8.34f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 17.73f, 8.95f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 20.38f, 7.73f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 20.92f, 8.34f));

        resoults.put("Shift to 0, 0", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", -444.03f, -838.38f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", -439.62f, -837.38f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", -439.48f, -836.56f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", -437.65f, -838.44f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", -437.51f, -837.31f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", -437.51f, -836.52f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", -433.84f, -846.64f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", -433.84f, -845.33f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", -432.28f, -836.52f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", -432.14f, -846.64f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", -432.14f, -845.4f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", -432.14f, -838.44f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", -428.41f, -837.17f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", -428.41f, -836.51f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", -423.52f, -837.25f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", -423.31f, -836.51f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", -419.31f, -837.69f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", -419.31f, -836.51f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", -414.42f, -838.3f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", -414.42f, -837.69f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", -411.77f, -838.9f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", -411.22f, -838.3f));

        resoults.put("Shift 1", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 46.34f, 28.53f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 47.44f, 28.78f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 47.48f, 28.98f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 47.94f, 28.51f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 47.97f, 28.8f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 47.97f, 28.99f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 48.89f, 26.46f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 48.89f, 26.79f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 49.28f, 28.99f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 49.31f, 26.46f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 49.31f, 26.77f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 49.31f, 28.51f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 50.25f, 28.83f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 50.25f, 29.0f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 51.47f, 28.81f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 51.52f, 29.0f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 52.52f, 28.7f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 52.52f, 29.0f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 53.74f, 28.55f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 53.74f, 28.7f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 54.41f, 28.4f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 54.54f, 28.55f));

        resoults.put("Scale 0.25", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 16.62f, 49.16f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 28.77f, 51.93f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 29.14f, 54.18f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 34.18f, 49.02f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 34.56f, 52.12f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 34.56f, 54.27f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 44.64f, 26.46f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 44.64f, 30.05f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 48.94f, 54.27f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 49.31f, 26.46f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 49.31f, 29.86f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 49.31f, 49.02f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 59.59f, 52.51f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 59.59f, 54.32f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 73.04f, 52.27f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 73.6f, 54.32f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 84.62f, 51.07f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 84.62f, 54.32f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 98.07f, 49.4f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 98.07f, 51.07f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 105.35f, 47.73f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 106.84f, 49.4f));

        resoults.put("Scale 2.75", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 41.06f, 14.58f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 40.05f, 18.99f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 39.23f, 19.13f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 41.11f, 20.96f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 39.98f, 21.1f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 39.2f, 21.1f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 49.31f, 24.77f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 48.01f, 24.77f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 39.2f, 26.33f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 49.31f, 26.46f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 48.08f, 26.46f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 41.11f, 26.46f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 39.84f, 30.2f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 39.18f, 30.2f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 39.93f, 35.09f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 39.18f, 35.29f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 40.36f, 39.3f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 39.18f, 39.3f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 40.97f, 44.19f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 40.36f, 44.19f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 41.58f, 46.84f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 40.97f, 47.38f));
        
        resoults.put("Rotate 90", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 36.22f, 20.3f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 37.56f, 24.62f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 36.92f, 25.15f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 39.46f, 25.8f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 38.55f, 26.48f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 37.87f, 26.87f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 48.46f, 24.99f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 47.34f, 25.64f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 40.49f, 31.4f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 49.31f, 26.46f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 48.24f, 27.08f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 42.21f, 30.56f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 42.98f, 34.43f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 42.41f, 34.76f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 45.5f, 38.63f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 44.95f, 39.18f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 47.98f, 42.06f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 46.96f, 42.65f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 50.95f, 45.99f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 50.43f, 46.29f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 52.8f, 47.98f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 52.55f, 48.75f));
        
        resoults.put("Rotate 60", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 35.07f, 23.89f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 37.48f, 27.73f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 37.0f, 28.4f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 39.62f, 28.37f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 38.92f, 29.27f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 38.37f, 29.82f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 48.11f, 25.26f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 47.19f, 26.18f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 42.07f, 33.52f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 49.31f, 26.46f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 48.44f, 27.34f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 43.51f, 32.26f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 45.26f, 35.8f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 44.79f, 36.27f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 48.78f, 39.2f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 48.39f, 39.87f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 52.06f, 41.87f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 51.23f, 42.7f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 55.95f, 44.9f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 55.52f, 45.33f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 58.25f, 46.34f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 58.21f, 47.15f));

        resoults.put("Rotate 45", nodes);
        
        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", -34.72f, 37.43f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", -35.72f, 41.84f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", -36.54f, 41.98f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", -34.66f, 43.81f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", -35.79f, 43.95f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", -36.58f, 43.95f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", -26.46f, 47.61f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", -27.77f, 47.61f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", -36.58f, 49.18f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", -26.46f, 49.31f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", -27.7f, 49.31f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", -34.66f, 49.31f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", -35.93f, 53.05f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", -36.59f, 53.05f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", -35.85f, 57.94f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", -36.59f, 58.14f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", -35.41f, 62.15f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", -36.59f, 62.15f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", -34.8f, 67.04f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", -35.41f, 67.04f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", -34.2f, 69.69f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", -34.8f, 70.23f));

        resoults.put("Independent rotation 90", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 19.25f, 47.28f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 22.82f, 50.06f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 22.6f, 50.86f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 25.06f, 49.93f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 24.7f, 51.01f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 24.37f, 51.72f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 31.97f, 44.11f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 31.42f, 45.29f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 29.11f, 53.93f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 33.51f, 44.82f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 32.99f, 45.94f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 30.04f, 52.26f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 32.89f, 54.99f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 32.61f, 55.58f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 37.36f, 56.97f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 37.23f, 57.74f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 41.36f, 58.36f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 40.86f, 59.43f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 46.05f, 59.88f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 45.79f, 60.43f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 48.71f, 60.44f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 48.94f, 61.22f));

        resoults.put("Independent rotation 25", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 28.07f, 74.64f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 31.38f, 76.81f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 31.48f, 78.56f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 32.86f, 74.53f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 32.96f, 76.96f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 32.96f, 78.64f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 35.71f, 56.9f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 35.71f, 59.7f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 36.88f, 78.64f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 36.98f, 56.9f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 36.98f, 59.55f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 36.98f, 74.53f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 39.79f, 77.26f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 39.79f, 78.68f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 43.45f, 77.07f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 43.61f, 78.68f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 46.61f, 76.14f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 46.61f, 78.68f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 50.28f, 74.83f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 50.28f, 76.14f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 52.27f, 73.52f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 52.67f, 74.83f));
        
        resoults.put("Independent scale", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 57.43f, 49.72f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 61.84f, 50.72f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 61.98f, 51.54f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 63.81f, 49.66f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 63.95f, 50.79f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 63.95f, 51.58f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 67.61f, 41.46f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 67.61f, 42.77f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 69.18f, 51.58f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 69.31f, 41.46f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 69.31f, 42.7f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 69.31f, 49.66f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 73.05f, 50.93f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 73.05f, 51.59f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 77.94f, 50.85f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 78.14f, 51.59f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 82.15f, 50.41f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 82.15f, 51.59f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 87.04f, 49.8f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 87.04f, 50.41f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 89.69f, 49.2f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 90.23f, 49.8f));

        resoults.put("Independent shift", nodes);

        return resoults;
    }
}

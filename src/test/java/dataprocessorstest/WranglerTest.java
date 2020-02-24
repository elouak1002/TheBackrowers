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

        TreeMap<String, TreeMap<String, Node>> results = new TreeMap<>();
        TreeMap<String, Node> nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 37.426018f, 34.71667f, "14"));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 41.841064f, 35.72446f, "14"));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 41.976913f, 36.54112f, "14"));

        results.put("input", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 37.43f, 34.72f, "14"));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 41.84f, 35.72f, "14"));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 41.98f, 36.54f, "14"));


        results.put("Nodes at same location", nodes);

        nodes = new TreeMap<>();        
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", -11.89f, 8.25f, "14"));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", -7.47f, 9.26f, "14"));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", -7.34f, 10.08f, "14"));


        results.put("Shift to 0, 0", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", -444.03f, -838.38f, "14"));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", -439.62f, -837.38f, "14"));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", -439.48f, -836.56f, "14"));


        results.put("Shift 1", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 46.34f, 28.53f, "14"));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 47.44f, 28.78f, "14"));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 47.48f, 28.98f, "14"));


        results.put("Scale 0.25", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 16.62f, 49.16f, "14"));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 28.77f, 51.93f, "14"));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 29.14f, 54.18f, "14"));

        results.put("Scale 2.75", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 41.06f, 14.58f, "14"));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 40.05f, 18.99f, "14"));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 39.23f, 19.13f, "14"));

        
        results.put("Rotate 90", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 36.22f, 20.3f, "14"));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 37.56f, 24.62f, "14"));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 36.92f, 25.15f, "14"));

        
        results.put("Rotate 60", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 35.07f, 23.89f, "14"));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 37.48f, 27.73f, "14"));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 37.0f, 28.4f, "14"));


        results.put("Rotate 45", nodes);
        
        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", -34.72f, 37.43f, "14"));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", -35.72f, 41.84f, "14"));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", -36.54f, 41.98f, "14"));

        results.put("Independent rotation 90", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 19.25f, 47.28f, "14"));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 22.82f, 50.06f, "14"));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 22.6f, 50.86f, "14"));

        results.put("Independent rotation 25", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 28.07f, 74.64f, "14"));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 31.38f, 76.81f, "14"));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 31.48f, 78.56f, "14"));

        
        results.put("Independent scale", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 57.43f, 49.72f, "14"));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 61.84f, 50.72f, "14"));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 61.98f, 51.54f, "14"));

        results.put("Independent shift", nodes);

        return results;
    }
}

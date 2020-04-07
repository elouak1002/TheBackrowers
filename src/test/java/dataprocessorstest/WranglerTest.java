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
        Node pivot = resultSet.get("input").get("HenRaph_04_374_347");

        //test no rotation no scale and no shift
        output = wrangler.runTransformations(0,1,1,pivot.getX(), pivot.getY(), pivot);
        assertEquals(true, equalTreeMaps(output, resultSet.get("case0")));
      
        //test rotation 360* no scale and no shift
        output = wrangler.runTransformations(360,1,1,pivot.getX(), pivot.getY(), pivot);
        assertEquals(true, equalTreeMaps(output, resultSet.get("case0")));
        
        //test transformation independent from pivot node
        output = wrangler.runTransformations(0,1,1,0, 0, null);
        assertEquals(true, equalTreeMaps(output, resultSet.get("case0")));
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

        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 37.426018f, 34.71667f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 41.841064f, 35.72446f));

        results.put("input", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 37.43f, 34.72f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 41.84f, 35.72f));

        results.put("case0", nodes);

        return results;

    }
}

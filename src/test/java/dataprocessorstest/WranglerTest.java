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
        assertEquals(true, equalTreeMaps(output, resultSet.get("input")));

        //test rotation 360* no scale and no shift
        wrangler = new Wrangler(resultSet.get("input"));
        output = wrangler.runTransformations(360,1,1,pivot.getX(), pivot.getY(), pivot);
        assertEquals(true, equalTreeMaps(output, resultSet.get("input")));
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
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", -11.886665f, 8.253464f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", -7.4716187f, 9.261253f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", -7.3357697f, 10.077911f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", -5.5018272f, 8.201336f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", -5.3659782f, 9.330757f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", -5.3659782f, 10.112663f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", -1.6980934f, 0.0f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", -1.6980934f, 1.3031788f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", -0.13584518f, 10.112663f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 0.0f, 0.0f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 0.0f, 1.233675f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 0.0f, 8.201336f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 3.7358131f, 9.469761f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 3.7358131f, 10.130039f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 8.6263275f, 9.382885f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 8.830101f, 10.130039f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 12.837601f, 8.94849f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 12.837601f, 10.130039f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 17.728119f, 8.34034f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 17.728119f, 8.94849f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 20.377144f, 7.732189f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 20.920532f, 8.34034f));

        resoults.put("Shift to 0, 0", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", -444.03165f, -838.38354f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", -439.6166f, -837.37573f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", -439.48077f, -836.5591f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", -437.64682f, -838.43567f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", -437.51096f, -837.3063f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", -437.51096f, -836.52435f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", -433.84308f, -846.637f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", -433.84308f, -845.33386f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", -432.28082f, -836.52435f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", -432.145f, -846.637f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", -432.145f, -845.4033f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", -432.145f, -838.43567f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", -428.40918f, -837.16724f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", -428.40918f, -836.50696f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", -423.51868f, -837.25415f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", -423.31488f, -836.50696f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", -419.30737f, -837.68854f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", -419.30737f, -836.50696f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", -414.41687f, -838.2967f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", -414.41687f, -837.68854f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", -411.76785f, -838.90485f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", -411.22446f, -838.2967f));

        resoults.put("Shift 1", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 46.34102f, 28.526573f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 47.44478f, 28.778519f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 47.47874f, 28.982685f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 47.937225f, 28.51354f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 47.971188f, 28.795895f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 47.971188f, 28.991371f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 48.88816f, 26.463207f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 48.88816f, 26.789001f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 49.27872f, 28.991371f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 49.312683f, 26.463207f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 49.312683f, 26.771626f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 49.312683f, 28.51354f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 50.246635f, 28.830647f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 50.246635f, 28.995716f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 51.469265f, 28.808928f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 51.52021f, 28.995716f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 52.522083f, 28.700329f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 52.522083f, 28.995716f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 53.744713f, 28.54829f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 53.744713f, 28.700329f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 54.406967f, 28.396254f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 54.542816f, 28.54829f));

        resoults.put("Scale 0.25", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 16.624352f, 49.160236f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 28.765732f, 51.931652f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 29.139313f, 54.177464f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 34.182655f, 49.01688f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 34.556244f, 52.12279f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 34.556244f, 54.27303f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 44.64293f, 26.463207f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 44.64293f, 30.046947f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 48.939102f, 54.27303f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 49.312683f, 26.463207f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 49.312683f, 29.855816f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 49.312683f, 49.01688f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 59.586166f, 52.505047f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 59.586166f, 54.320812f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 73.03508f, 52.26614f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 73.59546f, 54.320812f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 84.61609f, 51.07156f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 84.61609f, 54.320812f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 98.06502f, 49.399143f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 98.06502f, 51.07156f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 105.34982f, 47.726727f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 106.84415f, 49.399143f));

        resoults.put("Scale 2.75", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 41.059223f, 14.576542f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 40.051434f, 18.991589f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 39.234776f, 19.127438f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 41.11135f, 20.96138f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 39.98193f, 21.097229f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 39.200024f, 21.097229f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 49.312687f, 24.765114f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 48.009506f, 24.765114f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 39.200024f, 26.327362f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 49.312687f, 26.463207f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 48.07901f, 26.463207f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 41.11135f, 26.463207f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 39.842926f, 30.19902f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 39.182648f, 30.19902f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 39.929802f, 35.089535f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 39.182648f, 35.29331f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 40.364197f, 39.300808f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 39.182648f, 39.300808f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 40.972347f, 44.191326f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 40.364197f, 44.191326f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 41.580498f, 46.84035f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 40.972347f, 47.38374f));

        resoults.put("Rotate 90", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 36.22164f, 20.295788f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 37.556393f, 24.623226f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 36.91707f, 25.149204f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 39.459206f, 25.799156f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 38.54902f, 26.481518f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 37.871872f, 26.87247f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 48.46364f, 24.992619f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 47.335052f, 25.644207f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 40.48694f, 31.401897f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 49.312683f, 26.463207f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 48.244286f, 27.080048f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 42.210117f, 30.563877f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 42.979538f, 34.4334f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 42.40772f, 34.76354f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 45.50003f, 38.62527f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 44.954865f, 39.17532f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 47.981865f, 42.05514f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 46.95861f, 42.645916f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 50.953796f, 45.986378f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 50.427124f, 46.290455f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 52.80498f, 47.97643f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 52.550003f, 48.751087f));

        resoults.put("Rotate 60", nodes);

        nodes = new TreeMap<>();
        nodes.put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 35.07146f, 23.89415f));
        nodes.put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 37.480755f, 27.728672f));
        nodes.put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 36.99935f, 28.402195f));
        nodes.put("HenRaph_04_438_346", new Node("HenRaph_04_438_346", 39.62308f, 28.372051f));
        nodes.put("HenRaph_04_439_357", new Node("HenRaph_04_439_357", 38.92052f, 29.266731f));
        nodes.put("HenRaph_04_439_365", new Node("HenRaph_04_439_365", 38.36763f, 29.819622f));
        nodes.put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 48.111946f, 25.262478f));
        nodes.put("HenRaph_04_476_277", new Node("HenRaph_04_476_277", 47.19046f, 26.183964f));
        nodes.put("HenRaph_04_491_365", new Node("HenRaph_04_491_365", 42.06589f, 33.517883f));
        nodes.put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 49.312683f, 26.463207f));
        nodes.put("HenRaph_04_493_276", new Node("HenRaph_04_493_276", 48.44034f, 27.335552f));
        nodes.put("HenRaph_04_493_346", new Node("HenRaph_04_493_346", 43.51346f, 32.262432f));
        nodes.put("HenRaph_04_530_359", new Node("HenRaph_04_530_359", 45.25817f, 35.80096f));
        nodes.put("HenRaph_04_530_365", new Node("HenRaph_04_530_365", 44.791283f, 36.26785f));
        nodes.put("HenRaph_04_579_358", new Node("HenRaph_04_579_358", 48.777714f, 39.19765f));
        nodes.put("HenRaph_04_581_365", new Node("HenRaph_04_581_365", 48.393486f, 39.870052f));
        nodes.put("HenRaph_04_621_354", new Node("HenRaph_04_621_354", 52.0627f, 41.868305f));
        nodes.put("HenRaph_04_621_365", new Node("HenRaph_04_621_365", 51.22722f, 42.703785f));
        nodes.put("HenRaph_04_670_348", new Node("HenRaph_04_670_348", 55.950844f, 44.896397f));
        nodes.put("HenRaph_04_670_354", new Node("HenRaph_04_670_354", 55.52082f, 45.32642f));
        nodes.put("HenRaph_04_696_341", new Node("HenRaph_04_696_341", 58.254013f, 46.33951f));
        nodes.put("HenRaph_04_702_348", new Node("HenRaph_04_702_348", 58.20822f, 47.153767f));

        resoults.put("Rotate 45", nodes);

        return resoults;
    }

/*
    private static void printList(TreeMap<String, Node> nodesTree) {

        for (Map.Entry<String, Node> entry : nodesTree.entrySet()) {
            Node index = entry.getValue();
            System.out.println("nodes.add(new Node("+index.getName() +",. "+ index.getX() + "f, " + index.getY() + "f));");
        }
    }
*/

}

package parsertest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.NodeCreator;
import parser.Parser;
import parser.XMLParser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class XMLParserTest {
    private XMLParser parser;
    private NodeCreator nodeCreator;
    private List<String> paths = Arrays.asList("src/test/resources/XMLParserTestData1.txt", "src/test/resources/XMLParserTestData2.txt");
    private List<String> dataOutput = Arrays.asList("");

    @BeforeEach
    public void setup(){
        parser = new XMLParser(paths);
    }

    @Test
    public void emptyPathTest() throws IOException {
        assertThrows(IOException.class, () ->{
            XMLParser parser = new XMLParser(new ArrayList<>());
            parser.getLines();
        });
    }

    @Test
    public void getLinesTest() throws IOException{

    }
}

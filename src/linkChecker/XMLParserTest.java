package aws.devdocs.tools.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class XMLParserTest {

    @Mock
    Document document;

    @Mock
    NodeList nodelist;

    @Mock
    Node node;

    private XMLParser xmlParser;
    private String xmlFilePath;
    private String urlFileOutputPath;
    private String amazonurl;


    @BeforeEach
    public void setup() {
        initMocks(this);
        xmlParser = new XMLParser();
        amazonurl = "http://www.amazon.com";
        xmlFilePath = "src/SingleXMLElement.xml";
        urlFileOutputPath = "testFile.txt";
        File urlFile = new File(urlFileOutputPath);
        if (urlFile.exists()) {
            urlFile.delete();
        }


    }

    @Test
    public void readFile_FileExists_returnDocument() throws Exception {
        //GIVEN


        //WHEN
        Document result = xmlParser.readFile(xmlFilePath);


        //THEN
        assertTrue(new File(xmlFilePath).exists());
        assertEquals(1, result.getElementsByTagName("url").getLength());
        assertEquals(1, result.getElementsByTagName("loc").getLength());
        assertEquals(1, result.getElementsByTagName("urlset").getLength());
        assertDoesNotThrow(() -> SAXException.class);
        assertDoesNotThrow(() -> ParserConfigurationException.class);


    }

    @Test
    public void readFile_FileDoesNotExists_returnDocumentwithNoElements() throws Exception {
        //GIVEN
        xmlFilePath = "noFile.xml";


        //WHEN
        Document result = xmlParser.readFile(xmlFilePath);


        //THEN
        assertFalse(new File(xmlFilePath).exists());
        assertDoesNotThrow(() -> IOException.class);
        assertDoesNotThrow(() -> SAXException.class);
        assertDoesNotThrow(() -> ParserConfigurationException.class);
        assertTrue(result instanceof Document);
        assertEquals(null, result.getDocumentElement());


    }

    @Test
    public void getURLFromSitemapXMLFile_XMLDocument_returnStringWithURL() {

        //GIVEN
        when(document.getElementsByTagName(anyString())).thenReturn(nodelist);
        when(nodelist.getLength()).thenReturn(1);
        when(nodelist.item(anyInt())).thenReturn(node);
        when(node.getTextContent()).thenReturn(amazonurl);


        //WHEN
        String result = xmlParser.getURLFromSitemapXMLFile(document);

        //THEN
        assertEquals(amazonurl + "\n", result);
        verify(document).getElementsByTagName(anyString());
        verify(nodelist, times(2)).getLength();
        verify(nodelist).item(anyInt());
        verify(node).getTextContent();
        verifyNoMoreInteractions(document);
        verifyNoMoreInteractions(nodelist);
        verifyNoMoreInteractions(node);


    }

    @Test
    public void getURLFromSitemapXMLFile_NullXMLDocument_returnEmptyString() {

        //GIVEN
        when(document.getElementsByTagName(anyString())).thenReturn(nodelist);
        when(nodelist.getLength()).thenReturn(0);
        when(nodelist.item(anyInt())).thenReturn(node);
        when(node.getTextContent()).thenReturn(amazonurl);


        //WHEN
        String result = xmlParser.getURLFromSitemapXMLFile(document);

        //THEN
        assertEquals("", result);
        verify(document).getElementsByTagName(anyString());
        verify(nodelist, times(1)).getLength();
        verify(nodelist, never()).item(anyInt());
        verify(node, never()).getTextContent();
        verifyNoMoreInteractions(document);
        verifyNoMoreInteractions(nodelist);
        verifyNoMoreInteractions(node);


    }

    @Test
    public void writeURLstoFile_SingleLine_returnTrueandWrittenToFile() throws Exception {
        //GIVEN
        assertFalse(new File(urlFileOutputPath).exists());

        //WHEN
        Boolean result = xmlParser.writeURLstoFile(amazonurl, urlFileOutputPath);

        //THEN
        assertTrue(result);
        assertTrue(new File(urlFileOutputPath).exists());
        assertEquals(amazonurl, new Scanner(new File(urlFileOutputPath)).next());

    }

    @Test
    public void writeURLstoFile_null_returnFalse() throws Exception {
        //GIVEN
        assertFalse(new File(urlFileOutputPath).exists());
        amazonurl = null;

        //WHEN
        Set result = xmlParser.writeURLstoFile(amazonurl, urlFileOutputPath);

        //THEN
        assertFalse(result);
        assertTrue(new File(urlFileOutputPath).exists());
        assertEquals("", new Scanner(new File(urlFileOutputPath)).next());

    }
}

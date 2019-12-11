package aws.devdocs.tools.parser;

import aws.devdocs.tools.link.Link;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {


    /**
     * Read an external xml file and transform it into a XML Document Object.
     *
     * @param filepath path to a local xml file
     * @return Document created from the provided external xml file
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public  Document readFile(String filepath) throws ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            File xmlFile = new File(filepath);

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document docModelXml = builder.parse(xmlFile);

            return docModelXml;

        } catch (FileNotFoundException e) {
            System.out.println("Sorry the file doesn't exist. " + filepath + "\n" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Sorry there was an issue with your file. " + filepath + "\n" + e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println("The XML seems to have an error in file: " + filepath + "\n" + e.getMessage());
            throw e;
        } catch (SAXException e) {
            System.out.println("There was an error with SAX Parser in file: " + filepath + "\n" + e.getMessage());
            throw e;
        }

        return factory.newDocumentBuilder().newDocument();

    }

    /**
     * Retrieve all urls from XML Document with below structure of
     * <urlset>
     * <url>
     * <loc></loc>
     * </url>
     * </urlset>
     *
     * @param xmlDocument
     * @return String with each link on a new line
     */
    public  String getURLFromSitemapXMLFile(Document xmlDocument) {
        StringBuilder urlLists = new StringBuilder();

        // root should be urlset
        // System.out.println("Root element :" + xmlDocument.getDocumentElement().getNodeName());

        // Find all the elements with Tag loc. Should be inside tag url
        NodeList docElementList = xmlDocument.getElementsByTagName("loc");

        for (int i = 0; i < docElementList.getLength(); i++) {
            Node node = docElementList.item(i);
            String url = node.getTextContent();

            System.out.println("Url: " + url);

            urlLists.append(url);
            urlLists.append("\n");
        }

        return urlLists.toString();

    }

    /**
     * Create a Set of URLs to the specified File.
     *
     * @param urls     String containing a url on each line
     * @param fileName name of file to save urls
     * @return whether the file was successfully written.
     */
    public Set<Link> createSetofLinksToCheck(String urls) {
        File urlFile = new File(fileName);
        FileWriter fileWriter;
        Boolean successful = false;

        try {
            fileWriter = new FileWriter(urlFile);
            if (urls != null && !urls.equals("")) {
                fileWriter.append(urls);
                successful = true;
            } else {
                System.out.println("There was nothing in the urls to save");

            }
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Sorry the Links file doesn't exist. " + e.getMessage());
        }

        return successful;

    }

    public static void main(String[] args) {
        XMLParser parser = new XMLParser();
        try {

            Document xmlDocument = parser.readFile("sitemap.xml");

            String urls = parser.getURLFromSitemapXMLFile(xmlDocument);
            parser.writeURLstoFile(urls, "links.txt");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}

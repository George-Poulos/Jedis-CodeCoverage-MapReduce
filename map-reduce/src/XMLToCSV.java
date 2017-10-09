import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author George Poulos
 *
 * This file goes ahead and parses the coverage report made by clover in the main build file of the project.
 * Worst case runtime is O(n^3). A graph algorithm would make for a better run time since the XML structure could be
 * traversed as grapgh
 *
 * @TODO - Make the traversal algorithm into graph algorithm
 */

public class XMLToCSV
{

    public static void main(String args[]) throws JDOMException, IOException
    {
        String getCanonicalPath = new File(".").getCanonicalPath();
        String pathToXML = getCanonicalPath + "/build/reports/clover/clover.xml";
        String outputPath = getCanonicalPath + "/build/reports/clover/export.csv";

        if (args.length == 2){
            pathToXML = args[0];
            outputPath = args[1];
        }

        System.out.println("Files will be saved here : " + outputPath);

        File reportFile = new File(pathToXML);
        File outputFile = new File(outputPath);

        SAXBuilder fileBuilder = new SAXBuilder();
        Document document = fileBuilder.build(reportFile);
        Element root = document.getRootElement();

        Element project = root.getChild("project");
        List packages = project.getChildren("package");

        boolean checkCreateFile = outputFile.createNewFile();

        if(!checkCreateFile){
            System.out.println("Overwriting file : " + outputPath);
        }

        FileWriter writer = new FileWriter(outputFile);

        for (Object aPackage : packages) {
            Element packageItem = (Element) aPackage;
            List files = packageItem.getChildren("file");
            for (Object file1 : files) {
                Element file = (Element) file1;
                String fileName = file.getAttributeValue("name");
                List lines = file.getChildren("line");
                String testName = null;
                for (Object line1 : lines) {
                    Element line = (Element) line1;
                    if (line.getAttributeValue("type").equals("method")) {
                        testName = line.getAttributeValue("signature");
                    } else {
                        if (line.getAttributeValue("type").equals("stmt")) {
                            writer.write(fileName + " " + line.getAttributeValue("num") + ";" + testName + "\n");
                        }
                    }
                }
            }
        }
    }
}
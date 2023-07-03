/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.data;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author seylinsanchez
 */
public class DataXML {

    public static void writeXML(String[] tagNames, String[] tagValues, String fileName, String root, String elementName) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            File file = new File(fileName);
            Document doc;

            // Si el archivo no existe, crear uno nuevo con una raíz vacía
            if (!file.exists()) {
                doc = db.newDocument();
                Element rootElement = doc.createElement(root);
                doc.appendChild(rootElement);
            } else {
                // Si el archivo existe, parsearlo
                doc = db.parse(new File(fileName));
                doc.getDocumentElement().normalize();
            }

            Element rootElement = doc.getDocumentElement();

            // Crear el nuevo elemento
            Element newElement = doc.createElement(elementName);
            rootElement.appendChild(newElement);

            Attr attr = doc.createAttribute(tagNames[0]);
            attr.setValue(tagValues[0]);
            newElement.setAttributeNode(attr);

            // Agregar los atributos al nuevo elemento
            for (int i = 1; i < tagNames.length; i++) {
                Element dataElement = doc.createElement(tagNames[i]);
                dataElement.appendChild(doc.createTextNode(tagValues[i]));
                newElement.appendChild(dataElement);
            }

            // Escribir el documento modificado al archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            // Usar FileWriter con append=true para agregar información al final del archivo
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);

            System.out.println("Datos agregados al archivo " + fileName);
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException pce) {
        }
    }

}

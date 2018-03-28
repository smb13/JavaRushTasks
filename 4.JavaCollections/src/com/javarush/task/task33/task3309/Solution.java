package com.javarush.task.task33.task3309;




import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;


/*
Комментарий внутри xml
*/

public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment) throws SAXException, JAXBException, ParserConfigurationException, IOException, TransformerException {
        StringWriter writer = new StringWriter();
        String res = null;
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(obj, writer);

        System.out.println(writer.toString());


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //factory.setCoalescing(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(writer.toString().getBytes()));

        StreamResult streamResult = new StreamResult(new ByteArrayOutputStream());
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
        transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "yes");

        /*if (document.getElementsByTagName(tagName).getLength() == 0){
                DOMSource source = new DOMSource(document);
                transformer.transform(source, streamResult);
                return streamResult.getOutputStream().toString();
            }*/

        runThroughElements(document.getDocumentElement(), tagName, document, comment);

        DOMSource source = new DOMSource(document);
        transformer.transform(source, streamResult);
        res = streamResult.getOutputStream().toString();
        return res;
    }

    public static void runThroughElements (Node node, String tagName, Document document, String comm) {
        if (node == null) return;

        String nodeValue = node.getNodeValue();
        Node parent = node.getParentNode();
        if (nodeValue != null && node.getNodeType() == Node.TEXT_NODE && nodeValue.matches(".*[<>&\\'\\\"].*")){
            Node nextSibling = node.getNextSibling();
            CDATASection cdataSection = document.createCDATASection(nodeValue);
            parent.insertBefore(cdataSection, nextSibling);
            parent.removeChild(node);
            node = cdataSection;
        } else if (node.getNodeName().equals(tagName)) {
            Comment cmnt = document.createComment(comm);
            node.getParentNode().insertBefore(cmnt, node);
        }

        runThroughElements (node.getFirstChild(), tagName, document, comm);
        runThroughElements (node.getNextSibling(), tagName, document, comm);

    }


    public static void main(String[] args) throws SAXException, JAXBException, ParserConfigurationException, IOException,  TransformerException {
        System.out.println(toXmlWithComment(new First(), "second", "it's a comment"));
        System.out.println("--------------------------------------------");
        System.out.println(toXmlWithComment(new First(), "fake", "it's a comment"));
    }


}

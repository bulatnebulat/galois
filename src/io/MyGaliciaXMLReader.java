/*
 * Decompiled with CFR 0_114.
 */
package fr.lirmm.marel.gsh2.io;

import fr.lirmm.marel.gsh2.core.IBinaryContext;
import fr.lirmm.marel.gsh2.core.MyBinaryContext;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MyGaliciaXMLReader
implements ErrorHandler {
    InputStream input = null;

    public MyGaliciaXMLReader(InputStream input) {
        this.input = input;
    }

    public IBinaryContext readBinaryContext() throws Exception {
        DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
        DocumentBuilder DB = DBF.newDocumentBuilder();
        DB.setErrorHandler(this);
        Document doc = DB.parse(this.input);
        NodeList nl = doc.getElementsByTagName("BinaryContext");
        if (nl == null || nl.getLength() < 1) {
            throw new Exception("Not valid format for Galicia XML Binary Context");
        }
        Element root = (Element)nl.item(0);
        String name = "DefaultName";
        nl = root.getElementsByTagName("Name");
        if (nl != null && nl.getLength() > 0) {
            name = nl.item(0).getTextContent().trim();
        }
        int nbObj = Integer.parseInt(root.getAttributes().getNamedItem("numberObj").getNodeValue().trim());
        int nbAtt = Integer.parseInt(root.getAttributes().getNamedItem("numberAtt").getNodeValue().trim());
        MyBinaryContext binRel = new MyBinaryContext(nbObj, nbAtt, name);
        nl = root.getElementsByTagName("Object");
        int i = 0;
        while (i < nl.getLength()) {
            binRel.addObjectName(nl.item(i).getTextContent().trim());
            ++i;
        }
        nl = root.getElementsByTagName("Attribute");
        i = 0;
        while (i < nl.getLength()) {
            binRel.addAttributeName(nl.item(i).getTextContent().trim());
            ++i;
        }
        nl = root.getElementsByTagName("BinRel");
        int i2 = 0;
        while (i2 < nl.getLength()) {
            Element binRelElement = (Element)nl.item(i2);
            binRel.set(Integer.parseInt(binRelElement.getAttribute("idxO")), Integer.parseInt(binRelElement.getAttribute("idxA")), true);
            ++i2;
        }
        return binRel;
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        throw exception;
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        throw exception;
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        throw exception;
    }
}


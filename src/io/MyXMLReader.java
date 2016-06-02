package io;

import fr.lirmm.marel.gsh2.core.IBinaryContext;
import fr.lirmm.marel.gsh2.core.MyBinaryContext;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class MyXMLReader {
    InputStream inputStream = null;
    protected IBinaryContext result;

    public MyXMLReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void read() throws IOException {
        this.result = this.readBinaryContext();
    }

    public IBinaryContext readBinaryContext() throws IOException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(this.inputStream);
            MyBinaryContext binRel = new MyBinaryContext(1, 1, "XML binary context");
            return binRel;
        }
        catch (Exception e) {
            throw new IOException("Attempt to read XML format failed");
        }
    }

    public String getDescription() {
        return "XML Reader";
    }

    public IBinaryContext getBinaryContext() {
        return this.result;
    }
}


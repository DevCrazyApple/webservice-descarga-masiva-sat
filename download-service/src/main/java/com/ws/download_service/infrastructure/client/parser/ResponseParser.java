package com.ws.download_service.infrastructure.client.parser;

import org.w3c.dom.Document;
import static com.ws.download_service.infrastructure.client.util.XmlUtils.convertStringToXMLDocument;

public class ResponseParser {

    /**
     *
     * @param response
     * @return
     */
    public String getResult(String response) {
        Document doc = convertStringToXMLDocument(response);

        //Verify XML document is build correctly
        if (doc != null) {
            return doc.getElementsByTagName("Paquete")
                .item(0)
                .getTextContent();
        }
        return null;
    }
}

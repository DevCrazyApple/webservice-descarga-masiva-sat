package com.ws.request_service.infrastructure.client.parser;

import org.w3c.dom.Document;

import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ws.request_service.infrastructure.client.util.XmlUtils.convertStringToXMLDocument;

public class ResponseParser {

    /**
     * Get token of a previously obtained XML
     * @param response
     * @return
     */
    public String emitionGetResult(String response) {
        Document doc = convertStringToXMLDocument(response);

        //Verify XML document is build correctly
        if (doc != null)
            return doc.getElementsByTagName("SolicitaDescargaEmitidosResult")
                .item(0)
                .getAttributes()
                .getNamedItem("IdSolicitud").getTextContent();

        return null;
    }

    /**
     * Get token of a previously obtained XML
     * @param response
     * @return
     */
    public String receptionGetResult(String response) {
        Document doc = convertStringToXMLDocument(response);

        //Verify XML document is build correctly
        if (doc != null)
            return doc.getElementsByTagName("SolicitaDescargaRecibidosResult")
                    .item(0)
                    .getAttributes()
                    .getNamedItem("IdSolicitud").getTextContent();

        return null;
    }
}

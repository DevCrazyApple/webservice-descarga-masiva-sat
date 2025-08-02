package com.ws.status_service.infrastructure.client.parser;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ws.status_service.infrastructure.client.util.XmlUtils.convertStringToXMLDocument;

public class ResponseParser {

    /**
     * Get token of a previously obtained XML
     * @param response
     * @return
     */
    public String getResult(String response) {
        Document doc = convertStringToXMLDocument(response);

        //Verify XML document is build correctly
        if (doc != null) {
            int stateRequest = Integer.parseInt(doc.getElementsByTagName("VerificaSolicitudDescargaResult")
                    .item(0)
                    .getAttributes()
                    .getNamedItem("EstadoSolicitud").getTextContent());

            int codStatus = Integer.parseInt(doc.getElementsByTagName("VerificaSolicitudDescargaResult")
                    .item(0)
                    .getAttributes()
                    .getNamedItem("CodEstatus").getTextContent());

            if (stateRequest == 3) {
                var packages = new ArrayList<>();
                var count = doc.getElementsByTagName("IdsPaquetes").getLength();
                IntStream.range(0, count).forEachOrdered(n -> {
                    packages.add(doc.getElementsByTagName("IdsPaquetes").item(n).getTextContent());
                });

                return packages.stream().map(Object::toString).collect(Collectors.joining(","));
            } else {
                if (codStatus == 300) {
                    return "Token invalido";
                }
            }
        }

        return null;
    }
}

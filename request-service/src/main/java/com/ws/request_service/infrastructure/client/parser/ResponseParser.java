package com.ws.request_service.infrastructure.client.parser;

import com.ws.request_service.domain.exception.SatException;
import com.ws.request_service.infrastructure.client.parser.enums.Solicitud;
import org.w3c.dom.Document;


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
        if (doc != null) {
            int codeVerificar = Integer.parseInt(doc.getElementsByTagName("SolicitaDescargaEmitidosResult")
                    .item(0)
                    .getAttributes()
                    .getNamedItem("CodEstatus")
                    .getTextContent());

            try {
                return doc.getElementsByTagName("SolicitaDescargaEmitidosResult")
                        .item(0)
                        .getAttributes()
                        .getNamedItem("IdSolicitud").getTextContent();
            } catch (Exception e) {
                throw new SatException(CustomCodeResolver.resolveCode(codeVerificar).getMessage());
            }
        }

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
        if (doc != null) {

            int codeVerificar = Integer.parseInt(doc.getElementsByTagName("SolicitaDescargaRecibidosResult")
                    .item(0)
                    .getAttributes()
                    .getNamedItem("CodEstatus")
                    .getTextContent());

            try {
                return doc.getElementsByTagName("SolicitaDescargaRecibidosResult")
                        .item(0)
                        .getAttributes()
                        .getNamedItem("IdSolicitud").getTextContent();
            } catch (Exception e) {
                throw new SatException(CustomCodeResolver.resolveCode(codeVerificar).getMessage());
            }
        }

        return null;
    }


    /**
     * Get token of a previously obtained XML
     * @param response
     * @return
     */
    public String foilGetResult(String response) {
        Document doc = convertStringToXMLDocument(response);

        // Verify XML document is build correctly
        if (doc != null) {
            int codeVerificar = Integer.parseInt(doc.getElementsByTagName("SolicitaDescargaFolioResult")
                    .item(0)
                    .getAttributes()
                    .getNamedItem("CodEstatus")
                    .getTextContent());

            try {
                return doc.getElementsByTagName("SolicitaDescargaFolioResult")
                        .item(0)
                        .getAttributes()
                        .getNamedItem("IdSolicitud").getTextContent();
            } catch (Exception e) {
                throw new SatException(CustomCodeResolver.resolveCode(codeVerificar).getMessage());
            }
        }

        return null;
    }
}

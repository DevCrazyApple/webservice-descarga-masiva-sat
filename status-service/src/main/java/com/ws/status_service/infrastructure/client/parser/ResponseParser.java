package com.ws.status_service.infrastructure.client.parser;

import com.ws.status_service.domain.model.VerifyModel;
import com.ws.status_service.infrastructure.client.parser.enums.BaseCodes;
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
    public VerifyModel getResult(String response) {
        Document doc = convertStringToXMLDocument(response);

        //Verify XML document is build correctly
        if (doc != null) {

            int codeSolicitud = BaseCodes.Unknow.getCode();
            try {
                codeSolicitud = Integer.parseInt(doc.getElementsByTagName("VerificaSolicitudDescargaResult")
                        .item(0)
                        .getAttributes()
                        .getNamedItem("CodigoEstadoSolicitud")
                        .getTextContent());
            } catch (Exception e) {

            }

            int codeVerificar = Integer.parseInt(doc.getElementsByTagName("VerificaSolicitudDescargaResult")
                    .item(0)
                    .getAttributes()
                    .getNamedItem("CodEstatus")
                    .getTextContent());

            int statusCode = Integer.parseInt(doc.getElementsByTagName("VerificaSolicitudDescargaResult")
                    .item(0)
                    .getAttributes()
                    .getNamedItem("EstadoSolicitud")
                    .getTextContent());

            VerifyModel verifyModel = new VerifyModel(
                    CustomCodeResolver.resolveCode(codeSolicitud).getMessage(),
                    CustomCodeResolver.resolveCode(codeVerificar).getMessage(),
                    CustomCodeResolver.resolveCode(statusCode).getMessage()
            );


            if (statusCode == 3) {
                var packages = new ArrayList<>();
                var count = doc.getElementsByTagName("IdsPaquetes").getLength();
                IntStream.range(0, count).forEachOrdered(n -> {
                    packages.add(doc.getElementsByTagName("IdsPaquetes").item(n).getTextContent());
                });

                verifyModel.setPackagesIds(packages.stream().map(Object::toString).collect(Collectors.joining(",")));
            }

            return verifyModel;
        }

        return null;
    }
}

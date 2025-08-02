package com.ws.status_service.infrastructure.client.util;

import com.ws.status_service.domain.model.StatusModel;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.security.cert.CertificateEncodingException;
import java.util.Base64;

@Slf4j
@UtilityClass
public class XmlUtils {

    /**
     * Convert a String to XMl (Document Object)
     *
     * @param xmlString
     * @return
     */
    public static Document convertStringToXMLDocument(String xmlString) {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            return builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
//            e.printStackTrace();
            log.error(String.valueOf(e));
        }

        return null;
    }

    /**
     * create build timestamp
     * @param statusModel
     * @return
     */
    public static String buildTimestamp(StatusModel statusModel) {
        var canonicalTimestamp = new StringBuilder();
        canonicalTimestamp.append("<des:VerificaSolicitudDescarga xmlns:des=\"http://DescargaMasivaTerceros.sat.gob.mx\">")
            .append("<des:solicitud IdSolicitud=\"").append(statusModel.getIdRequest())
            .append("\" RfcSolicitante=\"").append(statusModel.getRfcSolicitante())
            .append(">")
            .append("</des:solicitud>")
            .append("</des:VerificaSolicitudDescarga>");

        return canonicalTimestamp.toString();
    }

    public static String buildSignedInfo(String digest) {
        var canonicalSignedInfo = new StringBuilder();
        canonicalSignedInfo.append("<SignedInfo xmlns=\"http://www.w3.org/2000/09/xmldsig#\">")
            .append("<CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"></CanonicalizationMethod>")
            .append("<SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"></SignatureMethod>")
            .append("<Reference URI=\"#_0\">")
            .append("<Transforms>")
            .append("<Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"></Transform>")
            .append("</Transforms>")
            .append("<DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"></DigestMethod>")
            .append("<DigestValue>").append(digest).append("</DigestValue>")
            .append("</Reference>")
            .append("</SignedInfo>");

        return canonicalSignedInfo.toString();
    }

    public static String buildEnvelope(String digest, String signature, StatusModel statusModel) throws CertificateEncodingException {
        var request_xml = new StringBuilder();
         request_xml.append("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:u=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:des=\"http://DescargaMasivaTerceros.sat.gob.mx\" xmlns:xd=\"http://www.w3.org/2000/09/xmldsig#\">")
             .append("<s:Header/>")
             .append("<s:Body>")
             .append("<des:VerificaSolicitudDescarga>")
             .append("<des:solicitud IdSolicitud=\"").append(statusModel.getIdRequest())
             .append("\" RfcSolicitante=\"").append(statusModel.getRfcSolicitante())
             .append("\">")
             .append("<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">")
             .append("<SignedInfo>")
             .append("<CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>")
             .append("<SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>")
             .append("<Reference URI=\"#_0\">")
             .append("<Transforms>")
             .append("<Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>")
             .append("</Transforms>")
             .append("<DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>")
             .append("<DigestValue>").append(digest).append("</DigestValue>")
             .append("</Reference>")
             .append("</SignedInfo>")
             .append("<SignatureValue>").append(signature).append("</SignatureValue>")
             .append("<KeyInfo>")
             .append("<X509Data>")
             .append("<X509IssuerSerial>")
             .append("<X509IssuerName>")
             .append(statusModel.getCertificate().getIssuerX500Principal())
             .append("</X509IssuerName>")
             .append("<X509SerialNumber>")
             .append(statusModel.getCertificate().getSerialNumber())
             .append("</X509SerialNumber>")
             .append("</X509IssuerSerial>")
             .append("<X509Certificate>")
             .append(Base64.getEncoder().encodeToString(statusModel.getCertificate().getEncoded()))
             .append("</X509Certificate>")
             .append("</X509Data>")
             .append("</KeyInfo>")
             .append("</Signature>")
             .append("</des:solicitud>")
             .append("</des:VerificaSolicitudDescarga>")
             .append("</s:Body>")
             .append("</s:Envelope>");

        return request_xml.toString();
    }

}

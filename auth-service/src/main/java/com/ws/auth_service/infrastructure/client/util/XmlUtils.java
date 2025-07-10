package com.ws.auth_service.infrastructure.client.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
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

    public static String buildTimestamp(String created, String expires) {
        var canonicalTimestamp = new StringBuilder();
        canonicalTimestamp.append("<u:Timestamp xmlns:u=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" u:Id=\"_0\">")
            .append("<u:Created>").append(created).append("</u:Created>")
            .append("<u:Expires>").append(expires).append("</u:Expires>")
            .append("</u:Timestamp>");

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

    public static String buildEnvelope(String created, String expires, String uuid, String digest, String signature, X509Certificate certificate) throws CertificateEncodingException {
        var request_xml = new StringBuilder();
         request_xml.append("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:u=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">")
             .append("<s:Header>")
             .append("<o:Security s:mustUnderstand=\"1\" xmlns:o=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">")
             .append("<u:Timestamp u:Id=\"_0\">")
             .append("<u:Created>").append(created).append("</u:Created>")
             .append("<u:Expires>").append(expires).append("</u:Expires>")
             .append("</u:Timestamp>")
             .append("<o:BinarySecurityToken u:Id=\"").append(uuid)
             .append("\" ValueType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3\" EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">")
             .append(Base64.getEncoder().encodeToString(certificate.getEncoded()))
             .append("</o:BinarySecurityToken>")
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
             .append("<o:SecurityTokenReference>")
             .append("<o:Reference ValueType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3\" URI=\"#").append(uuid).append("\"/>")
             .append("</o:SecurityTokenReference>")
             .append("</KeyInfo>")
             .append("</Signature>")
             .append("</o:Security>")
             .append("</s:Header>")
             .append("<s:Body>")
             .append("<Autentica xmlns=\"http://DescargaMasivaTerceros.gob.mx\"/>")
             .append("</s:Body>")
             .append("</s:Envelope>");

        return request_xml.toString();
    }
}

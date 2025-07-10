package com.ws.auth_service.infrastructure.client.parser;

import org.w3c.dom.Document;

import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ws.auth_service.infrastructure.client.util.XmlUtils.convertStringToXMLDocument;

public class AuthResponseParser {

    /**
     * Get token of a previously obtained XML
     * @param response
     * @return
     */
    public String extractToken(String response) {
        Document doc = convertStringToXMLDocument(response);

        //Verify XML document is build correctly
        if (doc != null)
            return doc.getElementsByTagName("AutenticaResult").item(0).getTextContent();

        return null;
    }

    /**
     * extract rfc from pem
     * @param certificate
     * @return
     */
    public String extractRfc(X509Certificate certificate) {
        String subject = certificate.getSubjectX500Principal().toString();
        System.out.println("Subject: " + subject);

        // Buscar el RFC en el campo serialNumber o directamente en el subject
        Pattern pattern = Pattern.compile("OID\\.2\\.5\\.4\\.45=([A-Z0-9]{12,13})");
        Matcher matcher = pattern.matcher(subject);
        if (matcher.find()) {
            return matcher.group(1); // Retorna el RFC
        }
        return null;
    }
}

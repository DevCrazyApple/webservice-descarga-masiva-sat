package com.ws.request_service.infrastructure.client.util;

import com.ws.request_service.domain.model.FoilModel;
import com.ws.request_service.domain.model.RequestModel;
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
     * Build the SignedInfo XML element
     *
     * @param digest
     * @return
     */
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


    /**
     * create emition build timestamp
     *
     * @param requestModel
     * @return
     */
    public static String emitionBuildTimestamp(RequestModel requestModel) {
        var canonicalTimestamp = new StringBuilder();
        canonicalTimestamp.append("<des:SolicitaDescargaEmitidos xmlns:des=\"http://DescargaMasivaTerceros.sat.gob.mx\">")
                .append("<des:solicitud RfcEmisor=\"").append(requestModel.getRfcSolicitante())
                .append("\" EstadoComprobante=\"").append(requestModel.getEstadoComprobante())
                .append("\" FechaInicial=\"").append(requestModel.getFechaInicial())
                .append("\" FechaFinal=\"").append(requestModel.getFechaFinal())
                .append("\" TipoComprobante=\"").append(requestModel.getTipoComprobante())
                .append("\" TipoSolicitud=\"").append(requestModel.getTipoSolicitud())
                .append("\">")
                .append("</des:solicitud>")
                .append("<des:RfcReceptores><des:RfcReceptor>")
                .append(requestModel.getRfcReceptor())
                .append("</des:RfcReceptor></des:RfcReceptores>")
                .append("</des:SolicitaDescargaEmitidos>");

        return canonicalTimestamp.toString();
    }


    /**
     * Build the full SOAP XML envelope with security headers
     * @param digest
     * @param signature
     * @param requestModel
     * @return
     * @throws CertificateEncodingException
     */
    public static String emitionBuildEnvelope(String digest, String signature, RequestModel requestModel) throws CertificateEncodingException {
        var request_xml = new StringBuilder();
        request_xml.append("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:u=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:des=\"http://DescargaMasivaTerceros.sat.gob.mx\" xmlns:xd=\"http://www.w3.org/2000/09/xmldsig#\">")
                .append("<s:Header/>")
                .append("<s:Body>")
                .append("<des:SolicitaDescargaEmitidos>")
                .append("<des:solicitud RfcEmisor=\"").append(requestModel.getRfcSolicitante())
                .append("\" EstadoComprobante=\"").append(requestModel.getEstadoComprobante())
                .append("\" FechaInicial=\"").append(requestModel.getFechaInicial())
                .append("\" FechaFinal=\"").append(requestModel.getFechaFinal())
                .append("\" TipoComprobante=\"").append(requestModel.getTipoComprobante())
                .append("\" TipoSolicitud=\"").append(requestModel.getTipoSolicitud())
                .append("\">")
                .append("<des:RfcReceptores><des:RfcReceptor>")
                .append(requestModel.getRfcReceptor())
                .append("</des:RfcReceptor></des:RfcReceptores>")
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
                .append(requestModel.getCertificate().getIssuerX500Principal())
                .append("</X509IssuerName>")
                .append("<X509SerialNumber>")
                .append(requestModel.getCertificate().getSerialNumber())
                .append("</X509SerialNumber>")
                .append("</X509IssuerSerial>")
                .append("<X509Certificate>")
                .append(Base64.getEncoder().encodeToString(requestModel.getCertificate().getEncoded()))
                .append("</X509Certificate>")
                .append("</X509Data>")
                .append("</KeyInfo>")
                .append("</Signature>")
                .append("</des:solicitud>")
                .append("</des:SolicitaDescargaEmitidos>")
                .append("</s:Body>")
                .append("</s:Envelope>");

        return request_xml.toString();
    }


    /**
     * create reception build timestamp
     *
     * @param requestModel
     * @return
     */
    public static String receptionBuildTimestamp(RequestModel requestModel) {
        var canonicalTimestamp = new StringBuilder();
        canonicalTimestamp.append("<des:SolicitaDescargaRecibidos xmlns:des=\"http://DescargaMasivaTerceros.sat.gob.mx\">")
                .append("<des:solicitud RfcReceptor=\"").append(requestModel.getRfcSolicitante())
                .append("\" EstadoComprobante=\"").append(requestModel.getEstadoComprobante())
                .append("\" FechaInicial=\"").append(requestModel.getFechaInicial())
                .append("\" FechaFinal=\"").append(requestModel.getFechaFinal())
                .append("\" TipoComprobante=\"").append(requestModel.getTipoComprobante())
                .append("\" TipoSolicitud=\"").append(requestModel.getTipoSolicitud())
                .append("\">")
                .append("</des:solicitud>")
                .append("</des:SolicitaDescargaEmitidos>");
        return canonicalTimestamp.toString();
    }


    /**
     * Build the full SOAP XML envelope with security headers
     * @param digest
     * @param signature
     * @param requestModel
     * @return
     * @throws CertificateEncodingException
     */
    public static String receptionBuildEnvelope(String digest, String signature, RequestModel requestModel) throws CertificateEncodingException {
        var request_xml = new StringBuilder();
        request_xml.append("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:u=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:des=\"http://DescargaMasivaTerceros.sat.gob.mx\" xmlns:xd=\"http://www.w3.org/2000/09/xmldsig#\">")
            .append("<s:Header/>")
            .append("<s:Body>")
            .append("<des:SolicitaDescargaRecibidos>")
            .append("<des:solicitud RfcReceptor=\"").append(requestModel.getRfcSolicitante())
            .append("\" EstadoComprobante=\"").append(requestModel.getEstadoComprobante())
            .append("\" FechaInicial=\"").append(requestModel.getFechaInicial())
            .append("\" FechaFinal=\"").append(requestModel.getFechaFinal())
            .append("\" TipoComprobante=\"").append(requestModel.getTipoComprobante())
            .append("\" TipoSolicitud=\"").append(requestModel.getTipoSolicitud())
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
            .append(requestModel.getCertificate().getIssuerX500Principal())
            .append("</X509IssuerName>")
            .append("<X509SerialNumber>")
            .append(requestModel.getCertificate().getSerialNumber())
            .append("</X509SerialNumber>")
            .append("</X509IssuerSerial>")
            .append("<X509Certificate>")
            .append(Base64.getEncoder().encodeToString(requestModel.getCertificate().getEncoded()))
            .append("</X509Certificate>")
            .append("</X509Data>")
            .append("</KeyInfo>")
            .append("</Signature>")
            .append("</des:solicitud>")
            .append("</des:SolicitaDescargaRecibidos>")
            .append("</s:Body>")
            .append("</s:Envelope>");

        return request_xml.toString();
    }


    /**
     * create emition build timestamp
     *
     * @param foilModel
     * @return
     */
    public static String foilBuildTimestamp(FoilModel foilModel) {
        var canonicalTimestamp = new StringBuilder();
        canonicalTimestamp.append("<des:SolicitaDescargaFolio xmlns:des=\"http://DescargaMasivaTerceros.sat.gob.mx\">")
                .append("<des:solicitud Folio=\"").append(foilModel.getFolio())
                .append("</des:SolicitaDescargaFolio>");

        return canonicalTimestamp.toString();
    }


    /**
     * Build the full SOAP XML envelope with security headers
     * @param digest
     * @param signature
     * @param foilModel
     * @return
     * @throws CertificateEncodingException
     */
    public static String foilBuildEnvelope(String digest, String signature, FoilModel foilModel) throws CertificateEncodingException {
        var request_xml = new StringBuilder();
        request_xml.append("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:u=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" xmlns:des=\"http://DescargaMasivaTerceros.sat.gob.mx\" xmlns:xd=\"http://www.w3.org/2000/09/xmldsig#\">")
                .append("<s:Header/>")
                .append("<s:Body>")
                .append("<des:SolicitaDescargaFolio>")
                .append("<des:solicitud Folio=\"").append(foilModel.getFolio())
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
                .append(foilModel.getCertificate().getIssuerX500Principal())
                .append("</X509IssuerName>")
                .append("<X509SerialNumber>")
                .append(foilModel.getCertificate().getSerialNumber())
                .append("</X509SerialNumber>")
                .append("</X509IssuerSerial>")
                .append("<X509Certificate>")
                .append(Base64.getEncoder().encodeToString(foilModel.getCertificate().getEncoded()))
                .append("</X509Certificate>")
                .append("</X509Data>")
                .append("</KeyInfo>")
                .append("</Signature>")
                .append("</des:solicitud>")
                .append("</des:SolicitaDescargaFolio>")
                .append("</s:Body>")
                .append("</s:Envelope>");

        return request_xml.toString();
    }
}

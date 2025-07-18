package com.ws.request_service.infrastructure.client.builder;

import com.ws.request_service.application.command.RequestDownloadCommand;
import com.ws.request_service.domain.model.RequestModel;
import lombok.extern.slf4j.Slf4j;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

import static com.ws.request_service.infrastructure.client.util.CryptoUtils.createDigest;
import static com.ws.request_service.infrastructure.client.util.CryptoUtils.sign;
import static com.ws.request_service.infrastructure.client.util.XmlUtils.*;

@Slf4j
public class XmlBuilder {

    private String created;
    private String expires;
    private String uuid;

    public String buildEmition(RequestModel requestModel) throws Exception {

        // create variables datetime
        String fechaInicial = requestModel.getFechaInicial();
        String fechaFinal = requestModel.getFechaFinal();
        String tipoComprobante = requestModel.getTipoComprobante();
        String tipoSolicitud = requestModel.getTipoSolicitud();
        String rfcReceptor = requestModel.getRfcReceptor();

        return null;
//        String canonicalTimestamp = buildTimestamp(this.created, this.expires);
//        String digest = createDigest(canonicalTimestamp);
//        String canonicalSignedInfo = buildSignedInfo(digest);
//        String signature = sign(canonicalSignedInfo, privateKey);
//
//        return buildEnvelope(this.created, this.expires, this.uuid, digest, signature, certificate);
    }

}

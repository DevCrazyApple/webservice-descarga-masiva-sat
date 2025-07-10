package com.ws.auth_service.infrastructure.client.builder;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

import static com.ws.auth_service.infrastructure.client.util.CryptoUtils.createDigest;
import static com.ws.auth_service.infrastructure.client.util.CryptoUtils.sign;
import static com.ws.auth_service.infrastructure.client.util.XmlUtils.*;

public class AuthXmlBuilder {

    private String created;
    private String expires;
    private String uuid;

    public String build(X509Certificate certificate, PrivateKey privateKey) throws Exception {

        // create variables datetime
        setTimeStamp();

        String canonicalTimestamp = buildTimestamp(this.created, this.expires);
        String digest = createDigest(canonicalTimestamp);
        String canonicalSignedInfo = buildSignedInfo(digest);
        String signature = sign(canonicalSignedInfo, privateKey);

        return buildEnvelope(this.created, this.expires, this.uuid, digest, signature, certificate);
    }

    private void setTimeStamp() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendarNow = Calendar.getInstance();

        this.created = simpleDateFormat.format(calendarNow.getTime());
        calendarNow.add(Calendar.SECOND, 5 * 60); // Add 300 seconds which equals 5 minutes

        this.expires = simpleDateFormat.format(calendarNow.getTime());
        this.uuid = "uuid-" + UUID.randomUUID() + "-1";
    }

}

package com.ws.status_service.infrastructure.client.builder;

import com.ws.status_service.domain.model.StatusModel;
import lombok.extern.slf4j.Slf4j;

import static com.ws.status_service.infrastructure.client.util.CryptoUtils.createDigest;
import static com.ws.status_service.infrastructure.client.util.CryptoUtils.sign;
import static com.ws.status_service.infrastructure.client.util.XmlUtils.*;

@Slf4j
public class XmlBuilder {

    public String build(StatusModel statusModel) throws Exception {

        String canonicalTimestamp = buildTimestamp(statusModel);
        String digest = createDigest(canonicalTimestamp);
        String canonicalSignedInfo = buildSignedInfo(digest);
        String signature = sign(canonicalSignedInfo, statusModel.getPrivateKey());
        return buildEnvelope(digest, signature, statusModel);
    }

}

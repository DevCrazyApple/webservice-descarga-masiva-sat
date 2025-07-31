package com.ws.request_service.infrastructure.client.builder;

import com.ws.request_service.domain.model.RequestModel;
import lombok.extern.slf4j.Slf4j;


import static com.ws.request_service.infrastructure.client.util.CryptoUtils.createDigest;
import static com.ws.request_service.infrastructure.client.util.CryptoUtils.sign;
import static com.ws.request_service.infrastructure.client.util.XmlUtils.*;

@Slf4j
public class XmlBuilder {

    public String buildEmition(RequestModel requestModel) throws Exception {

        String canonicalTimestamp = emitionBuildTimestamp(requestModel);
        String digest = createDigest(canonicalTimestamp);
        String canonicalSignedInfo = buildSignedInfo(digest);
        String signature = sign(canonicalSignedInfo, requestModel.getPrivateKey());
        return emitionBuildEnvelope(digest, signature, requestModel);
    }

    public String buildReception(RequestModel requestModel) throws Exception {
        String canonicalTimestamp = receptionBuildTimestamp(requestModel);
        String digest = createDigest(canonicalTimestamp);
        String canonicalSignedInfo = buildSignedInfo(digest);
        String signature = sign(canonicalSignedInfo, requestModel.getPrivateKey());
        return receptionBuildEnvelope(digest, signature, requestModel);
    }

}

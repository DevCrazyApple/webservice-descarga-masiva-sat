package com.ws.download_service.infrastructure.client.builder;

import com.ws.download_service.domain.model.DownloadModel;
import lombok.extern.slf4j.Slf4j;

import static com.ws.download_service.infrastructure.client.util.CryptoUtils.createDigest;
import static com.ws.download_service.infrastructure.client.util.CryptoUtils.sign;
import static com.ws.download_service.infrastructure.client.util.XmlUtils.*;

@Slf4j
public class XmlBuilder {

    public String build(DownloadModel model) throws Exception {

        String canonicalTimestamp = buildTimestamp(model);
        String digest = createDigest(canonicalTimestamp);
        String canonicalSignedInfo = buildSignedInfo(digest);
        String signature = sign(canonicalSignedInfo, model.getPrivateKey());
        return buildEnvelope(digest, signature, model);
    }

}

package com.ws.download_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DownloadModel {
    private String rfcSolicitante;
    private String idpackage;
    private String token;
    private X509Certificate certificate;
    private RSAPrivateKey privateKey;
}

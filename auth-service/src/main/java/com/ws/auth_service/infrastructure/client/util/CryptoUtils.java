package com.ws.auth_service.infrastructure.client.util;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Slf4j
public class CryptoUtils {

    /**
     * Parse pfx file pem
     * @param pem
     * @return
     */
    public static byte[] parseDERFromPEM(String pem, String beginDelimiter, String endDelimiter) {
        try {
            String[] tokens = pem.split(beginDelimiter);
            tokens = tokens[1].split(endDelimiter);
            return Base64.getDecoder().decode(tokens[0]);
        } catch (Exception e) {
            return Base64.getDecoder().decode(pem);
        }
    }

    /**
     * Get a private key through a pfx file
     * @param keyBytes
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static RSAPrivateKey generatePrivateKeyFromDER(byte[] keyBytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory factory = KeyFactory.getInstance("RSA");

        return (RSAPrivateKey)factory.generatePrivate(spec);
    }

    /**
     * Get a certificate through a pfx file
     * @param certBytes
     * @return
     * @throws CertificateException
     */
    public static X509Certificate generateCertificateFromDER(byte[] certBytes) throws CertificateException {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");

        return (X509Certificate)factory.generateCertificate(new ByteArrayInputStream(certBytes));
    }

    /**
     * Decodes a URL encoded string using `UTF-8`
     *
     * @param value
     * @return
     */
    public static String decodeValue(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    /**
     * Create digest SHA1 from a String and returning a Base64 String
     *
     * @param sourceData
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String createDigest(String sourceData) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(sourceData.getBytes());

        return Base64.getEncoder().encodeToString(digest.digest());
    }

    /**
     * Sign SHA1 with private key and a String and returning a Base64 String
     *
     * @param sourceData
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static String sign(String sourceData, PrivateKey privateKey) throws
            NoSuchAlgorithmException,
            InvalidKeyException,
            SignatureException {
        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initSign(privateKey);
        sig.update(sourceData.getBytes());

        return Base64.getEncoder().encodeToString(sig.sign());
    }
}

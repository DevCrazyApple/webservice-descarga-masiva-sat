package com.ws.request_service.infrastructure.client;

import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SoapClient {

    private final String endpoint;
    private final String soapAction;

    public SoapClient(String endpoint, String soapAction) {
        this.endpoint = endpoint;
        this.soapAction = soapAction;
    }

    public String send(String soapXml, String authorizationHeader) throws Exception {
        HttpURLConnection conn = getUrlConnection(authorizationHeader);

        // Write XML
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(soapXml.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

        InputStream inputStream = conn.getInputStream();

        // Read XML
        byte[] res = new byte[2048];
        int i;
        StringBuilder response = new StringBuilder();
        while ((i = inputStream.read(res)) != -1) {
            response.append(new String(res, 0, i));
        }
        inputStream.close();
        return response.toString();
    }

    private HttpURLConnection getUrlConnection(String authorizationHeader) throws IOException {
        URL url = new URL(this.endpoint);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set timeout as per needs
        conn.setConnectTimeout(20000);
        conn.setReadTimeout(20000);

        // Set DoOutput to true if you want to use URLConnection for output.
        // Default is false
        conn.setDoOutput(true);

        // Set Headers
        conn.setRequestProperty("Accept-Charset", "UTF_8");
        conn.setRequestProperty("Content-type", "text/xml; charset=utf-8");
        conn.setRequestProperty("SOAPAction", this.soapAction);

        if (authorizationHeader != null)
            conn.setRequestProperty("Authorization", authorizationHeader);
        return conn;
    }
}


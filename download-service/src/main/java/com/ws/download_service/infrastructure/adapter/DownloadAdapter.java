package com.ws.download_service.infrastructure.adapter;

import com.ws.download_service.domain.exception.TokenNotFoundException;
import com.ws.download_service.domain.model.DownloadModel;
import com.ws.download_service.domain.model.PackageModel;
import com.ws.download_service.domain.model.PfxModel;
import com.ws.download_service.domain.port.outbound.DownloadRequestOut;
import com.ws.download_service.domain.port.outbound.TokenRequestOut;
import com.ws.download_service.infrastructure.client.SoapClient;
import com.ws.download_service.infrastructure.client.builder.XmlBuilder;
import com.ws.download_service.infrastructure.client.parser.ResponseParser;
import com.ws.download_service.infrastructure.redis.TokenCacheAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static com.ws.download_service.infrastructure.client.util.CryptoUtils.generateCertificateFromDER;
import static com.ws.download_service.infrastructure.client.util.CryptoUtils.generatePrivateKeyFromDER;

@Slf4j
@Component
public class DownloadAdapter implements DownloadRequestOut, TokenRequestOut {

    private final String API_URL_AUTH = "http://auth-service:8080/api/auth/pfx/{rfc}";
    private final String API_URL_STATUS = "http://status-service:8080/api/status/package/{idrequest}";

    private final RestTemplate restTemplate;
    private final TokenCacheAdapter tokenCacheAdapter;
    private final XmlBuilder builder;
    private final ResponseParser parser;
    private final SoapClient client;

    public DownloadAdapter(RestTemplate restTemplate, TokenCacheAdapter tokenCacheAdapter, XmlBuilder builder, ResponseParser parser, SoapClient client) {
        this.restTemplate = restTemplate;
        this.tokenCacheAdapter = tokenCacheAdapter;
        this.builder = builder;
        this.parser = parser;
        this.client = client;
    }

    @Override
    public void getDownload(PackageModel model) throws Exception {

        // create folder
        String pathfile = "/home/shernandez/Descargas/TestDescargasMasivas/" + model.getRfcSolicitante() + "/";
        makeFolder(pathfile);

        // get token from redis
        String token = getToken(model.getRfcSolicitante());

        // obtenemos el pfx haciendo una llamada al microservicio de auth
        PfxModel outPfx = getPfx(model.getRfcSolicitante());

        byte[] keyBytes = Base64.getDecoder().decode(outPfx.getKey());
        RSAPrivateKey privateKey = generatePrivateKeyFromDER(keyBytes);

        byte[] certBytes = Base64.getDecoder().decode(outPfx.getCert());
        X509Certificate certificate = generateCertificateFromDER(certBytes);

        // traemos los registros
        PackageModel fromStatusService = this.getPackage(model.getIdRequest());

        List<String> idPackages = new ArrayList<>(Arrays.asList(fromStatusService.getPackagesIds().split(",")));
        for ( String idpackage : idPackages ) {

            // init model
            DownloadModel downloadModel = new DownloadModel(
                model.getRfcSolicitante(),
                idpackage,
                token,
                certificate,
                privateKey
            );

            // build request
            String request = this.builder.build(downloadModel);
            log.info("**** request:\n{}", request);

            // get response
            String response = this.client.send(request, downloadModel.getToken());
            log.info("**** response:\n{}", response);

            String b64content = this.parser.getResult(response);
            String pathname = String.format("%s", pathfile + idpackage);
            sevenZipfiles(pathname, b64content);
        }
    }

    @Override
    public String getToken(String rfc) {
        return this.tokenCacheAdapter.getToken(rfc)
            .orElseThrow(() -> new TokenNotFoundException(rfc));
    }

    @Override
    public PfxModel getPfx(String rfc) {
        return this.restTemplate.getForObject(API_URL_AUTH, PfxModel.class, rfc);
    }

    @Override
    public PackageModel getPackage(String idrequest) {
        return this.restTemplate.getForObject(API_URL_STATUS, PackageModel.class, idrequest);
    }

    public static void makeFolder(String pathfile) {
        Path route = Paths.get(pathfile);
        try {
            Files.createDirectories(route);
            log.info("Carpeta creada exitosamente.");
        } catch (IOException e) {
            log.warn("Error al crear la carpeta: " + e.getMessage());
        }
    }

    public static void sevenZipfiles(String pathname, String base64Content) throws IOException {

        // Ruta donde se va a guardar
        Path outputPath = Path.of(pathname + ".7z");

        // Decodifica el base64
        byte[] sevenZipBytes = Base64.getDecoder().decode(base64Content);

        // Escribe el archivo
        Files.write(outputPath, sevenZipBytes);

        log.info("Archivo .7z guardado en: " + outputPath.toAbsolutePath());

    }

}

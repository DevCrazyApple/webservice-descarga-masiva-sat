package com.ws.download_service.application.service;

import com.ws.download_service.domain.model.PackageModel;
import com.ws.download_service.domain.model.PfxModel;
import com.ws.download_service.domain.port.inbound.DownloadRequestIn;
import com.ws.download_service.domain.port.inbound.TokenRequestIn;
import com.ws.download_service.domain.port.outbound.DownloadRequestOut;
import com.ws.download_service.domain.port.outbound.TokenRequestOut;
import org.springframework.stereotype.Service;

@Service
public class DownloadService implements DownloadRequestIn, TokenRequestIn {

    private final DownloadRequestOut downloadRequestOut;
    private final TokenRequestOut tokenRequestOut;

    public DownloadService(DownloadRequestOut downloadRequestOut, TokenRequestOut tokenRequestOut) {
        this.downloadRequestOut = downloadRequestOut;
        this.tokenRequestOut = tokenRequestOut;
    }

    @Override
    public void getDownload(PackageModel model) throws Exception {
        this.downloadRequestOut.getDownload(model);
    }

    @Override
    public PackageModel getPackage(String idrequest) {
        return this.downloadRequestOut.getPackage(idrequest);
    }

    @Override
    public String getToken(String rfc) {
        return this.tokenRequestOut.getToken(rfc);
    }

    @Override
    public PfxModel getPfx(String rfc) {
        return this.tokenRequestOut.getPfx(rfc);
    }
}

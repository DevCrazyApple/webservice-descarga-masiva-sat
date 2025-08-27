package com.ws.download_service.domain.port.outbound;

import com.ws.download_service.domain.model.DownloadModel;
import com.ws.download_service.domain.model.PackageModel;

public interface DownloadRequestOut {
    void getDownload(PackageModel model) throws Exception;
    PackageModel getPackage(String idrequest);
}

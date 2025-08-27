package com.ws.download_service.domain.port.inbound;

import com.ws.download_service.domain.model.DownloadModel;
import com.ws.download_service.domain.model.PackageModel;

public interface DownloadRequestIn {
    void getDownload(PackageModel model) throws Exception;
    PackageModel getPackage(String idrequest);
}

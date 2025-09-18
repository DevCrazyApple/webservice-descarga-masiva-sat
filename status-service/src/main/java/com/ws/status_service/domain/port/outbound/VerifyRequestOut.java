package com.ws.status_service.domain.port.outbound;

import com.ws.status_service.domain.model.PackageModel;
import com.ws.status_service.domain.model.PfxModel;
import com.ws.status_service.domain.model.StatusModel;
import com.ws.status_service.domain.model.VerifyModel;

public interface VerifyRequestOut {
    VerifyModel getPackages(StatusModel statusModel) throws Exception;
    String getToken(String rfc);
    PfxModel getPfx(String rfc);
    PackageModel getPackage(String idrequest);
}

package com.ws.status_service.domain.port.outbound;

import com.ws.status_service.domain.model.StatusModel;

public interface VerifyRequestOut {
    String getPackages(StatusModel statusModel);
}

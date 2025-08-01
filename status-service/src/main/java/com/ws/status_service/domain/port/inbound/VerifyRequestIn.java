package com.ws.status_service.domain.port.inbound;

import com.ws.status_service.domain.model.StatusModel;

public interface VerifyRequestIn {
    String getPackages(StatusModel statusModel);
}

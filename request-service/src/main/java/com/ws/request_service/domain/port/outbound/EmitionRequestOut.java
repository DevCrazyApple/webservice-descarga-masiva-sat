package com.ws.request_service.domain.port.outbound;

import com.ws.request_service.domain.model.RequestModel;

public interface EmitionRequestOut {
    String requestDownload(RequestModel requestModel) throws Exception;
}

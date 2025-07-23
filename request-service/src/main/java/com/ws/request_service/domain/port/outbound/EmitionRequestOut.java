package com.ws.request_service.domain.port.outbound;

import com.ws.request_service.domain.model.RequestModel;

public interface EmitionRequestOut {
    RequestModel requestDownload(RequestModel requestModel) throws Exception;
}

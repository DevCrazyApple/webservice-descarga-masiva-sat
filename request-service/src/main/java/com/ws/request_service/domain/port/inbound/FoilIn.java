package com.ws.request_service.domain.port.inbound;

import com.ws.request_service.domain.model.FoilModel;

public interface FoilIn {
    String requestDownload(FoilModel foilModel) throws Exception;
}

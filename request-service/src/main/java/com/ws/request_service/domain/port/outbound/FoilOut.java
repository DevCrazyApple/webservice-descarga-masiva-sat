package com.ws.request_service.domain.port.outbound;

import com.ws.request_service.domain.model.FoilModel;

public interface FoilOut {
    String requestDownload(FoilModel foilModel) throws Exception;
}

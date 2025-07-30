package com.ws.request_service.domain.port.inbound;

import com.ws.request_service.application.command.RequestDownloadCommand;
import com.ws.request_service.domain.model.RequestModel;

public interface EmitionRequestIn {
    String requestDownload(RequestModel requestModel) throws Exception;
    RequestModel toModel(RequestDownloadCommand requestDownloadCommand);
}

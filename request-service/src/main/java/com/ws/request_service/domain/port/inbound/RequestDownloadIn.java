package com.ws.request_service.domain.port.inbound;

import com.ws.request_service.application.command.RequestDownloadCommand;
import com.ws.request_service.domain.model.RequestModel;

public interface RequestDownloadIn {
    RequestModel requestEmitionDownload(RequestDownloadCommand requestDownloadCommand);
}

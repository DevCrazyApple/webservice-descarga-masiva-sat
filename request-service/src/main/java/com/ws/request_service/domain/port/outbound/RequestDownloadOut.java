package com.ws.request_service.domain.port.outbound;

import com.ws.request_service.application.command.RequestDownloadCommand;
import com.ws.request_service.domain.model.RequestModel;

public interface RequestDownloadOut {
    RequestModel requestEmitionDownload(RequestDownloadCommand requestDownloadCommand);

}

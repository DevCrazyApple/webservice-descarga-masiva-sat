package com.ws.request_service.application.service;

import com.ws.request_service.application.command.RequestDownloadCommand;
import com.ws.request_service.domain.model.RequestModel;
import com.ws.request_service.domain.port.inbound.RequestDownloadIn;
import com.ws.request_service.domain.port.outbound.RequestDownloadOut;

public class RequestService implements RequestDownloadIn {

    private final RequestDownloadOut requestDownloadOut;

    public RequestService(RequestDownloadOut requestDownloadOut) {
        this.requestDownloadOut = requestDownloadOut;
    }

    @Override
    public RequestModel requestEmitionDownload(RequestDownloadCommand requestDownloadCommand) {
        return this.requestDownloadOut.requestEmitionDownload(requestDownloadCommand);
    }
}

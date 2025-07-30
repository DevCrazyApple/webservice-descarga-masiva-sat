package com.ws.request_service.infrastructure.repository.custom;

import com.ws.request_service.infrastructure.entity.RequestEntity;

public interface RequestCustomRepo {
    RequestEntity saveInfo(RequestEntity entity);
}

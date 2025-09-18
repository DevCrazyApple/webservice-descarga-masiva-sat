package com.ws.status_service.infrastructure.repository.custom;

import com.ws.status_service.infrastructure.entity.StatusEntity;

public interface StatusCustomRepo {
    StatusEntity saveOrUpdate(StatusEntity entity);
    StatusEntity findByIdRequest(String idrequest);
}

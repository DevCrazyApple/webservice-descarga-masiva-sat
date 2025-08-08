package com.ws.request_service.infrastructure.repository.custom;

import com.ws.request_service.infrastructure.entity.FoilEntity;

public interface FoilCustomRepo {
    FoilEntity saveInfo(FoilEntity foilEntity);
}

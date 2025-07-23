package com.ws.auth_service.infrastructure.repository.custom;

import com.ws.auth_service.infrastructure.entities.PfxEntity;

public interface PfxCustomRepo {
    PfxEntity findPfxByRfc(String rfc);
}

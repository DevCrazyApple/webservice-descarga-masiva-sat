package com.ws.auth_service.infrastructure.repository.custom;

import com.ws.auth_service.infrastructure.entities.AuthEntity;


public interface AuthCustomRepo {
    AuthEntity findByRfc(String rfc);
    AuthEntity saveOrUpdateByRfc(String rfc, String token, String cert, String key);
}

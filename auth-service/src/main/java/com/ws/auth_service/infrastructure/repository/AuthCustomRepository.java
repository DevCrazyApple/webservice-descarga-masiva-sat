package com.ws.auth_service.infrastructure.repository;

import com.ws.auth_service.infrastructure.entities.AuthEntity;

import java.util.List;
import java.util.Optional;

public interface AuthCustomRepository {
    AuthEntity findByRfc(String rfc);
    AuthEntity saveOrUpdateByRfc(String rfc, String token);
}

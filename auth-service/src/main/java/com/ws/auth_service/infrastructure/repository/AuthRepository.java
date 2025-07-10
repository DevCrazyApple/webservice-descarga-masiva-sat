package com.ws.auth_service.infrastructure.repository;

import com.ws.auth_service.infrastructure.entities.AuthEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends MongoRepository<AuthEntity, String>, AuthCustomRepository {
}

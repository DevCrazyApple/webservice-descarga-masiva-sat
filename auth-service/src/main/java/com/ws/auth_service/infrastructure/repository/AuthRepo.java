package com.ws.auth_service.infrastructure.repository;

import com.ws.auth_service.infrastructure.entities.AuthEntity;
import com.ws.auth_service.infrastructure.repository.custom.AuthCustomRepo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepo extends MongoRepository<AuthEntity, String>, AuthCustomRepo {
}

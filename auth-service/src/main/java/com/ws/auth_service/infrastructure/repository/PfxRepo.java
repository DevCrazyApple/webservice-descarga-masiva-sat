package com.ws.auth_service.infrastructure.repository;

import com.ws.auth_service.infrastructure.entities.PfxEntity;
import com.ws.auth_service.infrastructure.repository.custom.PfxCustomRepo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PfxRepo extends MongoRepository<PfxEntity, String>, PfxCustomRepo {
}

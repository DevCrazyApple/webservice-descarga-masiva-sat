package com.ws.status_service.infrastructure.repository;

import com.ws.status_service.infrastructure.entity.StatusEntity;
import com.ws.status_service.infrastructure.repository.custom.StatusCustomRepo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatusRepo extends MongoRepository<StatusEntity, String>, StatusCustomRepo {
}

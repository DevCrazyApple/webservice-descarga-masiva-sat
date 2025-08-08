package com.ws.request_service.infrastructure.repository;

import com.ws.request_service.infrastructure.entity.FoilEntity;
import com.ws.request_service.infrastructure.repository.custom.FoilCustomRepo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoilRepo extends MongoRepository<FoilEntity, String>, FoilCustomRepo {
}

package com.ws.request_service.infrastructure.repository;

import com.ws.request_service.infrastructure.entity.RequestEntity;
import com.ws.request_service.infrastructure.repository.custom.RequestCustomRepo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestRepo extends MongoRepository<RequestEntity, String>, RequestCustomRepo {
}

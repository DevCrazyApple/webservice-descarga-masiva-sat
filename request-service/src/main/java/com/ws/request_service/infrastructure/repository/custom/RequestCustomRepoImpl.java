package com.ws.request_service.infrastructure.repository.custom;

import com.ws.request_service.infrastructure.entity.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class RequestCustomRepoImpl implements RequestCustomRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public RequestEntity saveInfo(RequestEntity entity) {
        return mongoTemplate.save(entity);
    }
}

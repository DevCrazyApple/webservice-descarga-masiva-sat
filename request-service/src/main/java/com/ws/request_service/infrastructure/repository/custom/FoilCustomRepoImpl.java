package com.ws.request_service.infrastructure.repository.custom;

import com.ws.request_service.infrastructure.entity.FoilEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class FoilCustomRepoImpl implements FoilCustomRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public FoilEntity saveInfo(FoilEntity foilEntity) {
        return mongoTemplate.save(foilEntity);
    }
}

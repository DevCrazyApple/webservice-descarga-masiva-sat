package com.ws.auth_service.infrastructure.repository.custom;

import com.ws.auth_service.infrastructure.entities.PfxEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class PfxCustomRepoImpl implements PfxCustomRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public PfxEntity findPfxByRfc(String rfc) {
        Query query = new Query();
        query.addCriteria(Criteria.where("rfc").is(rfc));
        return mongoTemplate.findOne(query, PfxEntity.class);
    }
}

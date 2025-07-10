package com.ws.auth_service.infrastructure.repository;

import com.ws.auth_service.infrastructure.entities.AuthEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;
import java.util.Optional;

public class AuthCustomRepositoryImpl implements AuthCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public AuthEntity findByRfc(String rfc) {
        Query query = new Query();
        query.addCriteria(Criteria.where("rfc").is(rfc));
        // db.auth_service.findOne({"rfc": "UPS891122HV8"});
        // Aquí puedes agregar filtros, paginación, etc.
        return mongoTemplate.findOne(query, AuthEntity.class);

    }

    @Override
    public AuthEntity saveOrUpdateByRfc(String rfc, String token) {
        Query query = new Query( Criteria.where("rfc").is(rfc) );

        Update update = new Update()
            .set("token", token)
            .set("timestamp", LocalDateTime.now());

        return mongoTemplate.findAndModify(
            query,
            update,
            FindAndModifyOptions.options().upsert(true).returnNew(true),
            AuthEntity.class
        );
    }
}

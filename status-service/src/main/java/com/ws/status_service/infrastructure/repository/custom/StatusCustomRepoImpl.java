package com.ws.status_service.infrastructure.repository.custom;

import com.ws.status_service.infrastructure.entity.StatusEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class StatusCustomRepoImpl implements StatusCustomRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public StatusEntity saveOrUpdate(StatusEntity entity) {
        Query query = new Query( );
        query.addCriteria( Criteria.where("idRequest").is(entity.getIdRequest()) )
                .addCriteria( Criteria.where("rfcSolicitante").is(entity.getRfcSolicitante()) );

        Update update = new Update()
            .set("codeSolicitud", entity.getCodeSolicitud())
            .set("codeVerificar", entity.getCodeVerificar())
            .set("statusVerificar", entity.getStatusVerificar())
            .set("packagesIds", entity.getPackagesIds())
            .set("processed", entity.getProcessed());

        return mongoTemplate.findAndModify(
            query,
            update,
            FindAndModifyOptions.options().upsert(true).returnNew(true),
            StatusEntity.class
        );
    }

    @Override
    public StatusEntity findByIdRequest(String idrequest) {
        Query query = new Query( Criteria.where("idRequest").is(idrequest) );
        return mongoTemplate.findOne(query, StatusEntity.class);
    }
}

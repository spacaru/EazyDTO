package com.norberth.core.database;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseWrapper {

    private final Logger logger = Logger.getLogger(DatabaseWrapper.class.getSimpleName());
    private final EntityManager entityManager;

    public DatabaseWrapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List getObjectList(String sql, Class targetClass, SqlType sqlType) {
        Query q = null;
        switch (sqlType) {
            case JPQL:
                q = entityManager.createQuery(sql, targetClass);
                break;
            case NATIVE:
                q = entityManager.createNativeQuery(sql, targetClass);
//                    TODO : add support for parameters
                break;
            case NAMED:
                q = entityManager.createNamedQuery(sql, targetClass);
                break;
            default:
                logger.warning("Unknown sql type");

        }
        return q.getResultList();
    }
}

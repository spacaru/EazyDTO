package com.norberth.core.database;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class DatabaseWrapper {

    private EntityManager entityManager;

    public DatabaseWrapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List getObjectList(String sql, Class targetClass, SqlType sqlType) {
        Query q = null;
        switch (sqlType) {
            case NAMED:
                q = entityManager.createNamedQuery(sql, targetClass);
                break;
            case NATIVE:
                q = entityManager.createNativeQuery(sql, targetClass);
//                    TODO : add support for parameters
                break;
        }
        return q.getResultList();
    }
}

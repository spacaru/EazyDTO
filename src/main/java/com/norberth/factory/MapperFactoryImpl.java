package com.norberth.factory;

import com.norberth.core.MapperType;

import javax.persistence.EntityManager;
import java.util.ArrayList;

/**
 * Singleton provider factory class for generic converters of DTO (data transport object) classes
 *
 * @author Novanc Norberth
 * @version 1.0.0
 */
public class MapperFactoryImpl extends MapperFactory {


    private static MapperFactoryImpl instance = null;


    private MapperFactoryImpl() {
        mappers = new ArrayList<>();
    }

    /**
     * Get factory instance
     *
     * @return
     */
    private static MapperFactoryImpl getInstance() {
        if (instance == null) {
            instance = new MapperFactoryImpl();
        }
        return instance;
    }

    public static MapperFactoryImpl withPackageName(String packageName) {
        getInstance();
        instance.packageName = packageName;
        return instance;
    }

    public static MapperFactoryImpl withDebugEnabled(boolean isDebugEnabled) {
        getInstance();
        instance.isDebugEnabled = isDebugEnabled;
        return instance;
    }

    public static MapperFactoryImpl withMapperType(MapperType mapperType) {
        getInstance();
        instance.mapperType = mapperType;
        return instance;
    }

    public static MapperFactoryImpl withEntityManager(EntityManager entityManager) {
        getInstance();
        instance.entityManager = entityManager;
        return instance;
    }

    public static MapperFactoryImpl build() {
        return instance;
    }


}

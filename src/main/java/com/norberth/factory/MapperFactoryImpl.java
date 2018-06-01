package com.norberth.factory;

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
    public static MapperFactoryImpl getInstance() {
        if (instance == null) {
            instance = new MapperFactoryImpl();
        }
        return instance;
    }

    public static MapperFactoryImpl withPackageName(String packageName) {
        instance.packageName = packageName;
        return instance;
    }

//    todo : implement other setters in here


}

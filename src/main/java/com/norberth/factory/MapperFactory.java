package com.norberth.factory;

import com.norberth.exception.NoPackage;
import com.norberth.core.DTOMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Singleton provider factory class for generic converters of DTO (data transport object) classes
 *
 * @author Novanc Norberth
 * @version 1.0.0
 */
public class MapperFactory {

    private final Logger logger = Logger.getLogger(DTOMapper.class.getSimpleName());
    private List<DTOMapper> objectConverters;
    private static MapperFactory instance = null;
    private String packageName = null;
    private boolean debug = false;

    private MapperFactory() {
        objectConverters = new ArrayList<>();
    }

    public void setPackageName(String pckName) {
        packageName = pckName;
    }

    /**
     * Get factory instance
     *
     * @return
     */
    public static MapperFactory getInstance() {
        if (instance == null) {
            instance = new MapperFactory();
        }
        return instance;
    }

    /**
     * Get converter of DTO class
     *
     * @param target (Class) - target DTO class
     * @return - {@link DTOMapper} associated with the DTO class
     * <b>Note : if none exists a new one is created and returned </b>
     */
    public DTOMapper getConverter(Class target) {
        if (packageName == null) {
            if (isDebug())
                try {
                    throw new NoPackage("Package not set. Set a package to scan for. Package example : com.norberth.examples or set this factory as test package by calling setScanTestPackage(true) ");
                } catch (NoPackage noPackage) {
                    logger.severe(noPackage.getMessage());
                }
            return null;
        }


        DTOMapper converter = null;
        if (!objectConverters.contains(new DTOMapper(target, packageName, isDebug()))) {
            converter = new DTOMapper(target, packageName, isDebug());
            objectConverters.add(converter);
            if (isDebug())
                logger.info(" Created new converter for class " + target.getSimpleName());
            return converter;
        } else

        {
            for (DTOMapper objectConverter : objectConverters) {
                if (objectConverter.equals(new DTOMapper(target, packageName, isDebug()))) {
                    if (isDebug())
                        logger.info(" Found converter for " + target.getSimpleName() + " class " + objectConverter.getClass().getTypeName());
                    return objectConverter;
                }
            }
        }
        return null;
    }

    public boolean isDebug() {
        return debug;
    }

    /**
     * Method to enable logging for debugging purposes
     *
     * @param deb (boolean)- if set to true debug is enabled
     */
    public void setDebug(boolean deb) {
        debug = deb;
    }

    public String getPackageName() {
        return packageName;
    }
}

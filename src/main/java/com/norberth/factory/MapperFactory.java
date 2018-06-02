package com.norberth.factory;

import com.norberth.core.DTOMapper;
import com.norberth.core.Mapper;
import com.norberth.core.MapperType;
import com.norberth.exception.NoPackage;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

/**
 * Mapper interface for returning all types of Mappers
 *
 * @author Norberth Novanc
 * @version 1.0.0
 */
public abstract class MapperFactory {
    private final Logger logger = Logger.getLogger(MapperFactory.class.getSimpleName());
    protected String packageName;
    protected boolean isDebugEnabled;
    protected List<Mapper> mappers;
    //    default mapper type is dto
    protected MapperType mapperType = MapperType.DTOMapper;
    protected EntityManager entityManager;


    /**
     * Get converter of DTO class
     *
     * @param target (Class) - target DTO class
     * @return - {@link DTOMapper} associated with the DTO class
     * <b>Note : if none exists a new one is created and returned </b>
     */
    public Mapper getMapper(Class target) {
        if (packageName == null) {
            if (isDebugEnabled)
                try {
                    throw new NoPackage("Package not set. Set a package to scan for. Package example : com.norberth.examples or set this factory as test package by calling setScanTestPackage(true) ");
                } catch (NoPackage noPackage) {
                    logger.severe(noPackage.getMessage());
                }
            return null;
        }


        Mapper mapper = null;
        if (!mappers.contains(new DTOMapper(target, packageName, isDebugEnabled))) {
            switch (mapperType) {
                case DTOMapper:

                    mapper = new DTOMapper(target, packageName, isDebugEnabled);
                    mapper.setEm(entityManager);
                    mappers.add(mapper);
                    break;
            }
            if (isDebugEnabled)
                logger.info(" Created new converter for class " + target.getSimpleName());
            return mapper;
        } else {
            for (Mapper objectConverter : mappers) {
                switch (mapperType) {
                    case DTOMapper:
                        if (objectConverter.equals(new DTOMapper(target, packageName, isDebugEnabled))) {
                            if (isDebugEnabled)
                                logger.info(" Found converter for " + target.getSimpleName() + " class " + objectConverter.getClass().getTypeName());
                        }
                        return objectConverter;
                    default:
                        logger.warning("Unknown mapper type specified");

                }
            }
        }
        return null;
    }
}

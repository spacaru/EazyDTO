package com.norberth.factory;

import com.norberth.exception.NoPackage;
import com.norberth.service.Converter;
import com.norberth.service.GenericConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Singleton provider factory class for generic converters of DTO (data transport object) classes
 *
 * @author Novanc Norberth
 * @version 1.0.0
 */
public class GenericConverterFactory {

    private final Logger logger = Logger.getLogger(GenericConverter.class.getSimpleName());
    private static List<GenericConverter> genericConverters;
    private static GenericConverterFactory instance = null;
    private static String packageName = null;

    private static boolean debug = false;

    private GenericConverterFactory() {
        genericConverters = new ArrayList<>();
    }

    public static void setPackageName(String pckName) {
        packageName = pckName;
    }

    /**
     * Get factory instance
     *
     * @return
     */
    public static GenericConverterFactory getInstance() {
        if (instance == null) {
            instance = new GenericConverterFactory();
        }
        return instance;
    }

    /**
     * Get converter of DTO class
     *
     * @param target (Class) - target DTO class
     * @return - {@link GenericConverter} associated with the DTO class
     * <b>Note : if none exists a new one is created and returned </b>
     */
    public GenericConverter getConverter(Class target) {
        if (packageName == null) {
            if (isDebug())
                try {
                    throw new NoPackage("Package not set. Set a package to scan for. Package example : com.norberth.examples");
                } catch (NoPackage noPackage) {
                    logger.severe(noPackage.getMessage());
                }
            return null;
        }

        if (!genericConverters.contains(new GenericConverter(target, packageName))) {
            GenericConverter converter = new GenericConverter(target, packageName);
            genericConverters.add(converter);
            if (isDebug())
                logger.info(" Created new converter for class " + target.getSimpleName());
            return converter;
        } else {
            for (GenericConverter genericConverter : genericConverters) {
                if (genericConverter.equals(new GenericConverter(target, packageName))) {
                    if (isDebug())
                        logger.info(" Found converter for " + target.getSimpleName() + " class " + genericConverter.getClass().getTypeName());
                    return genericConverter;
                }
            }
        }
        return null;
    }

    public static boolean isDebug() {
        return debug;
    }

    /**
     * Method to enable logging for debugging purposes
     *
     * @param deb (boolean)- if set to true debug is enabled
     */
    public static void setDebug(boolean deb) {
        debug = deb;
    }
}

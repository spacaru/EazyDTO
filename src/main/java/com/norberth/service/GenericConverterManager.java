package com.norberth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Novanc Norberth
 * @version 1.0.0
 */
public class GenericConverterManager {

    private final Logger logger = Logger.getLogger(GenericConverter.class.getSimpleName());
    private static List<GenericConverter> genericConverters;
    private static GenericConverterManager instance = null;
    private static String packageName = null;

    private static boolean debug = false;

    private GenericConverterManager() {
        genericConverters = new ArrayList<>();
    }

    public static void setPackageName(String pckName) {
        packageName = pckName;
    }

    public static GenericConverterManager getInstance() {
        if (instance == null) {
            instance = new GenericConverterManager();
        }
        return instance;
    }

    public GenericConverter getConverter(Class target) {
        if (packageName == null) {
            if (isDebug())
                logger.warning("Package not set. Set a package to scan for. Package example : com.norberth.test.package");
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
                        logger.info(" Found converter for " + target.getSimpleName() + " class " + genericConverter.getType());
                    return genericConverter;
                }
            }
        }
        return null;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean deb) {
        debug = deb;
    }
}

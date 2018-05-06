package com.norberth.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NoPackage extends Exception {

    private final Logger logger = Logger.getLogger(NoPackage.class.getSimpleName());

    public NoPackage(String message) {
        super(message);
        logger.log(Level.INFO, message);
    }
}

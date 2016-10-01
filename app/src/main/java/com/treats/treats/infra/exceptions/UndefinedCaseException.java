package com.treats.treats.infra.exceptions;

/**
 * Created by david.uzan on 4/19/2016.
 */
public class UndefinedCaseException extends IllegalArgumentException {
    public UndefinedCaseException(String enumConstant, Class enumClass) {
        super("Undefined case for " + enumConstant + " enum constant (" + enumClass.getSimpleName() + ").");
    }
}

package com.gmail.maxsmv1998.eurodiffusion.util.validator;


public interface Validator<T> {
    /**
     * Check is input message is valid
     *
     * @param obj that need to check
     * @return {@code true} if input is valid
     * {@code false} if input is not valid
     */
    boolean isValid(T obj);

    /**
     * Check is input message is invalid
     *
     * @param obj that need to check
     * @return {@code true} if input is valid
     * {@code false} if input is not valid
     */
    default boolean isInvalid(T obj) {
        return !isValid(obj);
    }
}
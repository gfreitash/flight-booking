package com.gfreitash.flight_booking.services.validations;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a validator as a validator for a specific class
 * It is a complimentary annotation of a class that implements SpecificationValidator
 * The main use of this annotation is to be able to inject a validator for a specific class
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Validates {
    Class<?> value();
}

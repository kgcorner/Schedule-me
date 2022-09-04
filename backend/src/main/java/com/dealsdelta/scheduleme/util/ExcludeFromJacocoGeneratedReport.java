package com.dealsdelta.scheduleme.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description : This Annotation is created to exclude any method from jacoco code coverage
 * Author: kumar
 * Created on : 04/09/22
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExcludeFromJacocoGeneratedReport {
}

package com.github.yatatsu.fusuma.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Annotation for class that you'd like to isolate.
 * Found calling for the class with this annotation, regard as violation and warn it.
 *
 * If you want to use in specific condition, you can choose from below,
 * 1. Set true to {@link Fusuma#openIf()}, then the warning will be suppressed.
 * 2. Annotate method with {@link OpenFusuma}. You can specify target class.
 */
@Retention(CLASS)
@Target(TYPE)
public @interface Fusuma {

  /**
   * Optional condition for suppressing.
   *
   * @return Return true and the warning will be suppressed. The default value is `false`.
   */
  boolean openIf() default false;
}

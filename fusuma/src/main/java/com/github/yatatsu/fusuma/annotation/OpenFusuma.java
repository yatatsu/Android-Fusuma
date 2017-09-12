package com.github.yatatsu.fusuma.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Annotation for type or method, that you'd like to avoid lint with {@link Fusuma}.
 * You can suppress  specify type using {@link OpenFusuma#targetType()}
 *
 */
@Retention(CLASS)
@Target({ TYPE, METHOD })
public @interface OpenFusuma {

  /**
   * The target type for suppressing.
   *
   * @return class name you'd like to skip the lint by {@link Fusuma}.
   */
  Class<?> targetType() default Object.class;
}

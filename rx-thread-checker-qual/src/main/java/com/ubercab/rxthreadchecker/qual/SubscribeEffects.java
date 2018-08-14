package com.ubercab.rxthreadchecker.qual;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a method to specify the relationship between its receiver's thread type and its
 * arguments' effect types. The effects of the arguments at positions {@param value} must be
 * compatible with the receiver's thread.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SubscribeEffects {

  // The indices of parameters whose effect is applied on the method receiver's thread.
  int[] value();
}

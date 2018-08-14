package com.ubercab.rxthreadchecker.qual;

import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A stream (i.e. an RxJava Observable) annotated @CompThread will always execute subscribed
 * callbacks on a background computation thread.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@SubtypeOf({AnyThread.class})
public @interface CompThread {}

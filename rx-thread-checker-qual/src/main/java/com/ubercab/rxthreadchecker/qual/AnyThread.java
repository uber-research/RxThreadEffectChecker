package com.ubercab.rxthreadchecker.qual;

import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A stream (i.e. an RxJava Observable) annotated @AnyThread can execute subscribed callbacks on any
 * thread. AnyThread is the top of the thread-type lattice, meaning it's a conservative upper-bound
 * on the thread-pool of all streams, and is used when a more precise bound can't be determined.
 */
@DefaultQualifierInHierarchy
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@SubtypeOf({})
public @interface AnyThread {}

package com.ubercab.rxthreadchecker.qual;

import org.checkerframework.framework.qual.ImplicitFor;
import org.checkerframework.framework.qual.LiteralKind;
import org.checkerframework.framework.qual.SubtypeOf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A stream (i.e. an RxJava Observable) annotated @BottomThread can not execute subscribed callbacks
 * on any thread. BottomThread is the bottom of the thread-type lattice - which must exist for the
 * type system of the checker framework to behave properly, such that all pairs of thread-types have
 * a unique greatest lower bound, or meet -- but should never annotate a stream in practice.
 */
@ImplicitFor(literals = {LiteralKind.NULL})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@SubtypeOf({UIThread.class, CompThread.class})
public @interface BottomThread {}

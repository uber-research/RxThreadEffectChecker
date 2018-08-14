package com.ubercab.rxthreadchecker;

import org.checkerframework.checker.guieffect.GuiEffectChecker;
import org.checkerframework.checker.guieffect.GuiEffectTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;
import org.checkerframework.framework.source.SupportedLintOptions;

import java.util.LinkedHashSet;
import java.util.Properties;

/**
 * Compiler interface for Rx Thread Checker: handles options, flags, etc., and instantiates other
 * analysis components
 */
@SupportedLintOptions({"debug"})
public class RxThreadChecker extends BaseTypeChecker {
  @Override
  protected BaseTypeVisitor<?> createSourceVisitor() {
    GuiEffectTypeFactory guiTypeFactory = this.getTypeFactoryOfSubchecker(GuiEffectChecker.class);
    return new RxThreadVisitor(this, guiTypeFactory);
  }

  @Override
  protected LinkedHashSet<Class<? extends BaseTypeChecker>> getImmediateSubcheckerClasses() {
    LinkedHashSet<Class<? extends BaseTypeChecker>> res = new LinkedHashSet<>(1);
    res.add(GuiEffectChecker.class);
    return res;
  }

  @Override
  public Properties getMessages() {
    Properties messages = super.getMessages();
    messages.put(
        "rxjava.thread.violation",
        "Subscribing a callback with %s effect to a stream observed on %s, "
            + "but %s callbacks can only be subscribed to streams observed on %s.");
    messages.put(
        "rxjava.stub.missing",
        "Missing annotated RxChecker stub for method %s, "
            + "which is part of an rx stream type. This will often cause spurious checker errors.");
    return messages;
  }
}

/*
 *  Copyright (c) 2017-2018 Uber Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

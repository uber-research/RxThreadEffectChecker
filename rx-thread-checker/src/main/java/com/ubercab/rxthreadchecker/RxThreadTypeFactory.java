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

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;
import org.checkerframework.checker.guieffect.Effect;
import org.checkerframework.checker.guieffect.GuiEffectTypeFactory;
import org.checkerframework.checker.guieffect.qual.SafeEffect;
import org.checkerframework.checker.guieffect.qual.UI;
import org.checkerframework.checker.guieffect.qual.UIEffect;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.source.Result;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.javacutil.AnnotationUtils;
import org.checkerframework.javacutil.TreeUtils;
import org.checkerframework.javacutil.TypesUtils;

import com.ubercab.rxthreadchecker.qual.AnyThread;
import com.ubercab.rxthreadchecker.qual.UIThread;
import com.ubercab.rxthreadchecker.qual.CompThread;
import com.ubercab.rxthreadchecker.qual.BottomThread;
import com.ubercab.rxthreadchecker.qual.PolyThread;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Computes type annotations for syntactic elements; in particular, inherited/derived type
 * annotations.
 */
public class RxThreadTypeFactory extends BaseAnnotatedTypeFactory {
  protected final boolean debug;
  protected final GuiEffectTypeFactory guiTypeFactory;

  public RxThreadTypeFactory(BaseTypeChecker checker, GuiEffectTypeFactory guiTypeFactory) {
    super(checker, false);
    this.guiTypeFactory = guiTypeFactory;
    this.debug = checker.getLintOption("debug", false);
    String fff = "fefsdgasefasdf";

    this.postInit();
  }

  @Override
  protected Set<Class<? extends Annotation>> createSupportedTypeQualifiers() {
    return new LinkedHashSet<>(
        Arrays.asList(
            AnyThread.class,
            UIThread.class,
            CompThread.class,
            BottomThread.class,
            PolyThread.class));
  }

  /**
   * Check whether a callback with effect {@param effect} can legally be subscribed to observable
   * {@param rcvr}
   *
   * <p>This method is a no-op when the subscription is legal, and reports a typechecking error
   * otherwise
   */
  public void checkEffect(MethodInvocationTree invoke, Effect effect) {
    Tree rcvr = TreeUtils.getReceiverTree(invoke);

    // Gets the thread type of the receiver; there is always exactly one such annotation.
    AnnotationMirror threadAnnotation =
        this.getQualifierHierarchy()
            .findAnnotationInHierarchy(
                getAnnotatedType(rcvr).getAnnotations(),
                this.getQualifierHierarchy().getTopAnnotations().iterator().next());

    if (threadAnnotation == null) {
      ExecutableElement methodElt = TreeUtils.elementFromUse(invoke);
      checker.report(Result.failure("rxjava.stub.missing", methodElt), invoke);
    }

    if (effect.isUI() && !AnnotationUtils.areSameByClass(threadAnnotation, UIThread.class)) {
      checker.report(
          Result.failure(
              "rxjava.thread.violation",
              effect,
              threadAnnotation,
              effect,
              UIThread.class.getSimpleName()),
          invoke);
    }
    // else if(effect.isComp() && !AnnotationUtils.areSameByClass(threadAnnotation, UIThread.class))
    // implement computation effects if needed?  analogous to UI effects.
  }

  /**
   * Compute the set of effects incurred on the stream receiver of {@param method} when it is called
   * with actual parameters {@param actualParams}
   */
  public Set<Effect> methodEffects(
      ExecutableElement method, List<? extends ExpressionTree> actualParams) {

    ArrayList<Integer> indices = new ArrayList<Integer>();

    for (AnnotationMirror am : getDeclAnnotations(method)) {
      if (TypesUtils.isDeclaredOfName(
          am.getAnnotationType(), "com.ubercab.rxthreadchecker.qual.SubscribeEffects")) {

        // This horribly complex string of calls is required to get the integer values of the
        // SubscribeEffects
        // annotation out of the various internal compiler data structures that wrap them.
        // See javax.lang.model.element.AnnotationValue for more detail
        // Silence compiler warning about this cast
        @SuppressWarnings("unchecked")
        List<AnnotationValue> annotationValues =
            (List<AnnotationValue>)
                am.getElementValues().entrySet().iterator().next().getValue().getValue();

        for (AnnotationValue index : annotationValues) {
          indices.add((Integer) index.getValue());
        }
      }
    }

    Set<Effect> result = new LinkedHashSet<>();
    for (Integer argIdx : indices) {
      result.add(exprEffect(actualParams.get(argIdx)));
    }
    return result;
  }

  /** Get the effect type of an expression {@param expr}. */
  private Effect exprEffect(ExpressionTree expr) {
    AnnotatedTypeMirror exprAnnotation = guiTypeFactory.getAnnotatedType(expr);
    if (exprAnnotation.hasAnnotation(UI.class)) {
      return new Effect(UIEffect.class);
    } else {
      return new Effect(SafeEffect.class);
    }
  }
}

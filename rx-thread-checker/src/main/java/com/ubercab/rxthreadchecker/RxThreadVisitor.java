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

import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;
import org.checkerframework.checker.guieffect.Effect;
import org.checkerframework.checker.guieffect.GuiEffectTypeFactory;
import org.checkerframework.common.basetype.BaseTypeVisitor;
import org.checkerframework.javacutil.TreeUtils;
import java.util.Set;

/**
 * Traverses an AST, enforcing standard typing rules (inherited from BaseTypeVisitor) as well as
 * custom ones for this type system (those visit methods overriden here)
 */
public class RxThreadVisitor extends BaseTypeVisitor<RxThreadTypeFactory> {
  protected final boolean debug;

  public RxThreadVisitor(RxThreadChecker c, GuiEffectTypeFactory getf) {
    super(c, new RxThreadTypeFactory(c, getf));
    debug = c.getLintOption("debug", false);
  }

  @Override
  public Void visitMethodInvocation(MethodInvocationTree node, Void p) {
    Tree rcvrTree = TreeUtils.getReceiverTree(node);
    if (rcvrTree != null) {
      // Enforce invariant that @UIEffect operations are only subscribed to @UIThread observables
      Set<Effect> methodEffects =
          atypeFactory.methodEffects(TreeUtils.elementFromUse(node), node.getArguments());
      for (Effect e : methodEffects) {
        atypeFactory.checkEffect(node, e);
      }

      // Forbid redundant observeOn calls?
    }
    return super.visitMethodInvocation(node, p);
  }
}

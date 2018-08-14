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

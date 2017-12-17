package com.rx.errorprone;

import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.matchers.Matchers;
import com.google.errorprone.util.ASTHelpers;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.SimpleTreeVisitor;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

// This is an incomplete check.
public class SubscriptionInConstructorCheck extends BugChecker implements BugChecker.MethodTreeMatcher {

  private static final Matcher<ExpressionTree> ON_SUBSCRIBE = Matchers.anyOf(
      Matchers.instanceMethod().onExactClass(Observable.class.getName()).named("subscribe"),
      Matchers.instanceMethod().onExactClass(Single.class.getName()).named("subscribe"),
      Matchers.instanceMethod().onExactClass(Completable.class.getName()).named("subscribe"),
      Matchers.instanceMethod().onExactClass(Maybe.class.getName()).named("subscribe"),
      Matchers.instanceMethod().onExactClass(Flowable.class.getName()).named("subscribe"));

  public static Matcher<MethodTree> methodIsConstructor() {
    return new Matcher<MethodTree>() {
      @Override
      public boolean matches(MethodTree methodTree, VisitorState state) {
        return ASTHelpers.getSymbol(methodTree).isConstructor();
      }
    };
  }

  @Override public Description matchMethod(MethodTree tree, VisitorState state) {
    if (methodIsConstructor().matches(tree, state)) {
      return tree.accept(new MethodVisitorImpl(state), null);
    }
    return Description.NO_MATCH;
  }

  private final class MethodVisitorImpl extends SimpleTreeVisitor<Description, Void> {

    private VisitorState visitorState;

    MethodVisitorImpl(VisitorState visitorState) {
      this.visitorState = visitorState;
    }

    @Override public Description visitMethodInvocation(MethodInvocationTree node, Void aVoid) {
      if (ON_SUBSCRIBE.matches(node, visitorState)) {
        return describeMatch(node);
      }
      return Description.NO_MATCH;
    }
  }

  //@Override
  //public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
  //  System.out.println("coming here");
  //  if (ON_SUBSCRIBE.matches(tree, state)) {
  //    System.out.println("coming here 2");
  //    System.out.println(ASTHelpers.getSymbol(tree).getEnclosingElement().isConstructor());
  //    Symbol enclosingElement = ASTHelpers.getSymbol(tree);
  //    while (enclosingElement != null) {
  //      System.out.println("coming here 3");
  //      System.out.println(enclosingElement);
  //      if (enclosingElement.isConstructor()) {
  //        System.out.println("coming here 5");
  //        return describeMatch(tree);
  //      }
  //      enclosingElement = enclosingElement.getEnclosingElement();
  //    }
  //  }
  //  System.out.println("coming here 4");
  //  return Description.NO_MATCH;
  //}
}

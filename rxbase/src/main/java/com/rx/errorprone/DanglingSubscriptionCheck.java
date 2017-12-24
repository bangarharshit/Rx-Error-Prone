package com.rx.errorprone;

import static com.google.errorprone.BugPattern.Category.JDK;
import static com.google.errorprone.BugPattern.SeverityLevel.WARNING;
import static com.google.errorprone.util.ASTHelpers.hasAnnotation;

import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.bugpatterns.AbstractReturnValueIgnored;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.matchers.Matchers;
import com.google.errorprone.util.ASTHelpers;
import com.rx.errorprone.utils.MatcherUtils;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import io.reactivex.disposables.Disposable;
import java.util.Objects;

/** @author harshit.bangar@gmail.com (Harshit Bangar) */
@AutoService(BugChecker.class)
@BugPattern(
  name = "DanglingSubscriptionCheck",
  summary = "Subscription should be assigned to a disposable.",
  explanation =
      "Observable's subscription should be assigned to a disposable for cleanup, otherwise it may lead to a leak",
  category = JDK,
  severity = WARNING
)
public class DanglingSubscriptionCheck extends AbstractReturnValueIgnored {

  private static final Matcher<ExpressionTree> ON_SUBSCRIBE = MatcherUtils.subscribeWithForRx2();

  // Check for return type Disposable.
  // It is not able to detect correct type in subscribeWith so ON_SUBSCRIBE matcher is combined.
  // public final <E extends Observer<? super T>> E subscribeWith(E observer)
  private static final Matcher<ExpressionTree> MATCHER =
      Matchers.anyOf(
          ON_SUBSCRIBE,
          new Matcher<ExpressionTree>() {
            @Override
            public boolean matches(ExpressionTree tree, VisitorState state) {
              Type disposableType =
                  Objects.requireNonNull(state.getTypeFromString(Disposable.class.getName()));
              Symbol untypedSymbol = ASTHelpers.getSymbol(tree);
              if (!(untypedSymbol instanceof Symbol.MethodSymbol)) {
                return false;
              }
              Symbol.MethodSymbol sym = (Symbol.MethodSymbol) untypedSymbol;
              if (hasAnnotation(sym, CanIgnoreReturnValue.class, state)) {
                return false;
              }
              Type returnType = sym.getReturnType();
              return ASTHelpers.isSubtype(
                  ASTHelpers.getUpperBound(returnType, state.getTypes()), disposableType, state);
            }
          });

  @Override
  public Matcher<? super MethodInvocationTree> specializedMatcher() {
    return MATCHER;
  }

  @Override
  public String linkUrl() {
    return "https://bitbucket.org/littlerobots/rxlint";
  }
}

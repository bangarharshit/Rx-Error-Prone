package com.rx.errorprone.rx1;

import static com.google.errorprone.BugPattern.Category.JDK;
import static com.google.errorprone.BugPattern.SeverityLevel.WARNING;

import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.matchers.Matchers;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import rx.Observable;

/** @author harshit.bangar@gmail.com (Harshit Bangar) */
@BugPattern(
  name = "OnCreateCheck",
  summary = "Prefer backpressure compliant operators to onCreate without backpressure.",
  explanation =
      "Don't call observable's onCreate without backpressure. Use backpressure compliant operators instead. "
          + "It has been deprecated in rx 1.3 but the check is for older rx versions",
  category = JDK,
  severity = WARNING
)
public class OnCreateCheck extends BugChecker implements BugChecker.MethodInvocationTreeMatcher {

  private static final Matcher<ExpressionTree> ON_CREATE =
      Matchers.anyOf(
          Matchers.staticMethod()
              .onClass(Observable.class.getName())
              .withSignature("<T>create(rx.Observable.OnSubscribe<T>)"),
          Matchers.staticMethod().onClass(Observable.class.getName()).named("unsafeCreate"));

  @Override
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
    if (ON_CREATE.matches(tree, state)) {
      return describeMatch(tree);
    } else {
      return Description.NO_MATCH;
    }
  }

  @Override
  public String linkUrl() {
    return "https://speakerdeck.com/dlew/common-rxjava-mistakes";
  }
}

package com.rx.errorprone;

import static com.google.errorprone.BugPattern.Category.JDK;
import static com.google.errorprone.BugPattern.SeverityLevel.WARNING;

import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.rx.errorprone.utils.MatcherUtils;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;

/** @author harshit.bangar@gmail.com (Harshit Bangar) */
@AutoService(BugChecker.class)
@BugPattern(
  name = "SubscriptionInConstructorCheck",
  summary = "Subscription should not be done in constructor.",
  explanation =
      "Constructor is not a lifecycle method and finalizer is not guaranteed to be called. "
          + "Also, starting a new thread from constructor may lead to this leak. ",
  category = JDK,
  severity = WARNING
)
public class SubscriptionInConstructorCheck extends BugChecker
    implements BugChecker.MethodInvocationTreeMatcher {

  private static final Matcher<ExpressionTree> ON_SUBSCRIBE = MatcherUtils.subscribeForRx2();

  @Override
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
    if (ON_SUBSCRIBE.matches(tree, state)) {
      if (MatcherUtils.isEnclosingMethodConstructor(state)) {
        return describeMatch(tree);
      }
    }
    return Description.NO_MATCH;
  }

  @Override
  public String linkUrl() {
    return "https://medium.com/@harshitbangar/pro-rxjava-dont-subscribe-observable-in-the-constructor-615c941149f2";
  }
}

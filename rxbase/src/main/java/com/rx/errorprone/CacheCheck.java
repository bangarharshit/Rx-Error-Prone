package com.rx.errorprone;

import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.matchers.Matchers;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

import static com.google.errorprone.BugPattern.Category.JDK;
import static com.google.errorprone.BugPattern.SeverityLevel.WARNING;

/** @author harshit.bangar@gmail.com (Harshit Bangar) */
@BugPattern(
    name = "CacheCheck",
    summary = "Don't use cache. Use replay() instead",
    category = JDK,
    severity = WARNING
)
public class CacheCheck extends BugChecker implements BugChecker.MethodInvocationTreeMatcher {

  public static final Matcher<ExpressionTree> CACHE = Matchers.anyOf(
      Matchers.instanceMethod().onExactClass(rx.Observable.class.getName()).named("cache"),
      Matchers.instanceMethod().onExactClass(Observable.class.getName()).named("cache"),
      Matchers.instanceMethod().onExactClass(Single.class.getName()).named("cache"),
      Matchers.instanceMethod().onExactClass(rx.Single.class.getName()).named("cache"),
      Matchers.instanceMethod().onExactClass(Completable.class.getName()).named("cache"),
      Matchers.instanceMethod().onExactClass(Maybe.class.getName()).named("cache"),
      Matchers.instanceMethod().onExactClass(Flowable.class.getName()).named("cache"));

  @Override
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
    if (CACHE.matches(tree, state)) {
      return describeMatch(tree);
    } else {
      return Description.NO_MATCH;
    }
  }

  @Override public String linkUrl() {
    return "https://speakerdeck.com/dlew/common-rxjava-mistakes";
  }
}

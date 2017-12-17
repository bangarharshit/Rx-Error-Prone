package com.rx.errorprone;

import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.Description;
import com.sun.source.tree.MethodInvocationTree;

import static com.google.errorprone.BugPattern.Category.JDK;
import static com.google.errorprone.BugPattern.SeverityLevel.WARNING;

/** @author harshit.bangar@gmail.com (Harshit Bangar) */
@BugPattern(
    name = "SubscribeOnErrorMissingCheck",
    summary = "Subscriber is missing onError handling",
    explanation =
        "Every observable can report errors. "
            + "Not implementing onError will throw an exception at runtime "
            + "which can be hard to debug when the error is thrown on a Scheduler that is not the invoking thread."
            + "It can also be used for debugging error ",
    category = JDK,
    severity = WARNING
)
public class SubscribeOnErrorMissingCheck extends BugChecker implements
    BugChecker.MethodInvocationTreeMatcher {

  @Override
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
    tree.getArguments();
    return null;
  }

  @Override public String linkUrl() {
    return "https://speakerdeck.com/dlew/common-rxjava-mistakes";
  }
}

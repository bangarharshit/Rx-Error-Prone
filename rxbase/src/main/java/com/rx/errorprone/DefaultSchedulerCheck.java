package com.rx.errorprone;

import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.Description;
import com.sun.source.tree.AnnotationTree;

import static com.google.errorprone.BugPattern.Category.JDK;
import static com.google.errorprone.BugPattern.SeverityLevel.WARNING;

/** @author harshit.bangar@gmail.com (Harshit Bangar) */
@BugPattern(
    name = "DanglingSubscriptionCheck",
    summary = "Using a default scheduler",
    explanation = "Some operators such as interval use their own scheduler which can lead to unexpected threading issues. "
        + "Prefer to use an overload of the operator that takes a Scheduler as a parameter to prevent these issues.",
    category = JDK,
    severity = WARNING
)
public class DefaultSchedulerCheck extends BugChecker implements BugChecker.AnnotationTreeMatcher {

  @Override public Description matchAnnotation(AnnotationTree tree, VisitorState state) {
    return null;
  }

  @Override public String linkUrl() {
    return "https://bitbucket.org/littlerobots/rxlint/";
  }
}

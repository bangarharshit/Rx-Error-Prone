package com.rx.errorprone;

import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.annotations.CompatibleWith;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.matchers.Matchers;
import com.sun.source.tree.AnnotationTree;
import io.reactivex.annotations.SchedulerSupport;

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

  private static final Matcher<AnnotationTree> SCHEDULER_SUPPORT =
      Matchers.isType(SchedulerSupport.class.getCanonicalName());

  @Override public Description matchAnnotation(AnnotationTree tree, VisitorState state) {
    System.out.println("coming here");
    if (SCHEDULER_SUPPORT.matches(tree, state)) {
      System.out.println(tree.getArguments());
      return describeMatch(tree);
    }
    return Description.NO_MATCH;
  }

  @Override public String linkUrl() {
    return "https://bitbucket.org/littlerobots/rxlint/";
  }
}

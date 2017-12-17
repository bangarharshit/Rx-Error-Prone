package com.rx.errorprone;

import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.util.ASTHelpers;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.tools.javac.code.Symbol;
import io.reactivex.annotations.SchedulerSupport;

import static com.google.errorprone.BugPattern.Category.JDK;
import static com.google.errorprone.BugPattern.SeverityLevel.WARNING;

/** @author harshit.bangar@gmail.com (Harshit Bangar) */
@BugPattern(
    name = "DefaultSchedulerCheck",
    summary = "Using a default scheduler",
    explanation = "Some operators such as interval use their own scheduler which can lead to unexpected threading issues. "
        + "Prefer to use an overload of the operator that takes a Scheduler as a parameter to prevent these issues.",
    category = JDK,
    severity = WARNING
)
public class DefaultSchedulerCheck extends BugChecker implements BugChecker.MethodInvocationTreeMatcher {

  @Override
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
    Symbol.MethodSymbol methodSymbol = ASTHelpers.getSymbol(tree);
    if (ASTHelpers.hasAnnotation(methodSymbol, SchedulerSupport.class.getName(), state)) {
      SchedulerSupport schedulerSupport = ASTHelpers.getAnnotation(methodSymbol, SchedulerSupport.class);
      String value = schedulerSupport.value();
      if (value.equals(SchedulerSupport.NONE) || value.equals(SchedulerSupport.CUSTOM)) {
        return Description.NO_MATCH;
      } else {
        return describeMatch(tree);
      }
    }
    return Description.NO_MATCH;
  }

  @Override public String linkUrl() {
    return "https://bitbucket.org/littlerobots/rxlint/";
  }
}

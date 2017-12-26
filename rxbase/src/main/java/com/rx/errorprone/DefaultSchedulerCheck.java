package com.rx.errorprone;

import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.AnnotationMatcher;
import com.google.errorprone.matchers.AnnotationMatcherUtils;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.matchers.MultiMatcher;
import com.google.errorprone.util.ASTHelpers;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.tools.javac.code.Symbol;
import io.reactivex.annotations.SchedulerSupport;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.checkerframework.javacutil.AnnotationUtils;

import static com.google.errorprone.BugPattern.Category.JDK;
import static com.google.errorprone.BugPattern.SeverityLevel.WARNING;
import static com.google.errorprone.matchers.ChildMultiMatcher.MatchType.AT_LEAST_ONE;
import static com.google.errorprone.matchers.Matchers.annotations;
import static com.google.errorprone.matchers.Matchers.isType;

/** @author harshit.bangar@gmail.com (Harshit Bangar) */
@SuppressWarnings("unchecked") @AutoService(BugChecker.class)
@BugPattern(
  name = "DefaultSchedulerCheck",
  summary = "Using a default scheduler",
  explanation =
      "Some operators such as interval use their own scheduler which can lead to unexpected threading issues. "
          + "Prefer to use an overload of the operator that takes a Scheduler as a parameter to prevent these issues.",
  category = JDK,
  severity = WARNING
)
public class DefaultSchedulerCheck extends BugChecker
    implements BugChecker.MethodInvocationTreeMatcher {

  private static final String TARGET_ANNOTATION = "io.reactivex.annotations.SchedulerSupport";

  private static final MultiMatcher<MethodInvocationTree, AnnotationTree> HAS_TARGET_ANNOTATION =
      annotations(AT_LEAST_ONE, isType(TARGET_ANNOTATION));

  @Override
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
    Symbol.MethodSymbol methodSymbol = ASTHelpers.getSymbol(tree);
    if (ASTHelpers.hasAnnotation(methodSymbol, SchedulerSupport.class.getName(), state)) {
      SchedulerSupport schedulerSupport =
          ASTHelpers.getAnnotation(methodSymbol, SchedulerSupport.class);
      String value = schedulerSupport.value();
      if (value.equals(SchedulerSupport.NONE) || value.equals(SchedulerSupport.CUSTOM)) {
        return Description.NO_MATCH;
      } else {
        return describeMatch(tree);
      }
    }
    return Description.NO_MATCH;
  }

  @Override
  public String linkUrl() {
    return "https://bitbucket.org/littlerobots/rxlint/";
  }
}

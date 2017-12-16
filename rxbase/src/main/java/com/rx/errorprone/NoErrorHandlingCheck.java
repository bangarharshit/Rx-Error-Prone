package com.rx.errorprone;

import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.Description;
import com.sun.source.tree.MethodInvocationTree;

public class NoErrorHandlingCheck extends BugChecker implements
    BugChecker.MethodInvocationTreeMatcher {

  @Override
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
    tree.getArguments();
    return null;
  }
}

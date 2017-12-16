package com.rx.errorprone;

import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.Description;
import com.sun.source.tree.AnnotationTree;

public class DefaultSchedulerCheck extends BugChecker implements BugChecker.AnnotationTreeMatcher {
  @Override public Description matchAnnotation(AnnotationTree tree, VisitorState state) {
    tree.get
    return null;
  }
}

package com.rx.errorprone.utils;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.VisitorState;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.matchers.Matchers;
import com.google.errorprone.matchers.method.MethodMatchers;
import com.google.errorprone.util.ASTHelpers;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodTree;
import java.util.ArrayList;
import java.util.List;

public class MatcherUtils {
  private MatcherUtils() {
    throw new AssertionError("Instance not possible");
  }

  private static final String CACHE = "cache";
  private static final ImmutableList<String> RX2_CLASSES =
      ImmutableList.of(
          "io.reactivex.Observable",
          "io.reactivex.Single",
          "io.reactivex.Completable",
          "io.reactivex.Maybe",
          "io.reactivex.Flowable");

  private static final ImmutableList<String> RX_CLASSES =
      ImmutableList.of(
          "rx.Observable",
          "rx.Single",
          "rx.Completable",
          "io.reactivex.Observable",
          "io.reactivex.Single",
          "io.reactivex.Completable",
          "io.reactivex.Maybe",
          "io.reactivex.Flowable");

  private static final String SUBSCRIBE = "subscribe";
  private static final String SUBSCRIBE_WITH = "subscribeWith";

  private static Matcher<ExpressionTree> generateMatcherForSameMethodAndMultipleClasses(
      List<String> classes, String method) {
    List<MethodMatchers.MethodNameMatcher> matchers = new ArrayList<>();
    for (String aClass : classes) {
      matchers.add(Matchers.instanceMethod().onExactClass(aClass).named(method));
    }
    return Matchers.anyOf(matchers);
  }

  public static Matcher<ExpressionTree> subscribeWithForRx2() {
    return generateMatcherForSameMethodAndMultipleClasses(RX2_CLASSES, SUBSCRIBE_WITH);
  }

  public static Matcher<ExpressionTree> subscribeForRx2() {
    return generateMatcherForSameMethodAndMultipleClasses(RX2_CLASSES, SUBSCRIBE);
  }

  public static Matcher<ExpressionTree> cache() {
    return generateMatcherForSameMethodAndMultipleClasses(RX_CLASSES, CACHE);
  }

  public static boolean isEnclosingMethodConstructor(VisitorState state) {
    MethodTree methodTree = ASTHelpers.findEnclosingNode(state.getPath(), MethodTree.class);
    return methodTree != null && Matchers.methodIsConstructor().matches(methodTree, state);
  }
}

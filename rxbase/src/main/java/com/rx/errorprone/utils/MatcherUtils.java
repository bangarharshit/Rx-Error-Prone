package com.rx.errorprone.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.errorprone.VisitorState;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.matchers.Matchers;
import com.google.errorprone.matchers.method.MethodMatchers;
import com.google.errorprone.util.ASTHelpers;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodTree;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;

public class MatcherUtils {
  private MatcherUtils() {
    throw new AssertionError("Instance not possible");
  }

  public static final String  CACHE = "cache";
  public static final ImmutableList<Class> RX2_CLASSES = ImmutableList.of(
      Observable.class,
      Single.class,
      Completable.class,
      Maybe.class,
      Flowable.class);

  public static final ImmutableList<Class> RX_CLASSES = ImmutableList.of(
      rx.Observable.class,
      rx.Single.class,
      rx.Completable.class,
      Observable.class,
      Single.class,
      Completable.class,
      Maybe.class,
      Flowable.class);

  public static final String SUBSCRIBE = "subscribe";
  public static final String SUBSCRIBE_WITH = "subscribeWith";

  public static Matcher<ExpressionTree> generateMatcherForSameMethodAndMultipleClasses(
      List<Class> classes, String method) {
    List<MethodMatchers.MethodNameMatcher> matchers = new ArrayList<>();
    for (Class aClass : classes) {
      matchers.add(Matchers.instanceMethod().onExactClass(aClass.getName()).named(method));
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

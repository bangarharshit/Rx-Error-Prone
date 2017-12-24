package com.rx.errorprone.testdata;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class SubscribeOnErrorMissingCheckPositiveCases {

  public void testSubscribeNoArgs() {
    // BUG: Diagnostic contains: Subscriber is missing onError handling.
    Observable.just(1).subscribe();
  }

  public void testSubscribeWithConsumer() {
    Observable.just(1)
        // BUG: Diagnostic contains: Subscriber is missing onError handling.
        .subscribe(
            new Consumer<Integer>() {
              @Override
              public void accept(Integer integer) throws Exception {}
            });
  }

  public void testSingleSubscribeWithConsumer() {
    Single.just(1)
        // BUG: Diagnostic contains: Subscriber is missing onError handling.
        .subscribe(
            new Consumer<Integer>() {
              @Override
              public void accept(Integer integer) throws Exception {}
            });
  }

  public void testMaybeSubscribeWithConsumer() {
    Maybe.just(1)
        // BUG: Diagnostic contains: Subscriber is missing onError handling.
        .subscribe(
            new Consumer<Integer>() {
              @Override
              public void accept(Integer integer) throws Exception {}
            });
  }

  public void testCompletableSubscribeWithAction() {
    Completable.complete()
        // BUG: Diagnostic contains: Subscriber is missing onError handling.
        .subscribe(
            new Action() {
              @Override
              public void run() throws Exception {}
            });
  }

  public void testFlowableSubscribeWithConsumer() {
    Flowable.just(1)
        // BUG: Diagnostic contains: Subscriber is missing onError handling.
        .subscribe(
            new Consumer<Integer>() {
              @Override
              public void accept(Integer integer) throws Exception {}
            });
  }
}

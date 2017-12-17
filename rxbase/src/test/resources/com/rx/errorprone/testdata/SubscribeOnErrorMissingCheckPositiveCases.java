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
    // BUG: Diagnostic contains: Subscriber is missing onError handling
    Observable.just(1).subscribe();
  }

  public void testSubscribeWithConsumer() {
    // BUG: Diagnostic contains: Subscriber is missing onError handling
    Observable.just(1).subscribe(new Consumer<Integer>() {
      @Override public void accept(Integer integer) throws Exception {
      }
    });
  }

  public void testSingleSubscribeWithConsumer() {
    // BUG: Diagnostic contains: Subscriber is missing onError handling
    Single.just(1).subscribe(new Consumer<Integer>() {
      @Override public void accept(Integer integer) throws Exception {

      }
    });
  }

  public void testMaybeSubscribeWithConsumer() {
    // BUG: Diagnostic contains: Subscriber is missing onError handling
    Maybe.just(1).subscribe(new Consumer<Integer>() {
      @Override public void accept(Integer integer) throws Exception {

      }
    });
  }

  public void testCompletableSubscribeWithAction() {
    // BUG: Diagnostic contains: Subscriber is missing onError handling
    Completable.complete().subscribe(new Action() {
      @Override public void run() throws Exception {

      }
    });
  }

  public void testFlowableSubscribeWithConsumer() {
    // BUG: Diagnostic contains: Subscriber is missing onError handling
    Flowable.just(1).subscribe(new Consumer<Integer>() {
      @Override public void accept(Integer integer) throws Exception {

      }
    });
  }
}

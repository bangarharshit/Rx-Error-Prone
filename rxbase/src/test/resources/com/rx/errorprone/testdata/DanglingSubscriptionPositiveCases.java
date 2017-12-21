package com.rx.errorprone.testdata;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;

public class DanglingSubscriptionPositiveCases {

  public void testSubscribeObservable() {
    // BUG: Diagnostic contains: Observable's subscription should be assigned to a disposable for
    // cleanup
    Observable.just(1).subscribe();
  }

  public void testSubscribeWithObservable() {
    // BUG: Diagnostic contains: Observable's subscription should be assigned to a disposable for
    // cleanup
    Observable.just(1)
        .subscribeWith(
            new DisposableObserver<Integer>() {
              @Override
              public void onNext(Integer integer) {}

              @Override
              public void onError(Throwable e) {}

              @Override
              public void onComplete() {}
            });
  }

  public void testSubscribeSingle() {
    // BUG: Diagnostic contains: Observable's subscription should be assigned to a disposable for
    // cleanup
    Single.just(1).subscribe();
  }

  public void testSubscribeCompletable() {
    // BUG: Diagnostic contains: Observable's subscription should be assigned to a disposable for
    // cleanup
    Completable.complete().subscribe();
  }

  public void testSubscribeMaybe() {
    // BUG: Diagnostic contains: Observable's subscription should be assigned to a disposable for
    // cleanup
    Maybe.just(1).subscribe();
  }

  public void testSubscribeFlowable() {
    // BUG: Diagnostic contains: Observable's subscription should be assigned to a disposable for
    // cleanup
    Flowable.just(1).subscribe();
  }
}

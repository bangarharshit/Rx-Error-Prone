package com.rx.errorprone.testdata;

import com.rx.errorprone.ComponentWithLifeCycle;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;

public class DanglingSubscriptionPositiveCases extends ComponentWithLifeCycle {

  public void testSubscribeObservable() {
    // BUG: Diagnostic contains: Subscription should be assigned to a disposable.
    Observable.just(1).subscribe();
  }

  public void testSubscribeWithObservable() {
    Observable.just(1)
        // BUG: Diagnostic contains: Subscription should be assigned to a disposable.
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
    // BUG: Diagnostic contains: Subscription should be assigned to a disposable.
    Single.just(1).subscribe();
  }

  public void testSubscribeCompletable() {
    // BUG: Diagnostic contains: Subscription should be assigned to a disposable.
    Completable.complete().subscribe();
  }

  public void testSubscribeMaybe() {
    // BUG: Diagnostic contains: Subscription should be assigned to a disposable.
    Maybe.just(1).subscribe();
  }

  public void testSubscribeFlowable() {
    // BUG: Diagnostic contains: Subscription should be assigned to a disposable.
    Flowable.just(1).subscribe();
  }
}

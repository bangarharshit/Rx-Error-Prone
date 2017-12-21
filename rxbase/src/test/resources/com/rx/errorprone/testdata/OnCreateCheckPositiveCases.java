package com.rx.errorprone.testdata;

import rx.Observable;
import rx.Subscriber;

public class OnCreateCheckPositiveCases {

  public void testObservableCreate() {
    // BUG: Diagnostic contains: Prefer backpressure compliant operators to onCreate without
    // backpressure
    Observable.create(
        new Observable.OnSubscribe<String>() {
          @Override
          public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("ds");
          }
        });
  }

  public void testObservableUnSafeCreate() {
    // BUG: Diagnostic contains: Prefer backpressure compliant operators to onCreate without
    // backpressure
    Observable.unsafeCreate(
        new Observable.OnSubscribe<String>() {
          @Override
          public void call(Subscriber<? super String> subscriber) {}
        });
  }
}

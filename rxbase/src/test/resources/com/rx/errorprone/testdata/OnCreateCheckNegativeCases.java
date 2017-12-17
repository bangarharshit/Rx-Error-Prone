package com.rx.errorprone.testdata;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

public class OnCreateCheckNegativeCases {
  public void testObservableJust() {
    Observable.just(1);
  }
}

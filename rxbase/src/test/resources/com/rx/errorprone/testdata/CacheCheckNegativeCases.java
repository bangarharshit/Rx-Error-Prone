package com.rx.errorprone.testdata;

import io.reactivex.Observable;

public class CacheCheckNegativeCases {

  public void testObservableReplay() {
    Observable.just(1).replay();
  }
}

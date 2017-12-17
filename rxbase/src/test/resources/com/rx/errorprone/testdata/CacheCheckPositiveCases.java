package com.rx.errorprone.testdata;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public class CacheCheckPositiveCases {

  public void testObservableCache() {
    // BUG: Diagnostic contains: Don't use cache. Use replay() instead
    Observable.just(1).cache();
  }

  public void testrx1ObservableCache() {
    // BUG: Diagnostic contains: Don't use cache. Use replay() instead
    rx.Observable.just(1).cache();
  }

  public void testSingleCache() {
    // BUG: Diagnostic contains: Don't use cache. Use replay() instead
    Single.just(1).cache();
  }

  public void testRx1SingleCache() {
    // BUG: Diagnostic contains: Don't use cache. Use replay() instead
    rx.Single.just(1).cache();
  }

  public void testCompletableCache() {
    // BUG: Diagnostic contains: Don't use cache. Use replay() instead
    Completable.complete().cache();
  }

  public void testMaybeCache() {
    // BUG: Diagnostic contains: Don't use cache. Use replay() instead
    Maybe.just(1).cache();
  }

  public void testFlowableCache() {
    // BUG: Diagnostic contains: Don't use cache. Use replay() instead
    Flowable.just(1).cache();
  }
}

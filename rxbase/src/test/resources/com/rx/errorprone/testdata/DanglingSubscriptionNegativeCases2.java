package com.rx.errorprone.testdata;

import io.reactivex.Observable;

public class DanglingSubscriptionNegativeCases2 {

  public void testObservableCreation() {
    Observable.just(1).subscribe();
  }
}

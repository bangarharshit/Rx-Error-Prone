package com.rx.errorprone.testdata;

import io.reactivex.Observable;

public class SubscriptionInConstructorCheckPositiveCases {

  public void testObservableSubscribeInConstructor() {
    ABC abc = new ABC();
    System.out.println(abc);
  }

  public static class ABC {
    public ABC() {
      // BUG: Diagnostic contains: Subscription should not be done in constructor
      Observable.just(1).subscribe();
    }
  }
}

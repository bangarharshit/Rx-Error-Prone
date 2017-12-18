package com.rx.errorprone.testdata;

import io.reactivex.Observable;

public class SubscriptionInConstructorCheckNegativeCases {

  public void testObservableCreationInConstructor() {
    ABC abc = new ABC();
    System.out.println(abc);
  }

  public void testObservableSubscriptionInInit() {
    ABC abc = new ABC();
    abc.init();
  }

  public void testObservableSubscriptionInStaticCreate() {
    ABC abc = ABC.create();
  }

  public static class ABC {
    Observable observable;
    public ABC() {
      observable = Observable.just(1);
    }

    public void init() {
      observable.subscribe();
    }

    public static ABC create() {
      ABC abc = new ABC();
      abc.observable.subscribe();
      return abc;
    }
  }


}

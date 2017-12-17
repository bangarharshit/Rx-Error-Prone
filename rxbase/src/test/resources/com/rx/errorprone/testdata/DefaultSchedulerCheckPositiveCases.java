package com.rx.errorprone.testdata;

import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;

public class DefaultSchedulerCheckPositiveCases {

  public void testObservableReplay() {
    Observable.interval(2, TimeUnit.SECONDS);
  }
}

package com.rx.errorprone.testdata;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class DefaultSchedulerCheckNegativeCases {

  public void testObservableIntervalWithExplicitScheduler() {
    Observable.interval(2, TimeUnit.SECONDS, Schedulers.io());
  }

  public void testObservableTimerWithExplicitScheduler() {
    Observable.timer(2, TimeUnit.SECONDS, Schedulers.io());
  }
}

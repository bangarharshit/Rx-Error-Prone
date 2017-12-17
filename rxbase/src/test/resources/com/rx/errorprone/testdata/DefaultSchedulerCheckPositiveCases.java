package com.rx.errorprone.testdata;

import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;

public class DefaultSchedulerCheckPositiveCases {

  public void testObservableIntervalWithoutExplicitScheduler() {
    // BUG: Diagnostic contains: Using a default scheduler
    Observable.interval(2, TimeUnit.SECONDS);
  }

  public void testObservableTimerWithoutExplicitScheduler() {
    // BUG: Diagnostic contains: Using a default scheduler
    Observable.timer(2, TimeUnit.SECONDS);
  }
}

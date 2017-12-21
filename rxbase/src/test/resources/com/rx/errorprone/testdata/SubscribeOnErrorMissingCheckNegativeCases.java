package com.rx.errorprone.testdata;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SubscribeOnErrorMissingCheckNegativeCases {

  public void testSubscribeWithObserver() {
    Observable.just(1)
        .subscribe(
            new Observer<Integer>() {
              @Override
              public void onSubscribe(Disposable d) {}

              @Override
              public void onNext(Integer integer) {}

              @Override
              public void onError(Throwable e) {}

              @Override
              public void onComplete() {}
            });
  }

  public void testSubscribeWithErrorConsumer() {
    Observable.just(1)
        .subscribe(
            new Consumer<Integer>() {
              @Override
              public void accept(Integer integer) throws Exception {}
            },
            new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {}
            });
  }
}

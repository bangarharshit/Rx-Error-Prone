package com.rx.errorprone.testdata;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import org.reactivestreams.Subscription;

public class DanglingSubscriptionNegativeCases {

  public void testSubscribeObservableWithObserver() {
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

  public void testJustObservable() {
    Observable.just(1);
  }

  public void testSubscribeSingleWithObserver() {
    Single.just(1)
        .subscribe(
            new SingleObserver<Integer>() {
              @Override
              public void onSubscribe(Disposable d) {}

              @Override
              public void onSuccess(Integer integer) {}

              @Override
              public void onError(Throwable e) {}
            });
  }

  public void testSubscribeCompletableWithObserver() {
    Completable.complete()
        .subscribe(
            new CompletableObserver() {
              @Override
              public void onSubscribe(Disposable d) {}

              @Override
              public void onComplete() {}

              @Override
              public void onError(Throwable e) {}
            });
  }

  public void testSubscribeMaybeWithObserver() {
    Maybe.just(1)
        .subscribe(
            new MaybeObserver<Integer>() {
              @Override
              public void onSubscribe(Disposable d) {}

              @Override
              public void onSuccess(Integer integer) {}

              @Override
              public void onError(Throwable e) {}

              @Override
              public void onComplete() {}
            });
  }

  public void testSubscribeFlowableWithSubscriber() {
    Flowable.just(1)
        .subscribe(
            new FlowableSubscriber<Integer>() {
              @Override
              public void onSubscribe(Subscription s) {}

              @Override
              public void onNext(Integer integer) {}

              @Override
              public void onError(Throwable t) {}

              @Override
              public void onComplete() {}
            });
  }

  public void testObservableCreation() {
    Observable.just(1);
  }

  public void testCompositeDisposable(Disposable disposable) {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    compositeDisposable.add(disposable);
  }
}

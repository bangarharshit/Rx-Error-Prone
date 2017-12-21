package com.rx.errorprone.testdata;

import rx.Observable;
import rx.Observer;
import rx.observables.SyncOnSubscribe;

public class OnCreateCheckNegativeCases {

  public void testObservableJust() {
    Observable.just(1);
  }

  public void testObservableOnCreateWithoutOnSubscribe() {
    Observable.create(
        new SyncOnSubscribe<String, String>() {
          @Override
          protected String generateState() {
            return null;
          }

          @Override
          protected String next(String state, Observer<? super String> observer) {
            return null;
          }
        });
  }
}

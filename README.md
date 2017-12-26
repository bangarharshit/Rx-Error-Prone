# Rx-error-prone: Catch common Rx-Java errors at compile time.

It is a set of checks for RxJava code. The code below 
will emit the value on `computation` and not the `main` thread like one might think.

```java
public class SampleVerifier {
  public static void main(String[] args) {
    Observable observable = Observable.just(1).observeOn(Schedulers.io()).delay(1, TimeUnit.SECONDS);
  }
}
```

```
$ ./gradlew clean :sample:build
> Task :sample:compileJava FAILED
/Users/harshitbangar/Desktop/RIBs/rx/sample/src/main/java/SampleVerifier.java:7: error: [DefaultSchedulerCheck] Using a default scheduler
    Observable observable = Observable.just(1).observeOn(Schedulers.io()).delay(1, TimeUnit.SECONDS);
                                                                               ^
    (see https://bitbucket.org/littlerobots/rxlint/)
1 error
```

## How to use
Just add to your `build.gradle`
```gradle
apt "com.github.bangarharshit:rxbase:0.0.3"
```

By default all the checks are enabled. To disable check for code/unit-tests use the snippet below.
```gradle 
tasks.withType(JavaCompile) {
    if (!name.toLowerCase().contains("test")) {
        // For actual code.
        options.compilerArgs += ["-Xep:DefaultSchedulerCheck:OFF"]
    } else {
        // For unit tests.
        options.compilerArgs += [ '-Xep:DefaultSchedulerCheck:OFF', '-Xep:DanglingSubscriptionCheck:OFF']
    }
}
```

The list of checks
```gradle
1. DefaultSchedulerCheck
2. DanglingSubscriptionCheck
3. OnCreateCheck (for Rx1 create methods without backpressure)
4. CacheCheck
5. SubscribeOnErrorMissingCheck
6. SubscriptionInConstructorCheck
```


### SubscribeOnErrorMissingCheck
Check if the subscriber is handling the `onError()` callback. 

Every observable can report errors. Not implementing onError will throw an exception at runtime,
 which can be hard to debug when the error is thrown on a Scheduler that is not the invoking thread.

If a subscriber doesn't handle the error and the Observable emits an error, 
it is wrapped into an OnErrorNotImplementedException and routed to the RxJavaPlugins.onError handler.

Another advantage of implementing `onError` is a better stack-trace. You can include tags to enrich the trace.

### DefaultSchedulerCheck
Operators like `delay` or `interval` run on `Schedulers.computation()` by default. It can be extremely confusing
for the scenarios like - `someObservable.observeOn(AndroidSchedulers.MainThread()).interval(2, TimeUnit.Seconds)`
which will emit the value on `computation` and not the `main` thread like one might think.

An overloaded version is available where a custom scheduler can be provided and should be used. 

### DanglingSubscriptionCheck
Check that all the subscriptions are assigned to disposables. 
Any unassigned subscription means the subscription can't be `disposed()` or `unSubscribe()` 
which can lead to memory leaks and crashes in runtime.


### CacheCheck
cache() is a hard to understand operator. Use replay() instead. cache() is same as replay().autoConnect(). 
replay() is much more flexible, less confusing and can be combined with different multi-casting operations.
Check Dan Lew's [talk](https://youtu.be/QdmkXL7XikQ?t=19m21s) on the same.

### OnCreateCheck (for Rx1)
`<T>Observable.create(rx.Observable.OnSubscribe<T>)` and `unSafeCreate` doesn't handle backpressure.
These operators construct an Observable in an unsafe manner, that is, unsubscription and backpressure handling 
is the responsibility of the OnSubscribe implementation.

### SubscriptionInConstructorCheck
Subscription in constructor leads to unsafe object creation and is similar to starting a thread in the constructor.
Check the [safe construction technique article](https://www.ibm.com/developerworks/library/j-jtp0618/index.html) 
for more details.

Use any of the other overloaded `create` methods or `just/fromCallable` any other generator.

## Rxlint
[Rxlint](https://bitbucket.org/littlerobots/rxlint) is a great tool but has a few limitations:

1. It is specific to Android.
2. It is a separate tool and not the part of your build tool chain. 

Error-prone is integrated into the compilation chain and so it helps you [fail early](https://artemzin.com/blog/android-development-culture-the-document-qualitymatters/). For some this may not be an issue since IntelliJ flags all the lint/error-prone errors, so it fails earlier.

 

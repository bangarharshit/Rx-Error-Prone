# Rx-error-prone: Catch common Rx-Java errors at compile time.

It is a set of checks for RxJava code. Currently, there are following checks:

### SubscribeOnErrorMissingCheck
Check if subscriber is handling the `onError()` callback. 

Every observable can report errors. Not implementing onError will throw an exception at runtime,
 which can be hard to debug when the error is thrown on a Scheduler that is not the invoking thread.

If a subscriber doesn't handle the error and the Observable emits an error, 
it is wrapped into an OnErrorNotImplementedException and routed to the RxJavaPlugins.onError handler.

Another advantage of implementing `onError` is a better stack-trace. You can include tags to enrich the trace.

### DefaultSchedulerCheck
Operators like `delay` or `interval` runs on `Schedulers.computation()` by default. It can be extremely confusing
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
These operators constructs an Observable in an unsafe manner, that is, unsubscription and backpressure handling 
is the responsibility of the OnSubscribe implementation.

Use any of the other overloaded `create` methods or `just/fromCallable` any other generator.

## Rxlint
[Rxlint](https://bitbucket.org/littlerobots/rxlint) is a great tool but has a few limitations:

1. It is specific to android.
2. It is a separate tool and not the part of your build tool chain. 

Error prone is integrated into the compilation chain and so it helps you [fail early](https://artemzin.com/blog/android-development-culture-the-document-qualitymatters/). For some this may not be an issue since intellij flags all the lint/error-prone errors, so it is fail earlier.


 

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;

public class SampleVerifier {
  public static void main(String[] args) {
    Disposable disposable = Observable.interval(1, TimeUnit.SECONDS).cache().subscribe();
  }
}

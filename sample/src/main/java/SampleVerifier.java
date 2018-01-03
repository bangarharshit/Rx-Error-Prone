import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class SampleVerifier {
  public static void main(String[] args) {
    Observable observable =
        Observable.just(1).observeOn(Schedulers.io()).delay(1, TimeUnit.SECONDS);
  }
}

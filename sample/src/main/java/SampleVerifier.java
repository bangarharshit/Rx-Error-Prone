import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;

public class SampleVerifier {
  public static void main(String[] args) {
    Observable observable = Observable.interval(2, TimeUnit.SECONDS);
  }
}

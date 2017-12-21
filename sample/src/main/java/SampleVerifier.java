import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;

public class SampleVerifier {
  public static void main(String[] args) {
    Observable.interval(1, TimeUnit.SECONDS).cache().subscribe();
  }
}

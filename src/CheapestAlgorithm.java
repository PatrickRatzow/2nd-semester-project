import java.util.Random;

public class CheapestAlgorithm extends Thread {
    Random rnd = new Random();

    public void run() {
        try {
            sleep(rnd.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

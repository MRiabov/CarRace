import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Main {
    public static CyclicBarrier startLine = new CyclicBarrier(11);
    public static CyclicBarrier finishLine = new CyclicBarrier(10);
    public static Semaphore tunnel = new Semaphore(3);
    public static int winner;
    public static final Object WINNER_MONITOR=new Object();

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Car());
            thread.start();
        }
        rideBegin();
        long now=System.currentTimeMillis();
        rideEnd();
    }

    private static void rideBegin() throws BrokenBarrierException, InterruptedException {
        startLine.await();
        System.out.println("BEGIN!!!");
    }
    private static void rideEnd() throws BrokenBarrierException, InterruptedException {
        finishLine.await();
        System.out.println("The end");
    }
}

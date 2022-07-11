import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Car implements Runnable{
    Random random=new Random();
    private final long number=random.nextInt(50);

    @Override
    public void run() {
        try {
            prepare();
            onTheRoad();
            throughTunnel();
            onTheRoad();
            finish();
        } catch (InterruptedException | BrokenBarrierException ignored) {
        }
    }

    private void prepare() throws InterruptedException, BrokenBarrierException {
        Thread.sleep(random.nextInt(3000)+2000);
        System.out.println(number +" has finished preparing. Vroom!");
        Main.startLine.await();
    }

    private void onTheRoad() throws InterruptedException {
        System.out.println(number +" is on the road. The driver's cheeks are clenched");
        Thread.sleep(3000+random.nextInt(2000));
    }

    private void throughTunnel()  {
        try {
            Main.tunnel.acquire();
            System.out.println(number +" has entered the tunnel.");
            Thread.sleep(random.nextInt(3000)+1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {Main.tunnel.release();}
    }

    private void finish() throws BrokenBarrierException, InterruptedException {
        System.out.println(number +" has finished the ride.");

        synchronized (Main.WINNER_MONITOR) {
            if (Main.winner!=0) Main.winner=(int) number;
        }
        Main.finishLine.await();
    }



}

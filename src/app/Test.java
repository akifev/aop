package app;

import java.util.Random;

public class Test {
    static final long SLEEP = 500;
    static final int TIMES = 100;

    private final Random random = new Random(System.currentTimeMillis());

    public void run() throws InterruptedException {
        for (int i = 0; i < TIMES; i++) {
            switch (random.nextInt(2)) {
                case 0 -> zero();
                case 1 -> one();
            }
        }
    }

    public void zero() throws InterruptedException {
        Thread.sleep(random.nextInt(2) * SLEEP);
    }

    public void one() throws InterruptedException {
        Thread.sleep(random.nextInt(2) * SLEEP);
    }
}
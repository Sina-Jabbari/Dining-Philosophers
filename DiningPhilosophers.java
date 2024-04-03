import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DiningPhilosophers {
    private final int numPhilosophers;
    private final Philosopher[] philosophers;
    private final Lock[] forks;

    public DiningPhilosophers(int numPhilosophers) {
        this.numPhilosophers = numPhilosophers;
        philosophers = new Philosopher[numPhilosophers];
        forks = new Lock[numPhilosophers];
        for (int i = 0; i < numPhilosophers; i++) {
            philosophers[i] = new Philosopher(i);
            forks[i] = new ReentrantLock();
        }
    }

    public void startEating() {
        for (Philosopher philosopher : philosophers) {
            philosopher.start();
        }
    }

    public void stopEating() {
        for (Philosopher philosopher : philosophers) {
            philosopher.interrupt();
        }
    }

    class Philosopher extends Thread {
        private final int id;
        private final int left;
        private final int right;

        public Philosopher(int id) {
            this.id = id;
            this.left = id;
            this.right = (id + 1) % numPhilosophers;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    think();
                    eat();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void think() throws InterruptedException {
            System.out.println("Philosopher " + id + " is thinking");
            Thread.sleep((long) (Math.random() * 1000)); // thinking time
        }

        private void eat() throws InterruptedException {
            while (true) {
                if (forks[left].tryLock()) {
                    try {
                        if (forks[right].tryLock()) {
                            try {
                                System.out.println("Philosopher " + id + " has forks " + left + " and " + right);
                                System.out.println("Philosopher " + id + " is eating");
                                Thread.sleep((long) (Math.random() * 1000)); // eating time
                                System.out.println("Philosopher " + id + " has put down forks " + left + " and " + right);
                                return;
                            } finally {
                                forks[right].unlock();
                            }
                        }
                    } finally {
                        forks[left].unlock();
                    }
                }
                Thread.sleep(10); // Sleep for a short while before retrying
            }
        }
    }
}

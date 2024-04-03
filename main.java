public class main {
    public static void main(String[] args) {
        DiningPhilosophers diningPhilosophers = new DiningPhilosophers(5);
        diningPhilosophers.startEating();

        try {
            Thread.sleep(5000); // Let philosophers eat for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        diningPhilosophers.stopEating();
    }
}

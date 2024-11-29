package scenario01;

public class Barista implements Runnable {
    private final CoffeeShop coffeeShop;

    public Barista(CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String order = coffeeShop.prepareOrder();
                System.out.println(Thread.currentThread().getName() + " prepares order: " + order);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

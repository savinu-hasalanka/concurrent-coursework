package scenario01;

public class Main {
    public static void main(String[] args) {
        CoffeeShop coffeeShop = new CoffeeShop(10);

        Barista barista = new Barista(coffeeShop);
        Customer customer = new Customer(coffeeShop);

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(barista, "Barista #" + i);
            thread.setDaemon(true);
            thread.start();
        }

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(customer, "Customer #" + i);
            thread.start();
        }
    }
}
package scenario01;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CoffeeShop {
    private final Queue<String> orderQueue = new LinkedList<>();
    private final int capacity;
    private final Lock lock = new ReentrantLock();

    private final Condition queueEmpty = lock.newCondition();
    private final Condition queueFull = lock.newCondition();

    public CoffeeShop(int capacity) {
        this.capacity = capacity;
    }

    public void placeOrder(String order) throws InterruptedException {
        lock.lock();

        while (orderQueue.size() >= capacity) {
            System.out.println(Thread.currentThread().getName() + " going to wait");
            queueFull.await();
        }

        orderQueue.add(order);
        System.out.println(Thread.currentThread().getName() + " added order");
        queueEmpty.signalAll();
        lock.unlock();
    }

    public  String prepareOrder() throws InterruptedException {
        lock.lock();

        while (orderQueue.isEmpty()) {
            System.out.println(Thread.currentThread().getName() + " going to wait");
            queueEmpty.await();
        }

        String order = orderQueue.poll();
        queueFull.signalAll();
        lock.unlock();
        return order;
    }

}

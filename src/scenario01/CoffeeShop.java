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

    // two separate waiting rooms for consumer(baristas) and producers(customers)
    private final Condition queueEmpty = lock.newCondition();
    private final Condition queueFull = lock.newCondition();

    public CoffeeShop(int capacity) {
        this.capacity = capacity;
    }

    public void placeOrder(String order) throws InterruptedException {
        // acquire the lock
        lock.lock();

        // check if the queue is full
        while (orderQueue.size() >= capacity) {
            System.out.println(Thread.currentThread().getName() + " going to wait");
            // send the thread to a waiting state
            // thread will give up  the lock as it goes to the waiting state
            queueFull.await();
        }

        // perform the critical action
        orderQueue.add(order);

        System.out.println(Thread.currentThread().getName() + " added order");

        // signal the threads waiting on "queueEmpty" condition
        queueEmpty.signalAll();

        // release the lock
        lock.unlock();
    }

    public  String prepareOrder() throws InterruptedException {
        // acquire the lock
        lock.lock();

        // check if the queue is full
        while (orderQueue.isEmpty()) {
            System.out.println(Thread.currentThread().getName() + " going to wait");
            // send the thread to a waiting state
            // thread will give up  the lock as it goes to the waiting state
            queueEmpty.await();
        }

        // perform the critical action
        String order = orderQueue.poll();

        // signal the threads waiting on "queueFull" condition
        queueFull.signalAll();

        // release the lock
        lock.unlock();
        return order;
    }

}

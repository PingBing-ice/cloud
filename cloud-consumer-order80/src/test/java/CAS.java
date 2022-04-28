import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CAS {
    @Test
    public void Test() {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 2021)+"\t修改后的值:"+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 2022)+"\t修改后的值:"+atomicInteger.get());
        atomicInteger.getAndIncrement();
    }

    // 原子引用线程
    AtomicReference<Thread> threadAtomicReference = new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();

        System.out.println(Thread.currentThread().getName()+"\t invoked myLock");
        while (!threadAtomicReference.compareAndSet(null, thread)) {

        }
    }

    public void myUnLock() {
        Thread thread = Thread.currentThread();
        threadAtomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName()+"\t invoked myUnLock");
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("================");
        CAS cas = new CAS();
        new Thread(() -> {
            cas.myLock();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cas.myUnLock();

        }, "AA").start();
        Thread.sleep(1);
        new Thread(() -> {
            cas.myLock();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cas.myUnLock();

        }, "BB").start();
    }
}

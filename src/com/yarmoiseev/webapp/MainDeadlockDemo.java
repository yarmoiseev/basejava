package com.yarmoiseev.webapp;

public class MainDeadlockDemo {
    static final String resource1 = "resource1";
    static final String resource2 = "resource2";

    public static void main(String[] args) {
        Thread t1 = makeThread(resource1, resource2);
        Thread t2 = makeThread(resource2, resource1);
        startThreads(t1, t2);

    }

    static void startThreads(Thread t1, Thread t2) {
        t1.start();
        t2.start();
    }

    static Thread makeThread(String r1, String r2) {
        return new Thread(() -> {
            synchronized (r1) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (r2) {

                }
            }
        });
    }

}

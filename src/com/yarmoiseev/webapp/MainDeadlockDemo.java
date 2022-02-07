package com.yarmoiseev.webapp;

public class MainDeadlockDemo {
    static final String resource1 = "resource1";
    static final String resource2 = "resource2";

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (resource1) {
                //System.out.println("Thread 1 locked resource 1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource2) {
                    //System.out.println("Thread 1 locked resource 2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (resource2) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("Thread 2 locked resource 2");

                synchronized (resource1) {
                    //System.out.println("Thread 2 locked resource 1");
                }
            }
        });
        startThreads(t1, t2);

    }

    static void startThreads(Thread t1, Thread t2) {
        t1.start();
        t2.start();
    }

}

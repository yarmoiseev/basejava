package com.yarmoiseev.webapp;

public class MainDeadlockDemo {
    static final String resource1 = "resource1";
    static final String resource2 = "resource2";

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1 locked resource 1");

                synchronized (resource2) {
                    System.out.println("Thread 1 locked resource 2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread 2 locked resource 2");

                synchronized (resource1) {
                    System.out.println("Thread 2 locked resource 1");
                }
            }
        });

        t1.start();
        t2.start();
    }

}

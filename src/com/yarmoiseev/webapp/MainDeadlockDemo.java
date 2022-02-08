package com.yarmoiseev.webapp;

public class MainDeadlockDemo {
    static final String resource1 = "resource1";
    static final String resource2 = "resource2";

    public static void main(String[] args) {
        makeThread(resource1, resource2).start();
        makeThread(resource2, resource1).start();

    }


    static Thread makeThread(String r1, String r2) {
        return new Thread(() -> {
            synchronized (r1) {
                System.out.println("Thread " + Thread.currentThread().getName() + " took  " + r1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread " + Thread.currentThread().getName() + " is trying to take  " + r2);
                synchronized (r2) {

                }
            }
        });
    }

}

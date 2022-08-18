package org.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    private static final int MAX_NUMBER_AND_SIZE = 1_000_000;


    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
        Map<Integer, String> hashMap = Collections.synchronizedMap(new HashMap<>());

        Runnable addConcurrentHashMap = (() -> {
            for (int i = 0; i < MAX_NUMBER_AND_SIZE; i++) {
                concurrentHashMap.put((int) Math.round(Math.random()*MAX_NUMBER_AND_SIZE), "Thread");
            }
        });

        Runnable readConcurrentHashMap = (() -> {
            for (int i = 0; i < MAX_NUMBER_AND_SIZE; i++) {
                concurrentHashMap.get(i);
            }
        });

        Runnable addHashMap = (() -> {
            for (int i = 0; i < MAX_NUMBER_AND_SIZE; i++) {
                hashMap.put((int) Math.round(Math.random()*MAX_NUMBER_AND_SIZE), "Thread");;
            }
        });

        Runnable readHashMap = (() -> {
            for (int i = 0; i < MAX_NUMBER_AND_SIZE; i++) {
                hashMap.get(i);
            }
        });

        Thread thread1 = new Thread(addConcurrentHashMap);
        Thread thread2 = new Thread(addConcurrentHashMap);
        Thread thread3 = new Thread(readConcurrentHashMap);

        Thread thread4 = new Thread(addHashMap);
        Thread thread5 = new Thread(addHashMap);
        Thread thread6 = new Thread(readHashMap);

        long startTs = System.currentTimeMillis(); // start time
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        long endTs = System.currentTimeMillis(); // end time
        System.out.println("Time work ConcurrentHashMap: " + (endTs - startTs) + "ms");

        long startTsMap = System.currentTimeMillis(); // start time
        thread4.start();
        thread5.start();
        thread6.start();
        thread4.join();
        thread5.join();
        thread6.join();
        long endTsMap = System.currentTimeMillis(); // end time
        System.out.println("Time work HashMap: " + (endTsMap - startTsMap) + "ms");

    }


}
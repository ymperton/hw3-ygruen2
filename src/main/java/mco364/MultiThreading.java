

/*

I think i misunderstood the hw, and assumed there were two parts to the HW. So that's what I did...Sorry

*/



package mco364;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class MyThread extends Thread { 

    private Random numberGen = new Random();
    private final int NUMBER_OF_ADDITIONS = 100000000;
    public static double sharedAverageTotal;
    private int numberOfLoops = 0;
    private long total;
    private double average;

    MyThread(int number) {
        numberOfLoops = (NUMBER_OF_ADDITIONS / number);
    }

    @Override
    public void run() {

        for (int i = 0; i < numberOfLoops; i++) {
            total += numberGen.nextInt(100) + 1;
        }

        average = (double) total / numberOfLoops;

        synchronized ("BathroomKey") {
            MyThread.sharedAverageTotal += average;
        }

    }

}

class StopWatch {

    private long startTime = 0;
    private boolean calledStop = false;

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public int stopTimer() {

        if (startTime == 0) {
            throw new RuntimeException("Didn't call startTimer()");
        }
        if (calledStop) {
            throw new RuntimeException("Already called stopTimer()");
        }

        calledStop = true;
        return (int) (System.currentTimeMillis() - startTime);
    }
}

public class MultiThreading {

    public static void main(String[] args) {

        int[] amountOfThreads = {1, 2, 4, 16, 32, 64, 128, 256};

        for (int j = 0; j < amountOfThreads.length; j++) {

            int numberOfThreads = amountOfThreads[j];
            StopWatch timer = new StopWatch();

            ArrayList<MyThread> threadList = new ArrayList<>();
            timer.startTimer();
            for (int i = 0; i < numberOfThreads; i++) {
                MyThread t = new MyThread(numberOfThreads);
                threadList.add(t);
                t.start();
            }

            for (MyThread t : threadList) {
                try {
                    t.join(); // wait for each thread to die (= complete) before continuting
                } catch (InterruptedException ex) {
                    Logger.getLogger(MultiThreading.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            double avg = MyThread.sharedAverageTotal / numberOfThreads;

            System.out.println("Number of Threads: " + amountOfThreads[j]);
            System.out.println(avg);
            System.out.println(timer.stopTimer() + " milliseconds");
            System.out.println();
            //resets the total of static variable
            MyThread.sharedAverageTotal = 0;
        }

    }

    public static double averageMT(int[] list, int threadCount) {
        int startPosition = 0;
        int sizeOfPartions = list.length / threadCount;
        int extra = list.length % threadCount;
        StopWatch timer = new StopWatch();

        ArrayList<MyThread2> threadList = new ArrayList<>();
        timer.startTimer();
        for (int i = 0; i < threadCount; i++) {
            MyThread2 t = new MyThread2(list, startPosition, (startPosition + sizeOfPartions));
            threadList.add(t);
            t.start();
            startPosition += sizeOfPartions;
        }

        MyThread2 t = new MyThread2(list, startPosition, extra);
        threadList.add(t);
        t.start();

        for (MyThread2 t1 : threadList) {
            try {
                t1.join(); // wait for each thread to die (= complete) before continuting
            } catch (InterruptedException ex) {
                Logger.getLogger(MultiThreading.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return MyThread2.sharedAverageTotal / threadCount;
    }

}

class MyThread2 extends Thread {    // the entry point of a thread is run

    int[] array;
    int startIndex;
    int endIndex;
    public static double sharedAverageTotal;
    private long total;
    private double average;

    MyThread2(int[] thatArray, int start, int end) {
        this.array = thatArray;
        startIndex = start;
        endIndex = Math.min(this.array.length, end);
    }

    @Override
    public void run() {

        for (int i = startIndex; i < endIndex; i++) {
            total += array[i];
        }

        average = (double) total / (endIndex - startIndex);

        synchronized ("BathroomKey") {
            MyThread2.sharedAverageTotal += average;
        }

    }

}

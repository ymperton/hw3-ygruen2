package mco364;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class MyThread extends Thread {    // the entry point of a thread is run

    private static Random numberGen = new Random();
    public static double sharedAverage;
    int LOOP_MAX = 0; 
    long total;
    double average;

    MyThread(int number) {
        LOOP_MAX =  (100000000 / number);
    }

    @Override
    public void run() {

        for (int i = 0; i < LOOP_MAX; i++) {
            total += numberGen.nextInt(100) + 1;
        }

        average = (double) total / LOOP_MAX;

        synchronized ("BathroomKey") { // only one thread may enter at a time
            MyThread.sharedAverage += average;
        }

    }

}

class StopWatch {

    long startTime;

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    /**
     * returns the number of milliseconds since startTimer was called. If
     * startTimer was never called, or stopTimer was already called once, throws
     * an TimerException (a subclass of RuntimeException) with an appropriate
     * message
     */
    //Deal with above exceptions זך
    //Change return type back to int
    public double stopTimer() {
        return (System.currentTimeMillis() - startTime) / 1000.0;
    }
}

public class Main {

    public static void main(String[] args) {

        int numberOfThreads = 10;
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
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //double avg = MyThread.sharedAverage / numberOfThreads;
        System.out.println(MyThread.sharedAverage / numberOfThreads);
        System.out.println(timer.stopTimer());

    }
}

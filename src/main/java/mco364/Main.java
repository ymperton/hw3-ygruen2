package mco364;


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class MyThread extends Thread {    // the entry point of a thread is run

    final int LOOP_MAX = 1000000;
    int instanceCouter;
    static int sharedCounter;

    @Override
    public void run() {
        for (int i = 0; i < LOOP_MAX; i++) {
            instanceCouter++;
            synchronized("BathroomKey"){ // only one thread may enter at a time
            MyThread.sharedCounter++;
            }
            //System.out.println("T2: " + i);
            //Main.threadSleep(100);
        }
    }

}

public class Main {

    public static void threadSleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

        ArrayList<MyThread> threadList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MyThread t = new MyThread();
            threadList.add(t);
            t.start();
        }
        for(MyThread t : threadList){
            try {
                t.join(); // wait for each thread to die (= complete) before continuting
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for(MyThread t : threadList){
            System.out.println(t.instanceCouter + ", " + t.sharedCounter);
        }
        
    }
}

package mco364;

import org.junit.Test;
import static org.junit.Assert.*;


public class MultiThreadingTests {

    @Test
    public void averageMTTest() {
        int [] array = new int[5000];
        for (int i = 0; i < array.length; i++) {
            array[i] = 10;
        }
        
        double hello = MultiThreading.averageMT(array, 10);
        assertEquals(10.0, hello, .0001);
    }

}

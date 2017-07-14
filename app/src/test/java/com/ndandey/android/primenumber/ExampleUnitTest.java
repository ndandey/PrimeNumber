package com.ndandey.android.primenumber;

import android.content.Context;

import com.ndandey.android.primenumber.activities.PrimeNumberActivity;
import com.ndandey.android.primenumber.controllers.PrimeNumberController;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        int v1stPrimeNumber = PrimeNumberController.nthPrime(1);
        assertEquals(2, v1stPrimeNumber);
        int v100thPrimeNumber = PrimeNumberController.nthPrime(100);
        assertEquals(541, v100thPrimeNumber);
        int v1000thPrimeNumber = PrimeNumberController.nthPrime(1000);
        assertEquals(7919, v1000thPrimeNumber);
        int vMilliionthPrimeNumber = PrimeNumberController.nthPrime(1000000);
        assertEquals(15485863, vMilliionthPrimeNumber);
    }
}
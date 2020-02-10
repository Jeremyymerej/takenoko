package com.mycompany.app.FizzBuzz;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;


public class FizzBuzzTest {

    /**
     "Write a program that prints the numbers from 1 to 100.
     But for multiples of three print Fizz instead of the number and for the multiples of five print Buzz.
     For numbers which are multiples of both three and five print FizzBuzz.
     */

    private static final String FIZZ = "Fizz";
    private static final String BUZZ = "Buzz";
    private static final String FIZZBUZZ = "FizzBuzz";

    private String currentOutput;
    private FizzBuzz fb;

    @Before
    public void setUp(){
        fb = new FizzBuzz();

    }

    @Test
    public void testOutputFizzWhenMultipleOf3() {
        currentOutput = fb.fizzBuzzify(3);
        assertEquals(FIZZ, currentOutput);
    }

    @Test
    public void testOutputBuzzWhenMultipleOf5() {
        currentOutput = fb.fizzBuzzify(5);
        assertEquals(BUZZ, currentOutput);
    }

    @Test
    public void testOutputFizzBuzzWhenMultipleOf5andOf3() {
        currentOutput = fb.fizzBuzzify(15);
        assertEquals(FIZZBUZZ, currentOutput);
    }

    @Test
    public void testOutputItselfWhenNoMultipleOf3OrMultipleOf5() {
        currentOutput = fb.fizzBuzzify(1);
        assertEquals("1", currentOutput);
    }

    //@Test
    public void testOutput1to100() {
        for (int i = 1; i <= 100 ; i++) {
            System.out.println(fb.fizzBuzzify(i));
        }
    }

}

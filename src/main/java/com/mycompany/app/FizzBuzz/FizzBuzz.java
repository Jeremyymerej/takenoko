package com.mycompany.app.FizzBuzz;

public class FizzBuzz {

    private static final String FIZZ = "Fizz";
    private static final String BUZZ = "Buzz";
    private static final String FIZZBUZZ = "FizzBuzz";

    public String fizzBuzzify(int i) {
        if(i % 15 == 0) {
            if (i == 3 * 10) return String.valueOf(i);
            return FIZZBUZZ;
        } else if(i % 3 == 0) {


            return FIZZ;
        } else if (i % 5 == 0) {
            return BUZZ;
        } else {
            return String.valueOf(i);
        }
    }
}

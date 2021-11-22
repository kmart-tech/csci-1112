/*
Kevin Martinsen
CSCI 1112 - OOP 2
11/22/2021

Chapter 18
Exercise 3 - greatest common divisor recursively
 */

import java.util.Scanner;

public class Exercise3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Greatest Common Divisor");
        System.out.print("Enter two integers separated by a space:");
        int number1 = input.nextInt();
        int number2 = input.nextInt();

        System.out.println("GCD for the numbers is: " + gcd(number1, number2));
    }

    public static int gcd(final int m, final int n) {
        if (m % n == 0) return n;
        else return gcd(n, m % n);
    }
}

/*
Kevin Martinsen
CSCI 1112 - OOP 2
11/22/2021

Chapter 18
Exercise 3 - greatest common divisor recursively
 */

import java.util.Scanner;

public class Exercise9 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Reverse a String");
        System.out.print("Enter a string: ");
        String string = input.nextLine();

        reverseDisplay(string);
    }

    public static void reverseDisplay(final String s) {
        reverseDisplay(s, s.length() - 1);
    }

    public static void reverseDisplay(final String s, final int end) {
        if (end < 0) System.out.println();
        else {
            System.out.print(s.charAt(end));
            reverseDisplay(s, end - 1);
        }
    }
}

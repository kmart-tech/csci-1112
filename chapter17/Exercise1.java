/*
Kevin Martinsen
CSCI 1112 - OOP 2
11/18/2021
Chapter 17
Exercise 1 - append random integers to a text file
 */

import java.io.*;

public class Exercise1 {
    public static void main(String[] args) {
        try (
            PrintWriter output = new PrintWriter(new FileOutputStream("Exercise17_01.txt", true));
        ) {
            for (int i = 0; i < 100; i++) {
                output.print((int) (Math.random() * 1000) + " ");
            }
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}

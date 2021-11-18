/*
Kevin Martinsen
CSCI 1112 - OOP 2
11/18/2021
Chapter 17
Exercise 3 - append random integers to a data file
 */

import java.io.*;

public class Exercise3 {
    public static void main(String[] args) {
        String file = "Exercise17_03.dat";
        // append to file
        appendIntegerData(file);

        // read and sum the integers
        int sum = sumIntegerData(file);

        System.out.println("Sum of integers in " + file + ": " + sum);
    }

    private static void appendIntegerData(String file) {
        // appends integer bytes to given file
        try (
            DataOutputStream output = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(file, true)));
        ) {
            for (int i = 0; i < 100; i++) {
                output.writeInt((int) (Math.random() * 1000));
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static int sumIntegerData(String file) {
        int sum = 0;
        try (
            DataInputStream input = new DataInputStream(
                    new BufferedInputStream(new FileInputStream(file)));
        ) {
            while(true) {
                sum += input.readInt();
            }
        }
        catch (EOFException ex) {
            System.out.println("All data from file was read");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return sum;
    }
}



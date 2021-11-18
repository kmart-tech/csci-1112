/*
Kevin Martinsen
CSCI 1112 - OOP 2
11/18/2021
Chapter 17
Exercise 14 - Encrypt file
 */
import java.io.*;
import java.util.Scanner;

public class Exercise14 {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("File Encryption");
        System.out.print("Enter an input file: ");
        String inputFile = userInput.nextLine();

        System.out.print("Enter an output file: ");
        String outputFile = userInput.nextLine();

        if (inputFile.equals(outputFile)) {
            System.out.println("Files cannot have the same name.");
            System.exit(1);
        }

        try (
                DataInputStream inputStream = new DataInputStream(new FileInputStream(inputFile));
                DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(outputFile));
        ) {
            while(true) {
                outputStream.writeByte(inputStream.readByte() + 5);
            }

        }
        catch (EOFException ex) {
            System.out.println("Reached end of " + inputFile);
        }
        catch (FileNotFoundException ex) {
            System.out.println("Input file not found.");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

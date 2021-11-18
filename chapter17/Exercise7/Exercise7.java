/*
Kevin Martinsen
CSCI 1112 - OOP 2
Chapter 17
Exercise 7 - Serializable loan class
 */
import java.io.*;

public class Exercise7 {
    public static void main(String[] args) throws FileNotFoundException {
        Loan loan1 = new Loan();
        Loan loan2 = new Loan(1.8, 10, 10000);

        try (
                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("Exercise17_07.dat"));
        ) {
            output.writeObject(loan1);
            output.writeObject(loan2);
        } catch (IOException ex) {
            System.out.println("File could not be opened");
        }

        inputData("Exercise17_07.dat");
    }

    private static void inputData(String file) {
        Loan loan;
        int count = 1;
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                loan = (Loan)input.readObject();
                System.out.printf("Loan %d's balance: $%.2f\n",count++,loan.getLoanAmount());
            }
        }
        catch (EOFException ex) {
            System.out.println("Read all loan objects from file.");
        }
        catch (ClassNotFoundException ex) {
            System.out.println("Object was not a Loan instance.");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

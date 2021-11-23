/*
Kevin Martinsen
CSCI 1112 - OOP 2
11/23/2021

Exercise 19_05 - generic selection sort method
 */
import java.util.ArrayList;

public class Exercise9 {
  public static void main(String[] args) {
    ArrayList<Integer> list = new ArrayList<Integer>();
    list.add(14);
    list.add(24);
    list.add(4);
    list.add(42);
    list.add(5);
    Exercise9.<Integer>sort(list);

    System.out.println("Sorted list:");
    System.out.print(list + "\n");
  }

  public static <E extends Comparable<E>> void sort(ArrayList<E> list) {
    //selection sort
    E currentMin;
    int minIndex;
    for (int i = 0; i < list.size(); i++) {
      currentMin = list.get(i);
      minIndex = i;
      for (int j = i + 1; j < list.size(); j++) {
        if (currentMin.compareTo(list.get(j)) > 0) {
          currentMin = list.get(j);
          minIndex = j;
        }
      }
      // swap the minimum and index i element
      E temp = currentMin;
      list.set(minIndex, list.get(i));
      list.set(i, currentMin);
    }
  }
}

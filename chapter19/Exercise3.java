/*
Kevin Martinsen
CSCI 1112 - OOP 2
11/23/2021

Exercise 19_03 - generic removeDuplicates method
 */
import java.util.ArrayList;

public class Exercise3 {
  public static void main(String[] args) {
    ArrayList<Integer> list = new ArrayList<Integer>();
    list.add(14);
    list.add(24);
    list.add(14);
    list.add(42);
    list.add(25);
    
    ArrayList<Integer> newList = removeDuplicates(list);
    
    System.out.print(newList);
  }

  public static <E> ArrayList<E> removeDuplicates(final ArrayList<E> list) {
    ArrayList<E> uniqueList = new ArrayList<E>();
    uniqueList.add(list.get(0));

    // start at index 1 in list since 0 is already added
    for (int i = 1; i < list.size(); i++) {
      if (!uniqueList.contains(list.get(i))) {
        uniqueList.add(list.get(i));
      }
    }

    return uniqueList;
  }
}
/*
Kevin Martinsen
CSCI 1112 - OOP 2
11/23/2021

Exercise 19_05 - generic max method
 */
public class Exercise5 {
  public static void main(String[] args) {
    Integer[] numbers = {1, 2, 3};
    System.out.println(max(numbers));
    
    String[] words = {"red", "green", "blue"};
    System.out.println(max(words));
    
    Circle[] circles = {new Circle(3), new Circle(2.9), new Circle(5.9)};
    System.out.println(max(circles));
  }
  
  static class Circle implements Comparable<Circle> {
    double radius;
    
    public Circle(double radius) {
      this.radius = radius;
    }
    
    @Override
    public int compareTo(Circle c) {
      if (radius < c.radius) 
        return -1;
      else if (radius == c.radius)
        return 0;
      else
        return 1;
    }
    
    @Override
    public String toString() {
      return "Circle radius: " + radius;
    }
  }

  public static <E extends Comparable<E>> E max(final E[] list) {
    E maxElement = list[0];

    for (E elem: list) {
      if (elem.compareTo(maxElement) > 0) maxElement = elem;
    }

    return maxElement;
  }
}

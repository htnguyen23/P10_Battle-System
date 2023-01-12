import java.util.NoSuchElementException;

//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: BattleSystemTester.java
// Course: CS 300 Fall 2020
//
// Author: Huong Nguyen
// Email: htnguyen23@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: (identify each by name and describe how they helped)
// Online Sources: (identify each by URL and describe how it helped)
//
///////////////////////////////////////////////////////////////////////////////

public class BattleSystemTester {

  public static void main(String[] args) {
    if (testPeekBest())
      System.out.println("*** testPeekBest() FAILED ***");
    System.out.println("...");
    if (testEnqueueMoveQueue())
      System.out.println("*** testEnqueueMoveQueue() FAILED ***");
    System.out.println("...");
    if (testDequeueMoveQueue())
      System.out.println("*** testDequeueMoveQueue() FAILED ***");
    System.out.println("...");
    if (testHeapify())
      System.out.println("*** testHeapify() FAILED ***");
    System.out.println("...");
    if (testCompareTo())
      System.out.println("*** testCompareTo() FAILED ***");
    System.out.println("...");
    System.out.println("Done testing!");
  }

  /**
   * checks the correctness of the dequeue operation implemented in the MoveQueue class
   * 
   * @return true if any test cases fail, false if all passed
   */
  public static boolean testPeekBest() {
    MoveQueue queue = new MoveQueue();
    // (1) peek from an empty queue
    try {
      queue.peekBest();
      return true;
    } catch (NoSuchElementException nsee) {
      System.out.println(nsee.getMessage());
    }
    // (2) peek from queue with one element
    BattleCharacter peek;
    queue.enqueue(new BattleCharacter("Claire", new int[] {6, 8, 8, 3, 5}));
    peek = queue.peekBest();
    if (queue.isEmpty() || !peek.getName().equals("Claire")) {
      System.out.println("case 2 FAILED: " + peek.getName());
      return true;
    }
    // (3) peek from queue with multiple elements
    queue.enqueue(new BattleCharacter("Linda", new int[] {7, 4, 5, 4, 8}));
    queue.enqueue(new BattleCharacter("MaiNou", new int[] {5, 7, 2, 5, 4}));
    queue.enqueue(new BattleCharacter("Kalia", new int[] {3, 6, 5, 6, 3}));
    peek = queue.peekBest();
    if (queue.size() != 4 || !peek.getName().equals("Linda")) {
      System.out.println("case 3 FAILED: " + peek.getName());
      return true;
    }
    return false;
  }

  /**
   * checks the correctness of the enqueue operation implemented in the MoveQueue class
   * 
   * @return true if any test cases fail, false if all passed
   */
  public static boolean testEnqueueMoveQueue() {
    MoveQueue queue = new MoveQueue(2);
    BattleCharacter Claire = new BattleCharacter("Claire", new int[] {5, 8, 8, 3, 5});
    BattleCharacter Linda = new BattleCharacter("Linda", new int[] {4, 4, 5, 4, 8});
    BattleCharacter MaiNou = new BattleCharacter("MaiNou", new int[] {4, 7, 2, 5, 4});
    // (1) add invalid element to queue
    try {
      queue.enqueue(null);
      return true;
    } catch (IllegalArgumentException iae) {
      System.out.println(iae.getMessage());
    }
    // (2) add valid element to empty queue
    queue.enqueue(Claire);
    if (queue.isEmpty() || !queue.toString().equals("[ Claire(5, 5) | ]")) {
      System.out.println("case 2 FAILED: " + queue.toString());
      return true;
    }
    // (3) add element to non-empty queue
    queue.enqueue(Linda);
    if (queue.isEmpty() || !queue.toString().equals("[ Linda(6, 8) | Claire(5, 5) | ]")) {
      System.out.println("case 3 FAILED: " + queue.toString());
      return true;
    }
    // (4) add to a full queue
    try {
      queue.enqueue(MaiNou);
      return true;
    } catch (IllegalStateException ise) {
      System.out.println(ise.getMessage());
    }
    return false;
  }

  /**
   * checks the correctness of the dequeue operation implemented in the MoveQueue class
   * 
   * @return true if any test cases fail, false if all passed
   */
  public static boolean testDequeueMoveQueue() {
    MoveQueue queue = new MoveQueue();
    // (1) remove from an empty queue
    try {
      queue.dequeue();
      return true;
    } catch (NoSuchElementException nsee) {
      System.out.println(nsee.getMessage());
    }
    // (2) remove from queue with one element
    BattleCharacter removed;
    queue.enqueue(new BattleCharacter("Claire", new int[] {6, 8, 8, 3, 5}));
    removed = queue.dequeue();
    if (!queue.isEmpty() || !removed.getName().equals("Claire")) {
      System.out.println("case 2 FAILED: " + removed.getName());
      return true;
    }
    // (3) remove from queue with multiple elements
    queue.enqueue(new BattleCharacter("Linda", new int[] {7, 4, 5, 4, 8}));
    queue.enqueue(new BattleCharacter("MaiNou", new int[] {5, 7, 2, 5, 4}));
    queue.enqueue(new BattleCharacter("Kalia", new int[] {3, 6, 5, 6, 3}));
    queue.enqueue(new BattleCharacter("Piper", new int[] {6, 6, 5, 4, 7}));
    removed = queue.dequeue();
    if (queue.size() != 3 || !removed.getName().equals("Linda")
      || !queue.peekBest().getName().equals("Piper")) {
      System.out.println("case 3 FAILED: " + removed.getName());
      return true;
    }
    return false;
  }

  /**
   * checks the correctness of the heapify operation implemented in the MoveQueue class
   * 
   * @return true if any test cases fail, false if all passed
   */
  public static boolean testHeapify() {
    MoveQueue queue = new MoveQueue();
    // (1) heapify an empty queue
    try {
      queue.heapify();
    } catch (Exception e) {
      System.out.println("Error - Exception caught");
      System.out.println(e.getMessage());
      return true;
    }
    // (2) heapify a queue with one element
    queue.enqueue(new BattleCharacter("Claire", new int[] {6, 8, 8, 3, 5}));
    queue.heapify();
    if (queue.isEmpty() || !queue.toString().equals("[ Claire(1, 5) | ]")) {
      System.out.println("case 2 FAILED: " + queue.toString());
      return true;
    }
    // (3) heapify a queue with multiple elements
    queue.enqueue(new BattleCharacter("Piper", new int[] {6, 6, 5, 4, 7}));
    queue.enqueue(new BattleCharacter("MaiNou", new int[] {5, 7, 2, 5, 4}));
    queue.enqueue(new BattleCharacter("Kalia", new int[] {3, 6, 5, 6, 3}));
    if (!queue.toString().equals("[ Piper (2, 7) | Claire(1, 5) | MaiNou (3, 4) | Kalia (4, 3 | ]")) {
      System.out.println("case 3 FAILED: " + queue.toString());
      return true;
    }
    return false;
  }

  /**
   * checks the correctness of the heapify operation implemented in the MoveQueue class
   * @return
   */
  public static boolean testCompareTo() {
    BattleCharacter Claire = new BattleCharacter("Claire", new int[] {5, 8, 8, 3, 5});
    BattleCharacter Linda = new BattleCharacter("Linda", new int[] {4, 4, 5, 4, 8});
    BattleCharacter MaiNou = new BattleCharacter("MaiNou", new int[] {4, 7, 2, 5, 4});
    BattleCharacter Hanee = new BattleCharacter("Hanee", new int[] {4, 7, 5, 5, 4});
    if (Claire.compareTo(Linda) != -1) {
      System.out.println("compared smaller BattleCharacter to other BattleCharacter - should've returned 1");
      return true;
    }
    if (Linda.compareTo(Claire) != 1) {
      System.out.println("compared bigger BattleCharacter to other BattleCharacter - should've returned -1");
      return true;
    }
    if (MaiNou.compareTo(Hanee) != 1) {
      System.out.println("compared same speed BattleCharacters with other BattleCharacter having a greater id - should've return 1");
      return true;
    }
    if (Hanee.compareTo(MaiNou) != -1) {
      System.out.println("compared same speed BattleCharacters with other BattleCharacter having a smaller id - should've return -1");
      return true;
    }
    return false;
  }
}

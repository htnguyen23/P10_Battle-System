import java.util.NoSuchElementException;

//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: MoveQueue.java
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

public class MoveQueue implements PriorityQueueADT<BattleCharacter> {

  private BattleCharacter[] data; // a max-heap array of BattleCharacters; the root (greatest battle
                                  // character) is at index 0 of this array
  private int size = 0; // keeps track of the number of BattleCharacters stored in this MoveQueue

  /**
   * Constructor that builds an empty priority queue with array size or the given capacity.
   * 
   * @param capacity array size to build queue with
   * @throws IllegalArgumentException if capacity is zero or negative
   */
  public MoveQueue(int capacity) {
    this.data = new BattleCharacter[capacity];
  }

  /**
   * Constructor that builds an empty priority queue with array size 10.
   */
  public MoveQueue() {
    this.data = new BattleCharacter[10];
  }

  /**
   * Returns a String representation of the current contents of the MoveQueue in order from first to
   * last.
   * 
   * @author Michelle
   */
  @Override
  public String toString() {
    String s = ("[ ");
    for (int i = 0; i < size; i++) {
      s += (data[i].toString() + " | ");
    }
    s += ("]");
    return s;
  }


  @Override
  public boolean isEmpty() {
    if (this.size() > 0)
      return false;
    return true;
  }

  @Override
  public int size() {
    return this.size;
  }

  /**
   * Adds the given element to the priority queue in the correct position based on the natural
   * ordering of the elements.
   * 
   * @param element to be added to this queue
   * @throws IllegalArgumentException if element is null
   * @throws IllegalStateException    if this priority queue is full
   */
  @Override
  public void enqueue(BattleCharacter element) {
    if (element == null)
      throw new IllegalArgumentException("unable to add element - invalid element");
    if (this.size() == data.length)
      throw new IllegalStateException("unable to add element - queue is full");
    data[this.size()] = element;
    this.percolateUp(this.size());
    this.size++;
  }

  /**
   * Returns and removes the element at the front (aka root position) of this queue (the element
   * having the highest priority).
   * 
   * @return the removed element
   * @throws NoSuchElementException if this queue is empty
   */
  @Override
  public BattleCharacter dequeue() {
    BattleCharacter removed = null;
    if (this.isEmpty())
      throw new NoSuchElementException("unable to remove element - queue is empty");
    // remove root and insert last node into root and percolate down
    removed = data[0];
    data[0] = data[this.size() - 1];
    data[this.size()] = null;
    this.percolateDown(0);
    size--;
    return removed;
  }

  /**
   * Returns without removing the element at the front (aka root position) of this queue (the
   * element having the highest priority).
   * 
   * @return the element with the highest priority in this queue
   * @throws NoSuchElementException if this queue is empty
   */
  @Override
  public BattleCharacter peekBest() {
    BattleCharacter peek = null;
    if (this.isEmpty())
      throw new NoSuchElementException("unable to peek element - queue is empty");
    peek = data[0];
    return peek;
  }

  /**
   * Removes all the elements from this priority queue. The queue must be empty after this method
   * returns.
   */
  @Override
  public void clear() {
    for (int i = 0; i < this.size(); i++) {
      data[i] = null;
    }
  }

  /**
   * Recursively propagates max-heap order violations up. Checks to see if the current node i
   * violates the max-heap order property by checking its parent. If it does, swap them and continue
   * to ensure the heap condition is satisfied.
   * 
   * @param i index of the current node in this heap
   */
  protected void percolateUp(int i) {
    int parenti;
    BattleCharacter temp;
    while (i > 0) {
      parenti = (i - 1) / 2;
      // base case: child node is smaller than parent node
      if (data[i].compareTo(data[parenti]) == -1) // if current node is smaller than parent node
        return;
      // recursive case: child node is bigger than parent node
      else {
        temp = data[parenti];
        data[parenti] = data[i];
        data[i] = temp;
        percolateUp(parenti);
      }
    }
  }

  /**
   * Recursively propagates max-heap order violations down. Checks to see if the current node i
   * violates the max-heap order property by checking its children. If it does, swap it with the
   * optimal child and continue to ensure the heap condition is met.
   * 
   * @param i index of the current node in this heap
   */
  protected void percolateDown(int i) {
    int childi = 2 * i + 1;
    BattleCharacter currNode = data[i];
    BattleCharacter maxNode;
    int maxi;
    BattleCharacter temp;
    while (childi < this.size()) {
      // find the max among the node and all the node's children
      maxNode = currNode;
      maxi = -1;
      for (int x = 0; x < 2 && x + childi < this.size(); x++) {
        if (data[x + childi].compareTo(maxNode) == 1) { // if child node is greater than current
                                                        // node
          maxNode = data[x + childi];
          maxi = x + childi;
        }
      }
      // base case: current (parent) node is bigger than its child nodes (equal to max)
      if (maxNode.getSpeed() == currNode.getSpeed()) {
        return;
      }
      // recursive case: current (parent) node is smaller than its child nodes
      else {
        temp = data[i];
        data[i] = data[maxi];
        data[maxi] = temp;
        percolateDown(maxi);
      }
    }
  }

  /**
   * Eliminates all heap order violations from the heap data array
   */
  protected void heapify() {
    if (this.size() == 0 || data.length == 0) {
      return;
    } else {
      // heap sort
      BattleCharacter[] temp = new BattleCharacter[data.length];
      for (int i = 0; i < data.length; i++) {
        temp[i] = this.dequeue();
      }
      // deep copy of sorted array for data
      for (int i = 0; i < temp.length; i++) {
        data[i] = temp[i];
      }
    }
  }

}

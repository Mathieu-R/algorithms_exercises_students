package fundamentals;

import java.util.*;

/**
 * Author Pierre Schaus
 *
 * We are interested in the implementation of a circular simply linked list,
 * i.e. a list for which the last position of the list refers, as the next
 * position,
 * to the first position of the list.
 *
 * The addition of a new element (enqueue method) is done at the end of the list
 * and
 * the removal (remove method) is done at a particular index of the list.
 *
 * A (single) reference to the end of the list (last) is necessary to perform
 * all operations on this queue.
 *
 * You are therefore asked to implement this circular simply linked list by
 * completing the class see (TODO's)
 * Most important methods are:
 *
 * - the enqueue to add an element;
 * - the remove method [The exception IndexOutOfBoundsException is thrown when
 * the index value is not between 0 and size()-1];
 * - the iterator (ListIterator) used to browse the list in FIFO.
 *
 * @param <Item>
 */
public class CircularLinkedList<Item> implements Iterable<Item> {

  private long nOp = 0; // count the number of operations
  private int n; // size of the stack
  private Node last; // trailer of the list

  // helper linked list class
  private class Node {
    private Item item;
    private Node next;
  }

  public CircularLinkedList() {
    n = 0;
  }

  public boolean isEmpty() {
    return n == 0;
  }

  public int size() {
    return n;
  }

  private long nOp() {
    return nOp;
  }

  /**
   * Append an item at the end of the list
   * 
   * @param item the item to append
   */
  public void enqueue(Item item) {
    // creating a new node
    Node node = new Node();
    node.item = item;

    // adding the first element of the list
    if (last == null) {
      last = node;
      last.next = last;
    } else {
      // saving old link to first node
      Node firstNode = last.next;
      // removing the old last node + rewriting the link
      Node oldLast = last;
      oldLast.next = node;
      // setting the new last node + rewriting the link
      last = node;
      last.next = firstNode;
    }

    // increase size of the list
    n++;
    // increase the number of operations
    nOp++;
  }

  /**
   * Removes the element at the specified position in this list.
   * Shifts any subsequent elements to the left (subtracts one from their
   * indices).
   * Returns the element that was removed from the list.
   */
  public Item remove(int index) throws IndexOutOfBoundsException {
    if (index < 0 || index > (size() - 1)) {
      throw new IndexOutOfBoundsException("This element does not exist.");
    }

    // we start with the last node
    Node nodePtr = last;
    int indexPtr = 0;

    Node nodeToRemove = last;

    while (indexPtr < size()) {
      if (indexPtr == index) {
        // remove the node
        // currentNode.next is the element to remove as we started with "last"
        // shift the subsequent elements to the left
        nodeToRemove = nodePtr.next;
        nodePtr.next = nodePtr.next.next;
        break;
      }

      nodePtr = nodePtr.next;
      indexPtr++;
    }

    // decrease the size of the list
    n--;
    // increase the number of operations
    nOp++;

    return nodeToRemove.item;
  }

  /**
   * Returns an iterator that iterates through the items in FIFO order.
   * 
   * @return an iterator that iterates through the items in FIFO order.
   */
  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  /**
   * Implementation of an iterator that iterates through the items in FIFO order.
   * The iterator should implement a fail-fast strategy, that is
   * ConcurrentModificationException
   * is thrown whenever the list is modified while iterating on it.
   * This can be achieved by counting the number of operations (nOp) in the list
   * and
   * updating it everytime a method modifying the list is called.
   * Whenever it gets the next value (i.e. using next() method), and if it finds
   * that the
   * nOp has been modified after this iterator has been created, it throws
   * ConcurrentModificationException.
   */
  private class ListIterator implements Iterator<Item> {
    private long currentnOp;
    private Node nodePtr;
    private int indexPtr;

    private ListIterator() {
      // keep track of nOp when we start iterating
      currentnOp = nOp();
      nodePtr = last;
      indexPtr = size() - 1;
    }

    @Override
    public boolean hasNext() {
      return indexPtr >= 0;
    }

    @Override
    public Item next() throws ConcurrentModificationException {
      // fail-fast strategy
      if (currentnOp != nOp()) {
        // list has been modified as we iterate
        throw new ConcurrentModificationException("Circular Linked List has been modified while iterating on it.");
      }

      Node nextNode = nodePtr.next;

      nodePtr = nextNode;
      indexPtr--;

      return nextNode.item;
    }

    @Override
    public void remove() throws UnsupportedOperationException {
      // we do not implement the remove() method
      throw new UnsupportedOperationException();
    }
  }
}
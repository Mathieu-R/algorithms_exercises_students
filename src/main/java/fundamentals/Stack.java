package fundamentals;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Author: Pierre Schaus
 *
 * You have to implement the interface using
 * - a simple linkedList as internal structure
 * - a growing array as internal structure
 */
public interface Stack<E> {

  /**
   * Looks at the object at the top of this stack
   * without removing it from the stack
   */
  public boolean empty();

  /**
   * Returns the first element of the stack, without removing it from the stack
   *
   * @throws EmptyStackException if the stack is empty
   */
  public E peek() throws EmptyStackException;

  /**
   * Remove the first element of the stack and returns it
   *
   * @throws EmptyStackException if the stack is empty
   */
  public E pop() throws EmptyStackException;

  /**
   * Adds an element to the stack
   *
   * @param item the item to add
   */
  public void push(E item);

}

/**
 * Implement the Stack interface above using a simple linked list.
 * You should have at least one constructor without argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
class LinkedStack<E> implements Stack<E> {

  private Node<E> top;        // the node on the top of the stack
  private int size;        // size of the stack

  // helper linked list class
  private class Node<E> {
    private E item;
    private Node<E> next;

    public Node(E element, Node<E> next) {
      this.item = element;
      this.next = next;
    }
  }

  public LinkedStack() {
    this.top = null;
    this.size = 0;
  }

  @Override
  public boolean empty() {
    return size == 0;
  }

  @Override
  public E peek() throws EmptyStackException {
    if (empty()) {
      throw new EmptyStackException();
    }

    return top.item;
  }

  @Override
  public E pop() throws EmptyStackException {
    if (empty()) {
      throw new EmptyStackException();
    }

    Node<E> oldTop = top;
    top = top.next;
    size--;

    return oldTop.item;
  }

  @Override
  public void push(E item) {
    Node<E> newNode;

    if (top == null) {
      newNode = new Node(item, null);
    } else {
      Node<E> oldTop = top;
      newNode = new Node(item, oldTop);
    }

    top = newNode;
    size++;
  }
}


/**
 * Implement the Stack interface above using an array as internal representation
 * The capacity of the array should double when the number of elements exceed its length.
 * You should have at least one constructor without argument.
 * You are not allowed to use classes from java.util
 * @param <E>
 */
class ArrayStack<E> implements Stack<E> {

  private E[] array;        // array storing the elements on the stack
  private int size;        // size of the stack

  public ArrayStack() {
    array = (E[]) new Object[10];
    size = 0;
  }

  @Override
  public boolean empty() {
    return size == 0;
  }

  @Override
  public E peek() throws EmptyStackException {
    if (empty()) {
      throw new EmptyStackException();
    }

    return array[size - 1];
  }

  @Override
  public E pop() throws EmptyStackException {
    if (empty()) {
      throw new EmptyStackException();
    }

    if (size <= array.length / 4) {
      array = resize(array, array.length / 2);
    }

    E itemToRemove = array[size - 1];
    array[size - 1] = null;
    size--;

    return itemToRemove;
  }

  @Override
  public void push(E item) {
    // resize if we exceed capacity of the array
    if (size >= array.length) {
      array = resize(array, 2 * array.length);
    }

    array[size] = item;
    size++;
  }

  public E[] resize(E[] arr, int length) {
    return Arrays.copyOf(arr, length);
  }
}


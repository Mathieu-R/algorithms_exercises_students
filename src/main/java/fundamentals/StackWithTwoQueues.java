package fundamentals;

import java.util.ArrayDeque;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Author: Pierre Schaus and Auguste Burlats
 * Implement the abstract data type stack using two queues
 * You are not allowed to modify or add the instance variables,
 * only the body of the methods
 */
public class StackWithTwoQueues<E> {

  Queue<E> queue1;
  Queue<E> queue2;

  public StackWithTwoQueues() {
    queue1 = new ArrayDeque<>();
    queue2 = new ArrayDeque<>();
  }

  /**
   * Looks at the object at the top of this stack
   * without removing it from the stack
   */
  public boolean empty() {
    return queue1.isEmpty();
  }

  /**
   * Returns the first element of the stack, without removing it from the stack
   *
   * @throws EmptyStackException if the stack is empty
   */
  public E peek() throws EmptyStackException {
    // throw an exception if queue1 is empty (which means the stack is empty)
    if (queue1.size() == 0) {
      throw new EmptyStackException();
    }

    // remove all elements of queue1 except the last one
    // in FIFO order and push them in queue2
    for (int i = 0; i < queue1.size() - 1; i++) {
      queue2.offer(queue1.remove());
    }

    // check the last element added
    E tail = queue1.peek();

    // reset the queue
    queue2 = new ArrayDeque<>();

    return tail;
  }

  /**
   * Remove the first element of the stack and returns it
   *
   * @throws EmptyStackException if the stack is empty
   */
  public E pop() throws EmptyStackException {
    // throw an exception if queue1 is empty (which means the stack is empty)
    if (queue1.size() == 0) {
      throw new EmptyStackException();
    }

    // remove all elements of queue1 except the last one
    // in FIFO order and push them in queue2
    for (int i = 0; i < queue1.size() - 1; i++) {
      queue2.offer(queue1.remove());
    }

    // remove the last element added
    E tail = queue1.remove();
    System.out.println(tail);

    // swap the queues
    // not sure if it works because of reference
    Queue<E> temp = queue2;
    queue1 = temp;
    queue2 = new ArrayDeque<>();

    return tail;
  }

  /**
   * Adds an element to the stack
   *
   * @param item the item to add
   */
  public void push(E item) {
    queue1.offer(item);
  }

}

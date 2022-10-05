package others;

import java.util.*;

public class PostFix {
  private String postFixString;

  public PostFix(String postFixString) {
    this.postFixString = postFixString;
  }

  public int evaluate() {
    Stack<Integer> valueStack = new Stack<>();
    StringTokenizer tokenizer = new StringTokenizer(postFixString);

    while (tokenizer.hasMoreTokens()) {
      String element = tokenizer.nextToken();

      // push values onto a stack
      if (!(element.equals("+") || element.equals("*"))) {
        int value = Integer.parseInt(element);
        valueStack.push(value);
      } else {
        // if it is an operator, we apply it on the values in the stack
        // the way post-fix notation is made ensure there will be at least 2 elements on the stack
        int value1 = valueStack.pop();
        int value2 = valueStack.pop();
        int result;

        if (element.equals("+")) {
          result = value1 + value2;
        } else {
          result = value1 * value2;
        }

        // push the result on the stack
        valueStack.push(result);
      }
    }

    return valueStack.pop();
  }

  public static void main(String[] args) {
    PostFix pf = new PostFix("4 20 + 3 5 1 * * +");
    int result = pf.evaluate();
    System.out.println(result);
  }
}
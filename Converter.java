package hw2;

import java.util.*;

public class Converter {
    private List<String> tokens;

    public Converter(String infixExpression) {
        this.tokens = ParserHelper.parse(infixExpression.toCharArray());
    }

    public String toPostFix() {
        ArrayStack<String> stack = new ArrayStack<>();
        StringBuilder postfix = new StringBuilder();

        for (String token : tokens) {
            if (isOperand(token)) {
                postfix.append(token).append(" ");
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && hasEqualOrHigherPrecedence(peekStack(stack), token)) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !peekStack(stack).equals("(")) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.pop();  // it removes the open parenthesis
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(" ");
        }

        return postfix.toString().trim();
    }

    private boolean isOperand(String token) {
        return token.matches("\\d+");
    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^");
    }

    // I have to do adjusted precedence check to correctly handle operators of equal precedence
    private boolean hasEqualOrHigherPrecedence(String stackTop, String current) {
        if (stackTop.equals("^") && !current.equals("^")) {
            return true;
        } else if ((stackTop.equals("*") || stackTop.equals("/")) && (current.equals("+") || current.equals("-") || current.equals("*") || current.equals("/"))) {
            return true;
        } else if ((stackTop.equals("+") || stackTop.equals("-")) && (current.equals("+") || current.equals("-"))) {
            return true;
        }
        return false;
    }

    //  method to simulate the peek operation
    private String peekStack(ArrayStack<String> stack) {
        String top = stack.pop();
        stack.push(top);
        return top;
    }
}
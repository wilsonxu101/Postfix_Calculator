package hw2;

public class PostfixCalculator {

    public static double evaluate(String postfixExpression) {
        ArrayStack<Double> stack = new ArrayStack<>();
        String[] tokens = postfixExpression.split(" ");

        for (String token : tokens) {
            if (isOperand(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(operand1 + operand2);
                        break;
                    case "-":
                        stack.push(operand1 - operand2);
                        break;
                    case "*":
                        stack.push(operand1 * operand2);
                        break;
                    case "/":
                        stack.push(operand1 / operand2);
                        break;
                    case "^":
                        stack.push(Math.pow(operand1, operand2));
                        break;
                }
            }
        }

        return stack.pop();
    }

    private static boolean isOperand(String token) {
        return token.matches("\\d+(\\.\\d+)?");  //  integers and floating point numbers
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^");
    }
}
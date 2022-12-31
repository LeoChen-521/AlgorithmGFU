import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;


/**
 * A class that evaluates arithmetic expressions.
 */
public class Compute
{
   private static final String ADD_OPERATOR = "+";
   private static final String SUB_OPERATOR = "-";
   private static final String MUL_OPERATOR = "*";
   private static final String DIV_OPERATOR = "/";
   private static final String LEFT_PARENTHESIS = "(";
   private static final String RIGHT_PARENTHESIS = ")";
   private static final String EXP_OPERATOR = "^";
   
   // A list of all the operators.
   private static final List<String> OPERATORS = Arrays.asList(ADD_OPERATOR,
         SUB_OPERATOR, MUL_OPERATOR, DIV_OPERATOR, EXP_OPERATOR, 
         LEFT_PARENTHESIS, RIGHT_PARENTHESIS);
   // A array with int type elements to determine the precedence of each 
   // operator without considering left and right parenthesis.
   private static final int[] PRECEDENCE = {1, 1, 2, 2, 3};
   
   
   /**
    * A method that convert an String type infix expression to a list of 
    * postfix tokens.
    * 
    * @param infixExpression represents a String type infix expression.
    * @return a list of postfix tokens.
    * @throws ArithmeticException if infix expression is not valid.             
    */
   private static List<String> inFixToPostFix(String infixExpression) 
                                  throws ArithmeticException
   {
      // Tokenize the input by splitting the input by whitespace.
      String[] infixTokens = infixExpression.split(" ");
      
      // Throw ArithmeticException when infix expression is not valid.
      if (infixTokens.length < 1 
            || !RIGHT_PARENTHESIS.equals(infixTokens[infixTokens.length - 1])
            && !isOperand(infixTokens[infixTokens.length - 1]))
      {
         throw new ArithmeticException("Missing operantor or operand");
      }
      
      // Local variables.
      List<String> postfixTokens = new ArrayList<>();
      Deque<String> operatorStack = new ArrayDeque<>();
      String previousToken = null;
      int parthesisCount = 0;
      
      // For each token in the infix expression, when we meet an operand, add
      // it to postfixTokens; when we meet a left parenthesis, push it to the
      // operatorStack directly; when we meet a right parenthesis, pop out all
      // the operators in the operatorStack till the left parenthesis is found.
      // If we meet the operators other than parenthesis, use an algorithm to
      // determine the action.
      for (String currentToken : infixTokens)
      {
         // Check if the token is in incorrect order.
         if (!isInOrder(currentToken, previousToken))
         {
            throw new ArithmeticException("Missing operantor or operand");
         }
         
         // Left parenthesis situation.
         if (currentToken.equals(LEFT_PARENTHESIS))
         {
            operatorStack.push(currentToken);
            parthesisCount++;
         }
         
         // Right parenthesis situation.
         else if (currentToken.equals(RIGHT_PARENTHESIS))
         {
            if (parthesisCount <= 0)
            {
               throw new ArithmeticException("Missing operantor or operand");
            }
            
            // Pop off all the operators till the left parenthesis is found.
            while (!operatorStack.peek().equals(LEFT_PARENTHESIS))
            {
               postfixTokens.add(operatorStack.pop());
            }
            // Pop off the left parenthesis.
            operatorStack.pop();
            parthesisCount--;
         }
         
         // Operand situation.
         else if (isOperand(currentToken))
         {
            postfixTokens.add(currentToken);
         }
         
         // Other operators situations.
         else
         {
            // Pop off all the operators whose precedence are higher or equal
            // to current token.
            while (!operatorStack.isEmpty()
                  && isNotSmallerPrecedence(operatorStack.peek(), currentToken))
            {
               postfixTokens.add(operatorStack.pop());
            }
            // Push current token to operatorStack.
            operatorStack.push(currentToken);
         }
         // Point previous token to current token.
         previousToken = currentToken;
      }
      
      // Check the left and right parenthesis are matched(paired well).
      if (parthesisCount != 0)
      {
         throw new ArithmeticException("Missing operantor or operand");
      }
      
      // Pop off all the remaining operators.
      while (!operatorStack.isEmpty())
      {
         postfixTokens.add(operatorStack.pop());
      }
      return postfixTokens;
   }
   
   
   /**
    * A method that compares two tokens precedence.
    * @param tokenInStack
    *                represents the tokens already in operatorStack.
    * @param currentToken
    *                represents the tokens ready to compare with the token
    *                in the operatorStack.
    * @return true if token in operatorStack has higher or same precedence.
    */
   private static boolean isNotSmallerPrecedence
                                 (String tokenInStack, String currentToken)
   {
      return tokenInStack.equals(LEFT_PARENTHESIS) ? false : 
         PRECEDENCE[OPERATORS.indexOf(tokenInStack)] >= 
         PRECEDENCE[OPERATORS.indexOf(currentToken)];
   }
   
   
   /**
    * A method to determine whether a token is an operand or not.
    * @param token
    *             represents the String to check.
    * @return true if the token is an operand.
    */
   private static boolean isOperand(String token)
   {
      if (token != null && OPERATORS.indexOf(token) == -1)
      {
         // Check if the token is a numeric String.
         try
         {
            Integer.parseInt(token);
         }
         // Throw NumberFormatException, if the token is not a numeric String.
         catch(NumberFormatException e)
         {
            throw new NumberFormatException("Non-computable number found");
         }
      }
      // true if token is not listed in the OPERATOR.
      return  token != null && OPERATORS.indexOf(token) == -1;
   }
   
   
   /**
    * A method to compute the postfix tokens list.
    * @param postfixTokens
    *                represents the postfix tokens list.
    * @return  result of the expression.
    */
   private static int computePostfix(List<String> postfixTokens)
   {
      // Local variables.
      Deque<Integer> operandStack = new ArrayDeque<>();
      // For each token in the postfix tokens list, if the token is an operand,
      // push token onto operand stack, else pop 2 operands off operand stack
      // compute operation, then push result onto operand stack.
      for (String currentToken : postfixTokens)
      {
         // The situation when the current token is an operand.
         if (isOperand(currentToken))
         {
            operandStack.push(Integer.parseInt(currentToken));
         }
         // the situation when the current token is an operator.
         else
         {
            int operandRight = operandStack.pop();
            int operandLeft = operandStack.pop();
            
            // Switch statement base on the type of operator of current token.
            switch (currentToken)
            { 
               case ADD_OPERATOR:
                  operandStack.push(operandLeft + operandRight);
                  break;
               case SUB_OPERATOR:
                  operandStack.push(operandLeft - operandRight);
                  break;
               case MUL_OPERATOR:
                  operandStack.push(operandLeft * operandRight);
                  break;
               case DIV_OPERATOR:
                  operandStack.push(operandLeft / operandRight);
                  break;
               case EXP_OPERATOR:
                  operandStack.push((int) Math.pow(operandLeft, operandRight));
                  break;
            }
         }
      }
      // Return top of operand stack.
      return operandStack.pop();
   }
   
   
   /**
    * A method to determine whether two tokens are in legal order or not.
    * @param currentToken
    *                represents the current token we are checking in illegal 
    *                order or not.
    * @param previousToken
    *                the token before current token.
    * @return true if these two tokens are in legal order.
    */
   private static boolean isInOrder(String currentToken, String previousToken)
   {
      boolean isInOrder = false;
      
      // When the previous tokens is a right parenthesis or an operand,
      // true if the following token is an operator other than left 
      // parenthesis.
      if (RIGHT_PARENTHESIS.equals(previousToken) || isOperand(previousToken))
      {
         isInOrder = !LEFT_PARENTHESIS.equals(currentToken)
               && !isOperand(currentToken);
      }
      // Otherwise, true if the following tokens is an operand or left 
      // parenthesis.
      else
      {
         isInOrder = LEFT_PARENTHESIS.equals(currentToken)
               || isOperand(currentToken);
      }
      return isInOrder;
   }
   
   
   /**
    * Evaluates and prints the compute result.
    * @param args 
    *          represents the infix expression.
    */
   public static void main(String[] args)
   {
      // There should be 1 and only 1 expression from the command line.
      if (args.length != 1)
      {
         System.err.println("Error: Please provide one expression.");
         System.exit(1);
      }
      try
      {
         System.out.println(computePostfix(inFixToPostFix(args[0])));
      }
      catch(ArithmeticException | NumberFormatException e)
      {
         System.err.println("Error: " + e.getMessage());
         System.exit(1);
      }
   } 
}

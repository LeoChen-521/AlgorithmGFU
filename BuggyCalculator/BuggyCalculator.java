import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * 
 * A not-very-OOP command-line calculator
 *
 * @author David M. Hansen
 * @version 3.0
 */
public class BuggyCalculator {
   private static final String ADD_OP = "+";
   private static final String SUB_OP = "-";
   private static final String MUL_OP = "*";
   private static final String DIV_OP = "/";
   private static final String EXP_OP = "^";
   private static final String LEFT_PAREN = "(";
   private static final String RIGHT_PAREN = ")";
   
   // Modified by <Xiaoru Chen> on <11/9/2016>
   // Add a bitwise-and operator.
   private static final String BITWISE_AND = "&";
   // List of all the operators and a matching "parallel array" of
   // precedence (not the best idea; OOP or an enum would be better...)
   private static final List<String> OPERATORS = Arrays.asList(
         ADD_OP, SUB_OP, MUL_OP, DIV_OP, EXP_OP, LEFT_PAREN, RIGHT_PAREN,
         BITWISE_AND);
   // Corresponds to OPERATORS list
   private static final List<Integer> PRECEDENCE = Arrays.asList(
         0, 0, 1, 1, 2, -1, -1, -2); 


   /**
    * Convert an infix sequence of tokens to a postfix sequence
    *
    * @param inputSequence a string of tokens in infix notation
    *
    * @return the postfix sequence of tokens as a List
    *
    * @throws ArithmeticException if sequence is not a valid equation
    */
   private static List<String> convertInfixToPostfix(String infixString) 
            throws ArithmeticException {
      String previousToken = null;
      String currentToken;
      Deque<String> operatorStack = new ArrayDeque<String>();
      List<String> postfixSequence = new ArrayList<String>();
      Scanner inputSequence = new Scanner(infixString);

      // For each token in the infix sequence, we consider four cases:
      // 1) if it's a left-parenthesis we stack it on the operator stack. 
      // 2) If it's a right-parenthesis we move operators from
      // the operator stack to the postfix sequence until we find the
      // matching left-parenthesis. 
      // 3) If it's an operator, we pop operators from operator stack 
      // to the postfix stack until we find an operator of greater precedence.
      // 4) if it's an operand it goes to the postfix sequence. 
      while (inputSequence.hasNext()) {
         currentToken = inputSequence.next();

         // First make sure the current token can follow the previous
         // token
         if (!canFollow(currentToken, previousToken)) {
            throw new ArithmeticException("Illegal Equation");
         }

         // Determine what to do with the current token based on its
         // type
         switch (currentToken) {
            case LEFT_PAREN:
               // Left parenthesis are pushed onto operator stack
               operatorStack.push(currentToken);
               
            case RIGHT_PAREN:
               // Right-parens search for a matching left-paren and move
               // operators to the postfix sequence until we empty the
               // find a matching left-paren
               while (!operatorStack.peek().equals(LEFT_PAREN)) {
                  postfixSequence.add(operatorStack.pop());
               }
               
               // Modified by <Xiaoru Chen> on <11/9/2016>
               // Should also pop out the left parenthesis in the operator
               // stack after poping out the operator(s) between right and left
               // parenthesis. Because we do not need the left parenthesis in
               // the operator stack any more.
               operatorStack.pop();
               
               break;

            case ADD_OP:
            case SUB_OP:
            case MUL_OP:
            case DIV_OP:
            case EXP_OP:
            
            // Modified by <Xiaoru Chen> on <11/9/2016>
            // Treat bitwise-and operator as the same way.
            case BITWISE_AND:
               
               // For all operators, move any operators of greater or
               // equal precedence from the operator stack to the postfix
               // sequence, then push this operator
               while (!operatorStack.isEmpty() 
                      && precedenceIsGreaterThan(operatorStack.peek(),
                                                 currentToken)) {
                  postfixSequence.add(operatorStack.pop());
               }
               operatorStack.push(currentToken);
               break;

            default: 
               // Operands are appended to the postfix sequence
               postfixSequence.add(currentToken);

         } // switch

         previousToken = currentToken;

      } // for

      
      // Modified by <Xiaoru Chen> on <11/9/2016>
      // When the operator stack still has operator left, pop them out and add
      // them to the postfixSequence.
      while (!operatorStack.isEmpty())
      {
         postfixSequence.add(operatorStack.pop());
      }
      
      return postfixSequence;

   } // convertInfixToPostfix


   /**
    * Compute the answer to an arithmetic equation in postfix notation
    *
    * @param postfixSequence an equation in postfix notation
    *
    * @return computed result
    *
    * @throws ArithmeticException if postfix equation is invalid
    */
   static int computeEquation(List<String> postfixSequence) 
           throws ArithmeticException, NoSuchElementException{
      Deque<Integer> operandStack = new ArrayDeque<Integer>();
      int leftOperand;
      int rightOperand;

      // Iterate over the postfix sequence. 
      // If it's an operator, compute the required operation using the top two 
      // values of the operand stack and push the result onto the stack.
      // If it's an operand, convert it and push it onto the operand stack. 
      for (String currentToken : postfixSequence) {
         if (isOperator(currentToken)) { 
            // Operator - pop two operands and perform the operation
            
            // Modified by <Xiaoru Chen> on <11/9/2016>
            // Handling the error when operand is missing.
            // If the operand stack is empty, cannot pop out any operands.
            if (operandStack.isEmpty())
            {
               throw new NoSuchElementException("Missing operand");
            }
            
            // Modified by <Xiaoru Chen> on <11/9/2016>
            // Should make the top operand in the stack to be the right 
            // operand. Because the operand stack is first in last out.
            rightOperand = operandStack.pop();
            
            // Modified by <Xiaoru Chen> on <11/9/2016>
            // Handling the error when operand is missing.
            // If the operand stack is empty, cannot pop out any operands.
            
            if (operandStack.isEmpty())
            {
               throw new NoSuchElementException("Missing operand");
            }
            
            // Modified by <Xiaoru Chen> on <11/9/2016>
            // Should make the second top operand in the stack to be the left 
            // operand. Because the operand stack is first in last out.
            leftOperand = operandStack.pop();
            
            switch (currentToken) {
               case ADD_OP:
                  operandStack.push(leftOperand + rightOperand);
                  break;

               case SUB_OP:
                  operandStack.push(leftOperand - rightOperand);
                  break;

               case MUL_OP:
                  operandStack.push(leftOperand * rightOperand);
                  break;

               case DIV_OP:
                  operandStack.push(leftOperand / rightOperand);
                  break;

               case EXP_OP:
                  operandStack.push(
                           (int) Math.pow(leftOperand, rightOperand));
                  break;
                  
               case BITWISE_AND:
                  operandStack.push(leftOperand & rightOperand);
                  break;
               
               default: // An unknown operator
                  throw new ArithmeticException
                  ("Unknown operator " + currentToken);
            } // switch
         } 
         // Otherwise we assume it's an Operand. Attempt to convert it
         // and push onto operand stack
         
         else {
            
            // Modified by <Xiaoru Chen> on <11/9/2016>
            // Should check whether the currentToken is an legal operand or not.
            // If it is, the try will execute fully.
            try
            {
               operandStack.push(Integer.parseInt(currentToken));
            }
            catch (NumberFormatException e)
            {
               throw new NumberFormatException("Illegal operand");
            }
         }
         

      } // for

      // There should be an operand left on the stack - the answer;
      if (operandStack.isEmpty()) { 
         throw new ArithmeticException("Badly Formed Equation");
      }

      // It's all good, return the answer
      return operandStack.pop();

   } // computeEquation


   /**
    * Determine whether the current token can follow the previous token
    *
    * @param currentToken an equation token 
    * @param previousToken a token preceeding currentToken 
    *
    * @return true if currentToken can follow previousToken
    */
   private static boolean canFollow(String currentToken, 
                                    String previousToken) {
      boolean canFollow = false;

      // Operators and right-parenthesis
      if (isOperator(currentToken) || currentToken.equals(RIGHT_PAREN)) {
         // Operators and right parenthesis can not appear at the start, 
         // follow operators, or left parenthesis
         canFollow = previousToken != null
                     && !isOperator(previousToken) 
                     && !previousToken.equals(LEFT_PAREN);
      }
      else { 
         // Operands and left parenthesis can appear at the beginning, 
         // follow left parenthesis, or operators
         canFollow = previousToken == null 
                     || previousToken.equals(LEFT_PAREN) 
                     || isOperator(previousToken);
      }

      return canFollow;

   } // canFollow


   /**
    * Determine the precedence of two operators
    *
    * @param operator1 an operator in the OPERATORS list
    * @param operator2 an operator in the OPERATORS list
    *
    * @return true if operator1 has higher precedence than operator2
    */
   private static boolean precedenceIsGreaterThan(String operator1, String operator2) {
      // Get the precedence of the operators by looking them up in the
      // list of operators and using that location to get the precedence
      // value in the parallel precedence array
      
      // Modified by <Xiaoru Chen> on <11/9/2016>
      // Even though the two operators are equivalent, still pop out the top
      // operator in the stack (and then push the current operator into the
      // stack.
      return (PRECEDENCE.get(OPERATORS.indexOf(operator1)) 
                 >= PRECEDENCE.get(OPERATORS.indexOf(operator2))
              );
   } // precdenceIsGreaterThan

   
   /**
    * Determine whether the token is an operator
    *
    * @param token to check as an operator
    *
    * @return true if the token is an operator (excluding parenthesis)
    */
   private static boolean isOperator(String token) {
      // Operators are anything found in the set of OPERATORS that are
      // not parenthesis
      return (OPERATORS.indexOf(token) >= 0 
                 && !token.equals(RIGHT_PAREN)
                 && !token.equals(LEFT_PAREN)
              );

   } // isOperator


   /**
    * Evaluates and prints the result of the equation
    *
    * @param args Equation
    */
   public static void main(String[] args) {
      try {
         // Convert the infix expression given to us as a parameter to a
         // postfix expression, compute the result, and print it out
         System.out.println(computeEquation(convertInfixToPostfix(args[0])));
      }
      
      // Modified by <Xiaoru Chen> on <11/9/2016>
      // Should also handling all the other potential errors.
      catch (ArithmeticException 
            | NoSuchElementException | NumberFormatException e) { 
         // Syntax error when trying to compute
         System.err.println("ERROR - invalid equation: "+e.getMessage());
         System.exit(1);
      }

   } // Main

} // BuggyCalculator
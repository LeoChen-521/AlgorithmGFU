import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class can be used to do simple calculations with very large non-negative 
 * "integers." 
 */
public class BigInt 
{
	//Instance variable.
	private int _digits[];

	
	/**
	 * A constructor that takes a string representation of a number. 
	 * Note that the string may contain leading zeros that can be discarded; 
	 * however, remember that 0 is a number as well.
	 * @param value
	 * 			represents the user's input numbers.
	 */
	public BigInt(String value) 
	{
		char[] digitsAsCharArray = value.toCharArray();
		int cursor = 0;
		
		//Determine whether the input format is legal or not and skip the 
		//leading zeros.
		while(cursor < digitsAsCharArray.length) 
		{   
		   //Use the ASCII table to determine if the digits as Char in the 
		   //array are number from 0 to 9 or not. If the heading character we 
		   //get is not a number, then we throw an exception: "Input Error."
		   if(digitsAsCharArray[cursor] - '0' < 0 
		            || digitsAsCharArray[cursor] - '0' > 9)
		   {
		      throw new NumberFormatException("Input Error");
		   }
		   // If the heading characters we get are zeros, then we skip those 
		   //heading zeros.
		   else if(digitsAsCharArray[cursor] - '0' == 0)
		   {
		      cursor++;
		   }
		   // When we get the first non-zero number, we use that number as our 
		   //first digit.
		   else
		   {
		      break;
		   }
		}
		
		//Determine if the input is 0 or a lot of 0s, then we use 0 to be as our 
		//input number.
		if(cursor == digitsAsCharArray.length)
		{
		   _digits = new int[] {0};
		}
		
		//Get the length of the array after getting rid of the heading zeros.
		else
		{
		   _digits = new int[digitsAsCharArray.length - cursor];
		}
		
		//Make the first int type element of digits get equal to the #cursor 
		//element, which is Char type of digitAsCharArray minus the Char type 
		//'0', to make the int type element of the digits become one of the
		//decimal number from 0 to 9.
		for(int i = 0; cursor < digitsAsCharArray.length; cursor++, i++) 
		{
		   _digits[i] =  digitsAsCharArray[cursor] - '0';
		}

	}
	
	
	/**
	 * 
	 * @param value
	 */
	public BigInt(long value) 
	{
		
		long currentValue = value;
		long rest;
		ArrayList <Integer> result = new ArrayList<Integer>();
		
		while(currentValue != 0)
		{
			rest = currentValue % 10;
			currentValue = currentValue / 10;
			result.add((int) rest);
		}

		this._digits = new int[result.size()];

		for(int i =result.size()-1; i >= 0; i--)
		{
			this._digits[result.size()-1 -i] = result.get(i);
		}
	}

	
	/**
	 * This is a constructor to deal with the problem that if the sum or
	 * product result has a 0 heading because of "0" carries may go to the
	 * heading indexes of the array of the result.
	 * 
	 * @param value
	 * 			represent the sum or product result as an array with int type
	 * 			elements.
	 */
	private BigInt(int[] value)
	{
		_digits=value;
	}

	
	/**
	 * Display each digit of the results, using StringBuilder.
	 */
	public String toString() 
	{
		StringBuilder resultAsString = new StringBuilder();
		
		for(int d : _digits) 
		{
			resultAsString.append(d);
		}
		return resultAsString.toString();
	}

	
	/**
	 * This is the add method that the users can call in order to add a number
	 * to the number they have already. By using an array with int type
	 * elements to represent our sum result.
	 * 
	 * @param number
	 * 				represents the number that was added to the number the user
	 * 				already has.
	 * @return new BigInt(result)
	 * 				represents the BigInt type result.
	 */
	public BigInt add(BigInt number) 
	{
		int result[] = null;

		result = add(this._digits, number._digits);

		return new BigInt(result);
	}

	
	/**
	 * The adder to add numbers together to get a result.
	 * @param x
	 * 			the array of elements that are int type(numbers).
	 * @param y
	 * 			the array of elements that are int type(numbers).
	 * @return sumResult
	 * 			the sum of the two arrays.
	 */			
	private static int[] add(int[] x, int[] y) 
	{
		//Make the length of array x always longer than array y, by switching
		//the contents of both arrays. If array x is already longer than 
		//array y, then do not consider this if statement.
		if(x.length < y.length)
		{
			int[] temp = x;
			x = y;
			y = temp;
		}
		
		//Local variables.
		int xCursor = x.length - 1;
		int yCursor = y.length - 1;
		int sumResult[] = new int[x.length + 1];
		int sum = 0;
		int carry = 0;
		
		//Add those two int type elements and the carry from the last index to 
		//the first index. 
		for(int i = 0; i < y.length; i++, xCursor--, yCursor--) 
		{
			sum = x[xCursor] + y[yCursor] + carry;
			//Get the carry.
			carry = sum / 10;
			//Get the number that should be filled in that index.
			sum = sum % 10;
			//Fill the sum into the index of the array of the array from the 
			//last one.
			sumResult[xCursor + 1] = sum;
		}
		
		//When we consider the calculation after adding all the numbers of y.
		while(xCursor != -1)
		{
			//It does not have y[yCursor] any more. But it may have carry.
			//Then, add the #xCursor index of x to the carry, to get the sum.
			sum = x[xCursor] + carry;
			//Get the carry from the sum of carry and the #xCursor index of x.
			carry = sum / 10;
			//Get the sum that should be filled in that index.
			sum = sum % 10;
			//Fill the sum into the index of the array of the array from the 
			//last one.
			sumResult[xCursor + 1] = sum;
			//Each time we fill that number into the index of the array of the
			//result, we move to the next index, by move the Cursor approach to
			//the first index of the array of the result.
			xCursor--;
		}
		//The result may has a heading 0 or 1,
		sumResult[0] = carry;

		return sumResult;
	}

	
	/**
	 * The multiplier to multiply numbers together to get the result.
	 * @param x
	 * 			represents the array of elements that are int type(numbers).
	 * @param y
	 * 			represents the array of elements that are int type(numbers).
	 * @return
	 */
	private static int[] multiply(int[] x, int[] y) 
	{
		//Make the length of array x always longer than array y, by switching
		//the contents of both arrays. If array x is already longer than 
		//array y, then do not consider this if statement.
		if(x.length < y.length) 
		{
			int[] temp = x;
			x = y;
			y = temp;
		}
		
		//Local variables.
		int productResult[] = new int[x.length + y.length];
		//Create a two-dimension array with enough space to store each internal
		//multiply result.
		int digitResults[][] = new int[y.length][x.length + y.length];
		int product = 0;
		int carry = 0;
		int j = 0;			
		
		//Get internal multiply results for each y's digit.
		for(int i = y.length - 1; i > -1; i--) 
		{
			//Get internal multiply result for each x's digit.
			for(j = x.length - 1; j > -1; j--)
			{
				product = x[j] * y[i] + carry;
				carry = product / 10;
				product = product % 10;
				//Set multiply results to proper position in the two-dimension
				//array.
				digitResults[i][i + j + 1] = product;
			}
			//Set additional digit because we may have a carry not equal to 
			//zero.
			if(j == -1)
			{
				digitResults[i][i] = carry;
				//Reset carry.
				carry = 0;
			}
			
		}
		//Add all the internal multiply results together.
		for(int[] digitResult : digitResults)
		{
			productResult = add(productResult, digitResult);
		}
		return productResult;
	}

	
	/**
	 * This is the multiply method that the users can call in order to multiply
	 * a number to the number they have already. By using an array with int 
	 * type elements to represent our product result.
	 * 
	 * @param number
	 * 		 		represents the number that was multiplied to the number 
	 * 				the user already has.
	 * @return new BigInt(produtResult)
	 * 				represents the BigInt type result.
	 */
	public BigInt multiply(BigInt number) 
	{
    		 int[] productResult = multiply(_digits, number._digits);
		
		 return new BigInt(productResult);
	}

	/**
	 *  A simulation of BigInt Class.
	 * @param args
	 */
	public static void main(String[] args) 
	{
		StringBuilder num1 = new StringBuilder();
		StringBuilder num2 = new StringBuilder();
		StringBuilder spaces1 = new StringBuilder();
		StringBuilder spaces2 = new StringBuilder();
		StringBuilder line = new StringBuilder();
		String result = null;
		char operator = ' ';
		char[] inputChars = null;

		int indexOfOperator = 0;
		int count = 0;

		System.out.print("compute: ");
		Scanner myScanner = new Scanner(System.in);
		String input = myScanner.nextLine();
		while(!input.equals("quit")) 
		{
			inputChars = input.toCharArray();
			// Remove all white spaces of the input character array, get valid
			// characters with length of count.
			for(int i = 0; i < inputChars.length; i++) 
			{
				if(inputChars[i] != ' ') 
				{
					inputChars[count++] = inputChars[i];
				}
			}
			// Get first number and operator
			for(int i = 0; i < count; i++) 
			{
				if((inputChars[i] == '+') || (inputChars[i] == '*')) 
				{
					operator = inputChars[i];
					indexOfOperator = i;
					break;
				}
				else
				{
					num1.append(inputChars[i]);
				}
			}
			
			if(num1.length() < 1 || indexOfOperator == 0)
			{
				throw new NumberFormatException("Input Error");
			}
			
			// Get second number
			for(int i = indexOfOperator + 1; i < count; i++) 
			{
				num2.append(inputChars[i]);
			}
			
			if(num2.length() < 1)
			{
				throw new NumberFormatException("Input Error");
			}
			//Use regular expression to remove all the heading zeros.
			//Adding operation.
			if(operator == '+')
			{
				result = (new BigInt(num1.toString())).
						add(new BigInt(num2.toString())).toString();
				result = result.replaceFirst("^0+(?!$)", "");
				for(int i = 0; i < result.length() + 2; i++)
				{
					line.append("-");
				}
				for(int i = 0; i < result.length() - num1.toString().
						replaceFirst("^0+(?!$)", "").length() + 2; i++)
				{
					spaces1.append(" ");
				}
				spaces2.append(operator);
				for(int i = 0; i < result.length() - num2.toString().
						replaceFirst("^0+(?!$)", "").length() + 1; i++)
				{
					spaces2.append(" ");
				}

				System.out.println(spaces1.toString() + num1.toString().
										replaceFirst("^0+(?!$)", ""));
				System.out.println(spaces2.toString() + num2.toString().
										replaceFirst("^0+(?!$)", ""));
				System.out.println(line);
				System.out.println("  " + result);
			}
			//Multiply operation.
			else
			{
				result = (new BigInt(num1.toString())).
						multiply(new BigInt(num2.toString())).toString();
				result = result.replaceFirst("^0+(?!$)", "");
				for(int i = 0; i < result.length() + 2; i++)
				{
					line.append("-");
				}
				for(int i = 0; i < result.length() - num1.toString().
						replaceFirst("^0+(?!$)", "").length() + 2; i++)
				{
					spaces1.append(" ");
				}
				spaces2.append(operator);
				for(int i = 0; i < result.length() - num2.toString().
						replaceFirst("^0+(?!$)", "").length() + 1; i++)
				{
					spaces2.append(" ");
				}

				System.out.println(spaces1.toString() + num1.toString().
										replaceFirst("^0+(?!$)", ""));
				System.out.println(spaces2.toString() + num2.toString().
										replaceFirst("^0+(?!$)", ""));
				System.out.println(line);
				System.out.println("  " + result);
			}
			//Reset
			count = 0;
			indexOfOperator = 0;
			operator = ' ';
			num1 = new StringBuilder();
			num2 = new StringBuilder();
			spaces1 = new StringBuilder();
			spaces2 = new StringBuilder();
			line = new StringBuilder();
			result = null;
			System.out.print("Compute: ");
			input = myScanner.nextLine();
		}
		//Close scanner.
		myScanner.close();
	}
}
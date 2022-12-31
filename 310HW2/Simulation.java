import java.awt.*;
import java.util.*;

/**
 *  The Simulation class is a program that runs and animates a simulation of
 *  Foxes and Hounds.
 */

public class Simulation {

   // The constant CELL_SIZE determines the size of each cell on the screen
   // during animation.
   private static final int CELL_SIZE = 10;
   private static final String USAGE_MESSAGE = "Usage: java Simulation [--graphics] [--width int] [--height int] [--starvetime int] [--fox float] [--hound float]";
   
   
   /**
    * A method that counts the number of Foxes around a cell that has hound.
    * 
    * @param combinationOfEightCells
    *                      represents combination of the eight cells's colors
    *                      that around a cell.
    * @return the number of Foxes around that cell.
    */
   private static int getAroundFoxCount(Color[] combinationOfEightCells)
   {
      int foxNum = 0;
      for(Color color: combinationOfEightCells)
      {
         if(Color.green.equals(color))
         {
            foxNum++;
         }
      }
      return foxNum;
   }
   
   
   /**
    * A method that counts the number of hounds around a cell that has Fox.
    * @param combinationOfEightCells
    *                      represents combination of the eight cells's colors
    *                      that around a cell.
    * @return the number of hounds around that cell.
    */
   private static int getAroundHoundCount(Color[] combinationOfEightCells)
   {
      int houndNum = 0;
      for(Color color: combinationOfEightCells)
      {
         if(Color.red.equals(color))
         {
            houndNum++;
         }
      }
      return houndNum;
   }
   
   
   /**
    * Computes the next state of the field from the current state and
    * returns the new state
    *
    * @param currentState is the current state of the Field
    *
    * @return new field state after one timestep
    */
   private static Field performTimestep(Field currentState)
   {
      //Local variables.
      int height = currentState.getHeight();
      int width = currentState.getWidth();
      Field newState = new Field(width, height);
      Color cellColor = null;
      Hound tempHound = null;
      int tempAroundHoundCount = 0;
      int tempAroundFoxCount = 0;
      
      //Look through the field by starting with the first x-axis, and then the
      //second x-axis... Look through the y-axis in every single x-axis.
      //And compute the new state field.
      for(int i = 0; i < width; i++)
      {
         for(int j = 0; j < height; j++)
         {
            //Get current cell's color. If current cell's occupant is null,
            //then set the cell's color to null.
            if(currentState.getOccupantAt(i, j) != null)
            {
               cellColor = currentState.getOccupantAt(i, j).getDisplayColor();
            }
            else
            {
               cellColor = null;
            }
            
            //Get all eight cells around current cell. To make sure we can get
            //the index correctly we need the index in proper range.(For
            //example, 0<=index<=width-1). The remainder of a positive integer 
            //is the correct index since the edges of the field are connected 
            //together.
            Color[] combinationOfEightCells = new Color[8];
            if(currentState.getOccupantAt(i, 
                  (height + j + 1) % height) != null)
            {
               combinationOfEightCells[0] = 
                     currentState.getOccupantAt(i, 
                           (height + j + 1) % height).getDisplayColor();
            }
            //Set the around cells to null if the occupant is null.
            else
            {
               combinationOfEightCells[0] = null;
            }
            if(currentState.getOccupantAt((width + i + 1) % width, j) != null)
            {
               combinationOfEightCells[1] =  
                     currentState.getOccupantAt((width + i + 1) % width, 
                           j).getDisplayColor();
            }
            //Set the around cells to null if the occupant is null.
            else
            {
               combinationOfEightCells[1] = null;
            }
            if(currentState.getOccupantAt((width + i - 1)% width, j) != null)
            {
               combinationOfEightCells[2] = 
                     currentState.getOccupantAt((width + i - 1)% width, 
                           j).getDisplayColor();
            }
            //Set the around cells to null if the occupant is null.
            else
            {
               combinationOfEightCells[2] = null;
            }
            if(currentState.getOccupantAt(i, (height + j - 1) % height) != null)
            {
               combinationOfEightCells[3] = 
                     currentState.getOccupantAt(i, 
                           (height + j - 1) % height).getDisplayColor();
            }
            //Set the around cells to null if the occupant is null.
            else
            {
               combinationOfEightCells[3] = null;
            }
            if(currentState.getOccupantAt((width + i + 1) % width, 
                  (height + j - 1) % height) != null)
            {
               combinationOfEightCells[4] = 
                     currentState.getOccupantAt((width + i + 1) % width, 
                           (height + j - 1) % height).getDisplayColor();
            }
            //Set the around cells to null if the occupant is null.
            else
            {
               combinationOfEightCells[4] = null;
            }
            if(currentState.getOccupantAt((width + i - 1) % width, 
                  (height + j - 1) % height) != null)
            {
               combinationOfEightCells[5] = 
                     currentState.getOccupantAt((width + i - 1) % width, 
                     (height + j - 1) % height).getDisplayColor();
            }
            //Set the around cells to null if the occupant is null.
            else
            {
               combinationOfEightCells[5] = null;
            }
            if(currentState.getOccupantAt((width + i + 1) % width,
                  (height + j + 1) % height) != null)
            {
               combinationOfEightCells[6] = 
                     currentState.getOccupantAt((width + i + 1) % width,
                     (height + j + 1) % height).getDisplayColor();
            }
            //Set the around cells to null if the occupant is null.
            else
            {
               combinationOfEightCells[6] = null;
            }
            if(currentState.getOccupantAt((width + i - 1) % width, 
                  (height + j + 1) % height) != null)
            {
               combinationOfEightCells[7] = 
                     currentState.getOccupantAt((width + i - 1) % width, 
                     (height + j + 1) % height).getDisplayColor();
            }
            //Set the around cells to null if the occupant is null.
            else
            {
               combinationOfEightCells[7] = null;
            }
            //Deal with the situation that the current cell is hound by 
            //checking the cell color. 
            if (Color.red.equals(cellColor))
            {
               //when there is any of its neighbors is a Fox, then the Hound 
               //eats during the timestep, and it remains in the cell at the 
               //end of the timestep with its hunger completely gone.
               if(Simulation.getAroundFoxCount(combinationOfEightCells) > 0)
               {
                  newState.setOccupantAt(i, j, new Hound());
               }
               //If none of its neighbors is a Fox,
               //it gets hungrier during the timestep. If this timestep is the 
               //(starveTime + 1)th consecutive timestep the Hound has gone 
               //without eating, then the Hound dies (disappears). Otherwise, 
               //it remains in the cell but gets closer to starvation.
               if(Simulation.getAroundFoxCount(combinationOfEightCells) == 0)
               {
                  tempHound = (Hound)currentState.getOccupantAt(i, j);
                  //when there is no fox around the hound, it is getting closer
                  //to starvation, by decreasing the starve time.
                  tempHound.getHungry();
                  //If the hound died, then make it disappear in the cell.
                  if(tempHound.isDie())
                  {
                     newState.setOccupantAt(i, j, null);
                  }
                  //Otherwise, keep getting closer to starvation.
                  else
                  {
                     newState.setOccupantAt(i, j, tempHound);
                  }
               }
            }
            //Deal with the situation that the current cell is fox by 
            //checking the cell color.
            else if (cellColor.green.equals(cellColor))
            {
               tempAroundHoundCount = 
                     Simulation.getAroundHoundCount(combinationOfEightCells);
               if(tempAroundHoundCount == 1)
               {
                  newState.setOccupantAt(i, j, null);
               }
               else if(tempAroundHoundCount > 1)
               {
                  newState.setOccupantAt(i, j, new Hound());
               }
            }
            //Deal with the situation that the current cell is empty.
            else
            {
               tempAroundFoxCount = 
                     Simulation.getAroundFoxCount(combinationOfEightCells);
               tempAroundHoundCount = 
                     Simulation.getAroundHoundCount(combinationOfEightCells);
               if((tempAroundFoxCount >= 2) && (tempAroundHoundCount <= 1))
               {
                  newState.setOccupantAt(i, j, new Fox());
               }
               else if((tempAroundFoxCount >=2) && (tempAroundHoundCount >= 2))
               {
                  newState.setOccupantAt(i, j, new Hound());
               }
            }
         }
      }      
      return newState;
   } // performTimestep


   /**
    * Draws the current state of the field
    *
    * @param graphicsContext is an optional GUI window to draw to
    * @param theField is the object to display
    */
   private static void drawField(Graphics graphicsContext, Field theField) 
   {
      // If we have a graphics context then update the GUI, otherwise
      // output text-based display
      if (graphicsContext != null)
      {
         // Iterate over the cells and draw the thing in that cell
         for (int i = 0; i < theField.getHeight(); i++) 
         {
            for (int j = 0; j < theField.getWidth(); j++) 
            {
               // Get the color of the object in that cell and set the 
               // cell color
               if (theField.getOccupantAt(j,i) != null)
               {
                  graphicsContext.setColor(theField.getOccupantAt(j,i)
                                           .getDisplayColor());       
               }
               else // Empty cells are white
               {
                  graphicsContext.setColor(Color.white);
               } 
               graphicsContext.fillRect(j * CELL_SIZE, 
                                        i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            } // for
         } // for
      }
      else // No graphics, just text
      {
         // Draw a line above the field
         for (int i = 0; i < theField.getWidth() * 2 + 1; i++) 
         {
            System.out.print("-");
         }
         System.out.println();
         // For each cell, display the thing in that cell
         for (int i = 0; i < theField.getHeight(); i++) 
         {
            System.out.print("|"); // separate cells with '|' 
            for (int j = 0; j < theField.getWidth(); j++) 
            {
               if (theField.getOccupantAt(j,i) != null)
               {
                  System.out.print(theField.getOccupantAt(j,i)+"|");
               }
               else
               {
                  System.out.print(" |");
               }
            }
            System.out.println();
         } // for

         // Draw a line below the field
         for (int i = 0; i < theField.getWidth() * 2 + 1; i++) 
         {
            System.out.print("-");
         }
         System.out.println();

      } // else
   } // drawField


   /**
    *  Main reads the parameters and performs the simulation and animation.
    */
   public static void main(String[] args) throws InterruptedException 
   {
      /**
       *  Default parameters.  (You may change these if you wish.)
       */
      int width = 50;                              
      int height  = 25;                           
      int starveTime = Hound.DEFAULT_STARVE_TIME;
      double probabilityFox = 0.5;              
      double probabilityHound = 0.15;         
      boolean graphicsMode = false;

      Random randomGenerator = new Random();      
      Field theField = null;

      // If we attach a GUI to this program, these objects will hold
      // references to the GUI elements
      Frame windowFrame = null;
      Graphics graphicsContext = null;
      Canvas drawingCanvas = null;

      /*
       *  Process the input parameters. Switches we understand include:
       *  --graphics for "graphics" mode
       *  --width 999 to set the "width" 
       *  --height 999 to set the height
       *  --hound 0.999 to set the "hound probability"
       */
      for (int argNum=0; argNum < args.length; argNum++)
      {
         try
         {
            switch(args[argNum])
            {
               case "--graphics":  // Graphics mode
                  graphicsMode = true;
                  break;
                  
               case "--width": // Set width
                  width = Integer.parseInt(args[++argNum]);
                  break;

               case "--height": // set height
                  height = Integer.parseInt(args[++argNum]);
                  break;

               case "--starvetime": // set 'starve time'
                  starveTime = Integer.parseInt(args[++argNum]);
                  break;

               case "--fox": // set the probability for adding a fox
                  probabilityFox = Double.parseDouble(args[++argNum]);
                  break;

               case "--hound": // set the probability for adding a hound
                  probabilityHound = Double.parseDouble(args[++argNum]);
                  break;

               default: // Anything else is an error and we'll quit
                  System.err.println("Unrecognized switch.");
                  System.err.println(USAGE_MESSAGE);
                  System.exit(1);
            } // switch
         }
         catch (NumberFormatException | ArrayIndexOutOfBoundsException e) 
         {
            System.err.println("Illegal or missing argument.");
            System.err.println(USAGE_MESSAGE);
            System.exit(1);
         }
      } // for

      // Create the initial Field.
      theField = new Field(width, height);

      // Visit each cell; randomly placing a Fox, Hound, or nothing in each.
      for (int i = 0; i < theField.getWidth(); i++) 
      {
         for (int j = 0; j < theField.getHeight(); j++) 
         {
            // If a random number is less than or equal to the probability
            // of adding a fox, then place a fox
            if (randomGenerator.nextFloat() <= probabilityFox) 
            {
               theField.setOccupantAt(i, j, new Fox());
            } 
            // If a random number is less than or equal to the probability of
            // adding a hound, then place a hound. Note that if a fox
            // has already been placed, it remains and the hound is
            // ignored.
            if (randomGenerator.nextFloat() <= probabilityHound) 
            {    
               theField.setOccupantAt(i, j, new Hound());
            }
         } // for
      } // for

      // If we're in graphics mode, then create the frame, canvas, 
      // and window. If not in graphics mode, these will remain null
      if (graphicsMode)
      {
         windowFrame = new Frame("Foxes and Hounds");
         windowFrame.setSize(theField.getWidth() * CELL_SIZE + 10, 
                             theField.getHeight() * CELL_SIZE + 30);
         windowFrame.setVisible(true);

         // Create a "Canvas" we can draw upon; attach it to the window.
         drawingCanvas = new Canvas();
         drawingCanvas.setBackground(Color.white);
         drawingCanvas.setSize(theField.getWidth() * CELL_SIZE, 
                               theField.getHeight() * CELL_SIZE);
         windowFrame.add(drawingCanvas);
         graphicsContext = drawingCanvas.getGraphics();
      } // if 

      // Loop infinitely, performing timesteps. We could optionally stop
      // when the Field becomes empty or full, though there is no
      // guarantee either of those will ever arise...
      while (true) 
      {                                              
         Thread.sleep(1000);  // Wait one second (1000 milliseconds)
         drawField(graphicsContext, theField);  // Draw the current state 
         theField = performTimestep(theField);  // Simulate a timestep
      }

   } // main

} 
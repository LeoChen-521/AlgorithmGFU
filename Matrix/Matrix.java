import java.util.Vector;

public class Matrix
{
   
   protected int height, width;//Size of matrix.
   
   protected Vector rows;      //Vector of row vectors.
   
   public Matrix(int h, int w)
   //pre: h >= 0. w >= 0.
   //post: constructs an h row by w column matrix.
   {
      height = h;//Initialize height and width.
      width = w;
      //Allocate a vector of rows.
      rows = new Vector(height);
      for(int r = 0; r < height; r++)
      {  //Each row is allocated and filled with nulls.
         Vector theRow = new Vector(width);
         rows.add(theRow);
         for(int c = 0; c < width; c++)
         {
            theRow.add(null);
         }
      }
   }
   
   
   public Object get(int row, int col)
   //pre: 0 <= row < height(), 0 <= col < width()
   //post: returns object at (row, col)
   {
	   if(row < 0 || row >= height)
	   {
		   throw new IndexOutOfBoundsException("Row out of bound");
	   }
	   
	   if(col < 0 || col >= width)
	   {
		   throw new IndexOutOfBoundsException("Col out of bound");
	   }
	   
       Vector theRow = (Vector)rows.get(row);
       return theRow.get(col);
   }
   
   
   public void set(int row, int col, Object value)
   //pre: 0 <= row < height(), 0 <= col < width()
   //post: changes location (row, col) to value
   {
	   if(row < 0 || row >= height)
	   {
		   throw new IndexOutOfBoundsException("Row out of bound");
	   }
	   
	   if(col < 0 || col >= width)
	   {
		   throw new IndexOutOfBoundsException("Col out of bound");
	   }
       Vector theRow = (Vector)rows.get(row);
       theRow.set(col, value);
   }
   
   
   public void addRow(int r)
   //pre: 0 <= r < height()
   //post: inserts row of null values to be row r
   {
	   if(r < 0 || r >= height)
	   {
		   throw new IndexOutOfBoundsException("Row out of bound");
	   }
	   
       height++;
       Vector theRow = new Vector(width);
       for(int c = 0; c < width; c++)
       {
          theRow.add(null);
       }
       rows.add(r, theRow);
   }
   
   
   public void addCol(int c)
   //pre: 0 <= r < width()
   //post: inserts col of null values to be col c
   {
	   if(c < 0 || c >= width)
	   {
		   throw new IndexOutOfBoundsException("Col out of bound");
	   }
	   
	   width++;
	   Vector theCol = new Vector(height);
	   for(int r = 0; r < height; r++)
	   {
		   Vector theRow = (Vector) rows.get(r);
		   theRow.add(c, null);
	   }
   }
   
   
   public Vector removeRow(int r)
   //pre: 0 <= r < height()
   //post: removes row r and return that row
   {
	   if(r < 0 || r >= height)
	   {
		   throw new IndexOutOfBoundsException("Row out of bound");
	   }
	   
	   height--;
	   return (Vector) rows.remove(r);
   }
   
   
   public Vector removeCol(int c)
   //pre: 0 <= r < width()
   //post: removes col c and return that col
   {
	   if(c < 0 || c >= width)
	   {
		   throw new IndexOutOfBoundsException("Col out of bound");
	   }
	   
	   Vector theCol = new Vector(height);
	   width--;
	   
	   for(int r = 0; r < height; r++)
	   {
		   theCol.add(((Vector)rows.get(r)).remove(c));
	   }
	   return theCol;
   }
   
   public int width()
   {
      return width;
   }
   
   
   public int height()
   {
      return height;
   }
}
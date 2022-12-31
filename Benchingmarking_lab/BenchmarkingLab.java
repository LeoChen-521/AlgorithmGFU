
public class BenchmarkingLab
{
   public static void main(String[] args)
   {
   long i,loops,start,stop,duration;
   double speed;
   loops = 200000000L;
   
   start = System.nanoTime();
   for(i=0; i<loops;i++)
   {
      
   }
   stop = System.nanoTime();
   
   duration = stop-start;
   System.out.println("# Elapsed time: "+duration+"ns");
   System.out.println("# Mean time: "+
                     (((double)duration)/loops)+
                     "nanoseconds");
   }
}

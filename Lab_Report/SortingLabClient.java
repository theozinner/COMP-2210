/**
 * SortingLabClient.java
 *
 * Provides a simple example client of SortingLab.java.
 *
 * NOTE: The generic type of SortingLab must be bound
 *       to a Comparable type. The sorting methods in
 *       SortingLab use the natural ordering of the
 *       elements in the parameter array.
 *
 * @author    Dean Hendrix (dh@auburn.edu)
 * @version   2018-09-16
 *
 */
public final class SortingLabClient {

   /** Drives execution. */
   public static void main(String[] args) {

      // instantiate the SortingLab class
      // using your Banner ID number as the key value
      int key = 903502834;
      SortingLab<String> sls = new SortingLab<String>(key);

      // run each sort a few times before trying to
      // collect timing data
      String[] as = {"D", "B", "E", "C", "A"};
      for (int i = 0; i < 10; i++) {
         sls.sort1(as);
         sls.sort2(as);
         sls.sort3(as);
         sls.sort4(as);
         sls.sort5(as);
      }
      
      

      // generate timing data for one sort method using
      // the "doubling strategy" from lecture and lab
      SortingLab<Integer> sli = new SortingLab<Integer>(key);
      int M = 12801; // max capacity for array
      int N = 100;   // initial size of array
      double start;
      double elapsedTime;
      double eTimeLast = 0; 
      System.out.print("sort\n");
      for (; N < M; N *= 2) {
         Integer[] ai = getIntegerArray(N, Integer.MAX_VALUE);
         start = System.nanoTime(); //time start
         sli.sort4(ai); //sort happens here
         elapsedTime = (System.nanoTime() - start) / 1_000_000_000d; //time stop
         System.out.print(N + "\t"); //print N
         System.out.printf("%4.3f", elapsedTime); //print time
         if(eTimeLast == 0) {
            System.out.print("\tN/A\n"); //Print N/A
         }
         else {
         System.out.printf("\t%4.3f\n", elapsedTime / eTimeLast); //print ratio
         }
         eTimeLast = elapsedTime;
      }
      System.out.print("Sort twice\n");
      M = 128000001; // max capacity for array
      N = 1000000; 
      for (; N < M; N *= 2) {
         Integer[] ai2 = getIntegerArray2(N, Integer.MAX_VALUE);
         start = System.nanoTime();
         sli.sort4(ai2);
         elapsedTime = (System.nanoTime() - start) / 1_000_000_000d;
         System.out.print(N + "\t");
         System.out.printf("%4.3f", elapsedTime);
         if(eTimeLast == 0) {
            System.out.print("\tN/A\n");
         }
         else {
         System.out.printf("\t%4.3f\n", elapsedTime / eTimeLast);
         }
         eTimeLast = elapsedTime;
      }
      System.out.print("stability\n");
      SortingLab<twoNumber> sli2 = new SortingLab<twoNumber>(key);
      twoNumber[] x = getTwoNumberArray(20);
      for (int i = 0; i < 20; i++) {
         System.out.print(x[i].getFirst() + "\t" + x[i].getSecond() +"\n");
      }
      System.out.print("sorted\n");
      sli2.sort4(x);
      for (int i = 0; i < 20; i++) {
         System.out.print(x[i].getFirst() + "\t" + x[i].getSecond() +"\n");
      }
   }

   /** return an array of random integer values. */
   private static Integer[] getIntegerArray(int N, int max) {
      Integer[] a = new Integer[N];
      java.util.Random rng = new java.util.Random();
      for (int i = 0; i < N; i++) {
         a[i] = rng.nextInt(max);
      }
      return a;
   }
   /** return an array of sorted integer values. */
   private static Integer[] getIntegerArray2(int N, int max) {
      Integer[] b = new Integer[N];
      for (int i = 0; i < N; i++) {
         b[i] = i;
      }
      return b;
   }
   private static twoNumber[] getTwoNumberArray(int N) {
      twoNumber[] a = new twoNumber[N]; //create array of twoNumbers
      java.util.Random rng = new java.util.Random();
      boolean oneCount = true;
      for (int i = 0; i < N; i++) {
         if (oneCount) {
            a[i] = new twoNumber(1, i);
            oneCount = false;  
         }
         else {
            a[i] = new twoNumber (2, i);
            oneCount = true;
         }
      }
      return a;
   }

}

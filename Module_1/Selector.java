import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   Theo Zinner (tvz0001@auburn.edu)
* @author   Dean Hendrix (dh@auburn.edu)
* @version  8/24/18
*
*/
public final class Selector {

   /**
    * Can't instantiate this class.
    *
    * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
    *
    */
   private Selector() { }


   /**
    * Selects the minimum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    * @param a input
    * @return output
    * @throws IllegalArgumentException null
    */
   public static int min(int[] a) throws IllegalArgumentException {
      if ((a == null) || (a.length == 0)) {
         throw new IllegalArgumentException();
      }
      int output = a[0];
      for (int i = 0; i < a.length; i++) {
         if (a[i] < output) {
            output = a[i];
         }
      }
      return output;
   }


   /**
    * Selects the maximum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    * @param a input
    * @return output
    * @throws IllegalArgumentException null
    */
   public static int max(int[] a) throws IllegalArgumentException {
      if ((a == null) || (a.length == 0)) {
         throw new IllegalArgumentException();
      }
      int output = a[0];
      for (int i = 0; i < a.length; i++) {
         if (a[i] > output) {
            output = a[i];
         }
      }
      return output;
   }


   /**
    * Selects the kth minimum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth minimum value. Note that there is no kth
    * minimum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmin(int[] a, int k) throws IllegalArgumentException {
      if ((a == null) || (a.length == 0) || (k < 1) || (k > a.length)) {
         throw new IllegalArgumentException();
      }
      int low = min(a);
      for (int i = 0; i < k - 1; i++) {
         low = ceiling(a, low + 1);               
      }
      if (k == 1) {
         low = min(a);
      }
      return low;
   }


   /**
    * Selects the kth maximum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth maximum value. Note that there is no kth
    * maximum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmax(int[] a, int k) throws IllegalArgumentException {
      if ((a == null) || (a.length == 0) || (k < 1) || (k > a.length)) {
         throw new IllegalArgumentException();
      }
      int high = max(a);
      for (int i = 0; i < k - 1; i++) {
         high = floor(a, high - 1);         
      }
      if (k == 1) {
         high = max(a);
      }
      return high;
   }


   /**
    * Returns an array containing all the values in a in the
    * range [low..high]; that is, all the values that are greater
    * than or equal to low and less than or equal to high,
    * including duplicate values. The length of the returned array
    * is the same as the number of values in the range [low..high].
    * If there are no qualifying values, this method returns a
    * zero-length array. Note that low and high do not have
    * to be actual values in a. This method throws an
    * IllegalArgumentException if a is null or has zero length.
    * The array a is not changed by this method.
    * @param a input
    * @param low input
    * @param high input
    * @return output
    * @throws IllegalArgumentException null
    */
   public static int[] range(int[] a, int low, int high)
      throws IllegalArgumentException {
      if ((a == null) || (a.length == 0)) {
         throw new IllegalArgumentException();
      }
      int[] output = new int[0];
      for (int i = 0; i < a.length; i++) {
         if ((a[i] >= low) && (a[i] <= high)) {
            output = Arrays.copyOf(output, output.length + 1);
            output[output.length - 1] = a[i];
         }
      }
      return output;
   }


   /**
    * Returns the smallest value in a that is greater than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int ceiling(int[] a, int key) throws IllegalArgumentException {
      if ((a == null) || (a.length == 0)) {
         throw new IllegalArgumentException();
      }
      int output = max(a);
      for (int i = 0; i < a.length; i++) {
         if ((a[i] < output) && (a[i]) >= key) {
            output = a[i];
         }
      }
      if (output < key) {
         throw new IllegalArgumentException();
      }
      return output;
   }


   /**
    * Returns the largest value in a that is less than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int floor(int[] a, int key) throws IllegalArgumentException {
      if ((a == null) || (a.length == 0)) {
         throw new IllegalArgumentException();
      }
      int output = min(a);
      for (int i = 0; i < a.length; i++) {
         if ((a[i] > output) && (a[i]) <= key) {
            output = a[i];
         }
      }
      if (output > key) {
         throw new IllegalArgumentException();
      }
      return output;
   }

}

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Defines a library of selection methods on Collections.
 *
 * @author  Theo Zinner (tvz0001@auburn.edu)
 * @author  Dean Hendrix (dh@auburn.edu)
 * @version 09/10/18
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
    * Returns the minimum value in the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty, this method throws a
    * NoSuchElementException. This method will not change coll in any way.
    *
    * @param coll    the Collection from which the minimum is selected
    * @param comp    the Comparator that defines the total order on T
    * @return        the minimum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
    //   static Comparator<Integer> ascendingInteger =
   public static <T> T min(Collection<T> coll, Comparator<T> comp)
      throws IllegalArgumentException, NoSuchElementException {
      if ((coll == null) || (comp == null)) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();  
      }
      Iterator<T> iMin = coll.iterator();
      T min = iMin.next();
      if (iMin.hasNext()) {
         for (T e : coll) {
            if (comp.compare(e, min) < 0) {
               min = e;
            }
         }
      }
      return min;
   }


   /**
    * Selects the maximum value in the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty, this method throws a
    * NoSuchElementException. This method will not change coll in any way.
    *
    * @param coll    the Collection from which the maximum is selected
    * @param comp    the Comparator that defines the total order on T
    * @return        the maximum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T max(Collection<T> coll, Comparator<T> comp)
      throws IllegalArgumentException, NoSuchElementException {
      if ((coll == null) || (comp == null)) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();  
      }
      Iterator<T> iMax = coll.iterator();
      T max = iMax.next();
      if (iMax.hasNext()) {
         for (T e : coll) {
            if (comp.compare(e, max) > 0) {
               max = e;
            }
         }
      }
      return max;
   }


   /**
    * Selects the kth minimum value from the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty or if there is no kth minimum
    * value, this method throws a NoSuchElementException. This method will not
    * change coll in any way.
    *
    * @param coll    the Collection from which the kth minimum is selected
    * @param k       the k-selection value
    * @param comp    the Comparator that defines the total order on T
    * @return        the kth minimum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T kmin(Collection<T> coll, int k, Comparator<T> comp)
      throws IllegalArgumentException, NoSuchElementException {
      if ((coll == null) || (comp == null)) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();  
      }
      ArrayList<T> sColl = new ArrayList<T>();
      sColl.addAll(coll);
      sColl.sort(comp); 
      for (int i = 1; i < sColl.size(); i++) {
         if (comp.compare(sColl.get(i), sColl.get(i - 1)) == 0) {
            sColl.set(i - 1, null); //set dupes to null
         }
      }
      while (sColl.remove(null)){
         //nothing haha
      }
      if ((sColl.size() < k) || (k < 1)) {
         throw new NoSuchElementException();  
      }
      return sColl.get(k - 1);
   }


   /**
    * Selects the kth maximum value from the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty or if there is no kth maximum
    * value, this method throws a NoSuchElementException. This method will not
    * change coll in any way.
    *
    * @param coll    the Collection from which the kth maximum is selected
    * @param k       the k-selection value
    * @param comp    the Comparator that defines the total order on T
    * @return        the kth maximum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T kmax(Collection<T> coll, int k, Comparator<T> comp)
      throws IllegalArgumentException, NoSuchElementException {
      if ((coll == null) || (comp == null)) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();  
      }
      ArrayList<T> sColl = new ArrayList<T>();
      sColl.addAll(coll);
      sColl.sort(comp); 
      for (int i = 1; i < sColl.size(); i++) {
         if (comp.compare(sColl.get(i), sColl.get(i - 1)) == 0) {
            sColl.set(i - 1, null); //set dupes to null
         }
      }
      while (sColl.remove(null)) {
         //nothing
      }
      if ((sColl.size() < k) || (k < 1)) {
         throw new NoSuchElementException();  
      }
      return sColl.get(sColl.size() - k);
   }

   /**
    * Returns a new Collection containing all the values in the Collection coll
    * that are greater than or equal to low and less than or equal to high, as
    * defined by the Comparator comp. The returned collection must contain only
    * these values and no others. The values low and high themselves do not have
    * to be in coll. Any duplicate values that are in coll must also be in the
    * returned Collection. If no values in coll fall into the specified range or
    * if coll is empty, this method throws a NoSuchElementException. If either
    * coll or comp is null, this method throws an IllegalArgumentException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the range values are selected
    * @param low     the lower bound of the range
    * @param high    the upper bound of the range
    * @param comp    the Comparator that defines the total order on T
    * @return        a Collection of values between low and high
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> Collection<T> range(Collection<T> coll, T low, T high,
      Comparator<T> comp) throws IllegalArgumentException, NoSuchElementException {
      if ((coll == null) || (comp == null)) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();  
      }
      Iterator<T> iRange = coll.iterator();
      Collection<T> rangeOut = new ArrayList<T>();
      if (iRange.hasNext()) {
         for (T e : coll) {
            if ((comp.compare(e, low) >= 0) && (comp.compare(e, high) <= 0)) {
               rangeOut.add(e);
            }
         }
      }
      if (rangeOut.isEmpty()) {
         throw new NoSuchElementException();  
      }
      return rangeOut;
   }


   /**
    * Returns the smallest value in the Collection coll that is greater than
    * or equal to key, as defined by the Comparator comp. The value of key
    * does not have to be in coll. If coll or comp is null, this method throws
    * an IllegalArgumentException. If coll is empty or if there is no
    * qualifying value, this method throws a NoSuchElementException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the ceiling value is selected
    * @param key     the reference value
    * @param comp    the Comparator that defines the total order on T
    * @return        the ceiling value of key in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T ceiling(Collection<T> coll, T key, Comparator<T> comp)
      throws IllegalArgumentException, NoSuchElementException {
      if ((coll == null) || (comp == null)) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();  
      }
      
      Iterator<T> iMax = coll.iterator();
      T ceil = iMax.next();
      if (iMax.hasNext()) {
         for (T e : coll) {
            if (comp.compare(e, ceil) > 0) {
               ceil = e;
            }
         }
      }
      
      Iterator<T> iCeil = coll.iterator();
      if (iCeil.hasNext()) {
         for (T e : coll) {
            if (comp.compare(e, key) == 0) {
               return e;
            }
            if ((comp.compare(e, ceil) < 0) && (comp.compare(e, key) > 0)) {
               ceil = e;
            }
         }
      }
      if ((ceil == null) || (comp.compare(ceil, key) < 0)) {
         throw new NoSuchElementException();  
      }
      return ceil;
   }


   /**
    * Returns the largest value in the Collection coll that is less than
    * or equal to key, as defined by the Comparator comp. The value of key
    * does not have to be in coll. If coll or comp is null, this method throws
    * an IllegalArgumentException. If coll is empty or if there is no
    * qualifying value, this method throws a NoSuchElementException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the floor value is selected
    * @param key     the reference value
    * @param comp    the Comparator that defines the total order on T
    * @return        the floor value of key in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T floor(Collection<T> coll, T key, Comparator<T> comp)
      throws IllegalArgumentException, NoSuchElementException {
      if ((coll == null) || (comp == null)) {
         throw new IllegalArgumentException();
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException();  
      }
      
      Iterator<T> iMin = coll.iterator();
      T floor = iMin.next();
      if (iMin.hasNext()) {
         for (T e : coll) {
            if (comp.compare(e, floor) < 0) {
               floor = e;
            }
         }
      }
      Iterator<T> iFloo = coll.iterator();      
      if (iFloo.hasNext()) {
         for (T e : coll) {
            if (comp.compare(e, key) == 0) {
               return e;
            }
            if ((comp.compare(e, floor) > 0) && (comp.compare(e, key) < 0)) {
               floor = e;
            }
         }
      }
      if ((floor == null) || (comp.compare(floor, key) > 0)) {
         throw new NoSuchElementException();  
      }
      return floor;
   }

}

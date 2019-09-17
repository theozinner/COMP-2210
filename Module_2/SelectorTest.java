import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class SelectorTest {

   /** Collections used in the various examples. */
   static Collection<Integer> c1 = Arrays.<Integer>asList(new Integer[]{2,8,7,3,4});
   static Collection<Integer> c2 = Arrays.<Integer>asList(new Integer[]{5,9,1,7,3});
   static Collection<Integer> c3 = Arrays.<Integer>asList(new Integer[]{8,7,6,5,4});
   static Collection<Integer> c5 = Arrays.<Integer>asList(new Integer[]{2,2,2,2,2}); //same
   static Collection<Integer> c6 = Arrays.<Integer>asList(new Integer[]{}); //empty
   static Collection<Integer> c7 = Arrays.<Integer>asList(new Integer[]{-3,3,9,7,0});
   static Collection<Integer> c8 = Arrays.<Integer>asList(new Integer[]{3,7,3,3,1,9,1,1,1,5});

   /**
    * Defines a total order on integers as ascending natural order.
    */
   static Comparator<Integer> ascendingInteger =
      new Comparator<Integer>() {
         public int compare(Integer i1, Integer i2) {
            return i1.compareTo(i2);
         }
      };


   /**
    * Defines a total order on integers as descending natural order.
    * This is the reverse of ascendingInteger above.
    */
   static Comparator<Integer> descendingInteger =
      new Comparator<Integer>() {
         public int compare(Integer i1, Integer i2) {
            return i2.compareTo(i1);
         }
      };


   /**
    * Defines an exmple composite "data" value with two fields.
    */
   static class Data {
      String  stringVal;
      Integer integerVal;

      public Data(String sval, Integer ival) {
         stringVal = sval;
         integerVal = ival;
      }

      @Override
      /**
       * Returns a string representation of this Data.
       * @return a formatted string with s and i values
       */
      public String toString() {
         return "(" + stringVal + ", " + integerVal + ")";
      }

      @Override
      /**
       * Returns true if the provided object is
       * equal to this Data, false otherwise.
       */
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         }
         if (obj == null) {
            return false;
         }
         if (obj.getClass() != this.getClass()) {
            return false;
         }
         Data that = (Data) obj;
         return (this.stringVal.equals(that.stringVal))
            && (this.integerVal.equals(that.integerVal));
      }
   }


   /** An array of Data used in various examples. */
   static Collection<Data> c4 = Arrays.<Data>asList(new Data[]{
      new Data("A",5), new Data("B", 4), new Data("C", 3), new Data("D", 2), new Data("E", 1)});


   /**
    * Defines a total order on Data as ascending natural order of
    * the String field s.
    */
   static Comparator<Data> ascendingStringData =
      new Comparator<Data>() {
         public int compare(Data d1, Data d2) {
            return d1.stringVal.compareTo(d2.stringVal);
         }
      };


   /**
    * Defines a total order on Data as ascending natural order of
    * the Integer field i.
    */
   static Comparator<Data> ascendingIntegerData =
      new Comparator<Data>() {
         public int compare(Data d1, Data d2) {
            return d1.integerVal.compareTo(d2.integerVal);
         }
      };


   /**
    * Returns a string representation of the given collection.
    * @param  c the provided collection
    * @return   a formatted string with each element of the collection
    */
   static String asString(Collection c) {
      StringBuilder s = new StringBuilder();
      s.append("[");
      for (Object o : c) {
         s.append(o.toString() + ",");
      }
      s.delete(s.length() - 1, s.length());
      s.append("]");
      return s.toString();
   }


   /**
    * Calls one or more methods to generate the handout examples.
    *
    * @param args command line arguments (unused)
    */
   public static void main(String[] args) {
      minExamples();
      maxExamples();
      kminExamples();
      kmaxExamples();
      rangeExamples();
      ceilingExamples();
      floorExamples();
   }


   /**
    * Generates examples for the floor method.
    */
   public static void floorExamples() {
      System.out.println(Selector.<Integer>floor(c1, 6, ascendingInteger));
      System.out.println(Selector.<Integer>floor(c2, 1, descendingInteger));
      System.out.println(Selector.<Integer>floor(c3, 9, ascendingInteger));
      System.out.println(Selector.<Data>floor(c4, new Data("F",0), ascendingStringData));
      System.out.println(Selector.<Data>floor(c4, new Data("B",9), ascendingIntegerData));
   }


   /**
    * Generates examples for the ceiling method.
    */
   public static void ceilingExamples() {
      System.out.println(Selector.<Integer>ceiling(c1, 1, ascendingInteger));
      System.out.println(Selector.<Integer>ceiling(c2, 7, descendingInteger));
      System.out.println(Selector.<Integer>ceiling(c3, 0, ascendingInteger));
      System.out.println(Selector.<Data>ceiling(c4, new Data("B",9), ascendingStringData));
      System.out.println(Selector.<Data>ceiling(c4, new Data("F",0), ascendingIntegerData));
   }


   /**
    * Generates examples for the range method.
    */
   public static void rangeExamples() {
      System.out.println(asString(Selector.<Integer>range(c1, 1, 5, ascendingInteger)));
      System.out.println(asString(Selector.<Integer>range(c2, 3, 5, ascendingInteger)));
      System.out.println(asString(Selector.<Integer>range(c2, 5, 3, descendingInteger)));
      System.out.println(asString(Selector.<Integer>range(c3, 4, 8, ascendingInteger)));
      System.out.println(asString(Selector.<Data>range(c4, new Data("B",3),
         new Data("C",5), ascendingStringData)));
      System.out.println(asString(Selector.<Data>range(c4, new Data("F",4),
         new Data("G",7), ascendingIntegerData)));
   }


   /**
    * Generates examples for the kmax method.
    */
   public static void kmaxExamples() {
      System.out.println(Selector.<Integer>kmax(c1, 1, ascendingInteger));
      System.out.println(Selector.<Integer>kmax(c2, 2, descendingInteger));
      System.out.println(Selector.<Integer>kmax(c3, 3, ascendingInteger));
      System.out.println(Selector.<Data>kmax(c4, 4, ascendingStringData));
      System.out.println(Selector.<Data>kmax(c4, 2, ascendingIntegerData));
   }


   /**
    * Generates examples for the kmin method.
    */
   public static void kminExamples() {
      System.out.println(Selector.<Integer>kmin(c1, 1, ascendingInteger));
      System.out.println(Selector.<Integer>kmin(c2, 2, descendingInteger));
      System.out.println(Selector.<Integer>kmin(c3, 3, ascendingInteger));
      System.out.println(Selector.<Data>kmin(c4, 4, ascendingStringData));
      System.out.println(Selector.<Data>kmin(c4, 2, ascendingIntegerData));
   }


   /**
    * Generates examples for the max method.
    */
   public static void maxExamples() {
      System.out.println(Selector.<Integer>max(c1, ascendingInteger));
      System.out.println(Selector.<Integer>max(c2, descendingInteger));
      System.out.println(Selector.<Integer>max(c3, ascendingInteger));
      System.out.println(Selector.<Data>max(c4, ascendingStringData));
      System.out.println(Selector.<Data>max(c4, ascendingIntegerData));
   }


   /**
    * Generates examples for the min method.
    */
   public static void minExamples() {
      System.out.println(Selector.<Integer>min(c1, ascendingInteger));
      System.out.println(Selector.<Integer>min(c2, descendingInteger));
      System.out.println(Selector.<Integer>min(c3, ascendingInteger));
      System.out.println(Selector.<Data>min(c4, ascendingStringData));
      System.out.println(Selector.<Data>min(c4, ascendingIntegerData));
   }







   /** A test that always fails 60% of the time everytime. **/
   @Test public void maxTest() {
      Assert.assertEquals(2, Selector.<Integer>max(c5, ascendingInteger), 0.0);
   }
   @Test public void kMaxTest() {
      Assert.assertEquals(5, Selector.<Integer>kmax(c8, 3, ascendingInteger), 0.0);
   }
   @Test public void ceilingTest() {
      Assert.assertEquals(9, Selector.<Integer>ceiling(c7, 9, ascendingInteger), 0.0);
   }
}

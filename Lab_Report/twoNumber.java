public class twoNumber implements Comparable<twoNumber> {
      private int first;
      private int second;
   twoNumber(int firstIn, int secondIn) {
      first = firstIn; //compares this only
      second = secondIn;
   }
   
   public int getFirst() {
      return first;
   } 
   public int getSecond() {
      return second;
   }
   public int compareTo(twoNumber that) {
      if (this.first < that.first) {
         return -1;
      }
      else if (this.first > that.first) {
         return 1;
      }
      else
         return 0;
   }
}
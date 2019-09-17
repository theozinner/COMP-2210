import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class LinkedSetTest {

   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }

   @Test public void addTest() { //test add
      LinkedSet ls = new LinkedSet();
      ls.add("aaa");
      ls.add("abc");
            Assert.assertEquals( ls.toString(), "[aaa, abc]");
   }
   @Test public void complementTest() { //test complement
      LinkedSet ls = new LinkedSet();
      LinkedSet ls2 = new LinkedSet();
      ls.add("aaa");
      ls.add("abc");
      ls2.add("abc");
      
            Assert.assertEquals( ls.complement(ls2).toString(), "[aaa]");
   }
   @Test public void unionTest() { //test union
      LinkedSet ls = new LinkedSet();
      LinkedSet ls2 = new LinkedSet();
      ls.add("aaa");
      ls.add("abc");
      ls2.add("bbb");
      
            Assert.assertEquals( ls.union(ls2).toString(), "[aaa, abc, bbb]");
   }
   @Test public void powerhasnextnullTest() { //test hasNext null
      LinkedSet ls = new LinkedSet();
      
            Assert.assertEquals(ls.powerSetIterator().hasNext(), false);
   }
   @Test public void powerhasnextTest() { //test hasNext
      LinkedSet ls = new LinkedSet();
      ls.add("aaa");
      
            Assert.assertEquals(ls.powerSetIterator().hasNext(), true);
   }
}

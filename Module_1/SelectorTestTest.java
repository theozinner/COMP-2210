import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class SelectorTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


   /** A test that always fails. **/
   @Test public void kmaxtest() {
      int[] a = {1,3,5,6,9,11};
      int k = 3;
      Assert.assertEquals( 6, Selector.kmax(a,k));
   }
}

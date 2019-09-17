
/**
 * Provides a factory method for creating word search games. 
 *
 * @author Theo Zinner (tvz0001@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 11/24/18
 */
public class WordSearchGameFactory {

   /**
    * Returns an instance of a class that implements the WordSearchGame
    * interface.
    */
   public static WordSearchGame createGame() {
      // You must return an instance of your solution class here instead of null.
      return new Boogle();
   }

}

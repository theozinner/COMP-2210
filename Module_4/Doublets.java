import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;


import java.util.stream.Collectors;

/**
 * Provides an implementation of the WordLadderGame interface. 
 *
 * @author Theo Zinner (tvz0001@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2018-10-29
 */
public class Doublets implements WordLadderGame {

    // The word list used to validate words.
    // Must be instantiated an∂d populated in the constructor.
    /////////////////////////////////////////////////////////////////////////////
    // DECLARE A FIELD NAMED lexicon HERE. THIS FIELD IS USED TO STORE ALL THE //
    // WORDS IN THE WORD LIST. YOU CAN CREATE YOUR OWN COLLECTION FOR THIS     //
    // PURPOSE OF YOU CAN USE ONE OF THE JCF COLLECTIONS. SUGGESTED CHOICES    //
    // ARE TreeSet (a red-black tree) OR HashSet (a closed addressed hash      //
    // table with chaining).                                                   //
    /////////////////////////////////////////////////////////////////////////////
   TreeSet lexicon = new TreeSet();
    /**
     * Instantiates a new instance of Doublets with the lexicon populated with
     * the strings in the provided InputStream. The InputStream can be formatted
     * in different ways as long as the first string on each line is a word to be
     * stored in the lexicon.
     */
     
   public Doublets(InputStream in) {
      try {
            //////////////////////////////////////
            // INSTANTIATE lexicon OBJECT HERE  //
            //////////////////////////////////////
         Scanner s = new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.next();
                /////////////////////////////////////////////////////////////
                // INSERT CODE HERE TO APPROPRIATELY STORE str IN lexicon. //
                /////////////////////////////////////////////////////////////
            lexicon.add(str.toLowerCase());
            s.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }


    //////////////////////////////////////////////////////////////
    // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
    //////////////////////////////////////////////////////////////

    /**
     * Checks to see if the given string is a word.
     *
     * @param  str the string to check
     * @return     true if str is a word, false otherwise
     */
     
   public boolean isWord(String str) {
      if (lexicon.contains(str)) {
         return true;
      }
      return false;
   }
   
   public int getWordCount() {
      return lexicon.size();
   }
   
    /**
     * Returns the Hamming distance between two strings, str1 and str2. The
     * Hamming distance between two strings of equal length is defined as the
     * number of positions at which the corresponding symbols are different. The
     * Hamming distance is undefined if the strings have different length, and
     * this method returns -1 in that case. See the following link for
     * reference: https://en.wikipedia.org/wiki/Hamming_distance
     *
     * @param  str1 the first string
     * @param  str2 the second string
     * @return      the Hamming distance between str1 and str2 if they are the
     *                  same length, -1 otherwise
     */
   
   public int getHammingDistance(String str1, String str2) {
      if (str1.length() != str2.length()) {
         return -1;
      }
      int output = 0;
      for (int i = 0; i < str1.length(); i++) {
         if (str1.charAt(i) != str2.charAt(i)) {
            output++;
         }
      }
      return output;
   }
   
    /**
     * Returns all the words that have a Hamming distance of one relative to the
     * given word.
     *
     * @param  word the given word
     * @return      the neighbors of the given word
     */
   
   public List<String> getNeighbors(String word) {
      ArrayList<String> output = new ArrayList();
      String abc = "abcdefghijklmnopqrstuvwxyz";
      for (int i = 0; i < word.length(); i++) {
         for (int j = 0; j < 26; j++) {
            String wordC = word.substring(0, i).concat(abc.substring(j, j + 1)).concat(word.substring(i + 1, word.length()));
            if (isWord(wordC) && !output.contains(wordC)) {
               output.add(wordC);
            }
         }
      }
      output.remove(word);
      return output;
   }
   
    /**
     * Checks to see if the given sequence of strings is a valid word ladder.
     *
     * @param  sequence the given sequence of strings
     * @return          true if the given sequence is a valid word ladder,
     *                       false otherwise
     */
   
   public boolean isWordLadder(List<String> sequence) {
      if (sequence.isEmpty()) {
         return false;
      }
      ArrayList words = new ArrayList();
      Iterator<String> itr = sequence.iterator();
      boolean first = true;
      String last = null;
      while (itr.hasNext()) {
         String word = itr.next();
         if (isWord(word) && !words.contains(word)) {
            if (!first) {
               if (getHammingDistance(word, last) != 1) {
                  return false;
               }
            }
            words.add(word);
         }
         else {
            return false;
         }
         first = false;
         last = word;
      }
      return true;
   }
   
   /**
    * Returns a minimum-length word ladder from start to end. If multiple
    * minimum-length word ladders exist, no guarantee is made regarding which
    * one is returned. If no word ladder exists, this method returns an empty
    * list.
    *
    * Breadth-first search must be used in all implementing classes.
    *
    * @param  start  the starting word
    * @param  end    the ending word
    * @return        a minimum length word ladder from start to end
    */
   
   public List<String> getMinLadder(String start, String end) { //use stack
   
      ArrayList<String> output = new ArrayList();
      TreeSet<String> first = new TreeSet();
      
      if (start.length() != end.length()) {
         return output; //empty   
      }
      if (start.equals(end)) {
         output.add(start);
         return output;
      }
      else if (getHammingDistance(start, end) == 1) {
         output.add(start);
         output.add(end);
         return output;
      }
      ArrayDeque<Node> deq = new ArrayDeque();
      first.add(start);
      deq.addLast(new Node(start, null));
      while (!deq.isEmpty()) {
         Node n = deq.removeFirst();
         String spot = n.spot;
         for (String element : getNeighbors(spot)) {
            if (!first.contains(element)) {
               first.add(element);
               deq.addLast(new Node(element, n));
            }
            if (element.equals(end)) {
               Node m = deq.removeLast();
               
               while (m != null) {
                  output.add(0, m.spot);
                  m = m.last;
               }
               return output;
            }
         }
         
      }
      ArrayList<String> eOutput = new ArrayList();
      return eOutput;
   }
   
   private class Node {
      String spot;
      Node last;
      
      public Node(String s, Node l) {
         spot = s;
         last = l;
      }
   }
}

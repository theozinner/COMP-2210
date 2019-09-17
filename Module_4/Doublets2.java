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
public class Doublets2 implements WordLadderGame {

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
     
   public Doublets2(InputStream in) {
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
   
 public List<String> getMinLadder(String start, String end) {
      
      List<String> ladder = new ArrayList<String>();
      if (start == null || end == null) {
         return ladder;
      }
      if (getHammingDistance(start, end) == 1) {
         ladder.add(start);
         ladder.add(end);
         return ladder;
      }
      else if (start.equals(end)) {
         ladder.add(start);
         return ladder;
      }
      else if (!isWord(start) || !isWord(end) || (start.length() != end.length())) {
         return ladder;
      }
      
      List<String> visit = new ArrayList<String>();//Everything we've visited.
      Deque<String> queue = new ArrayDeque<>();
      visit.add(start);
      ladder.add(start);
      queue.addLast(start);
      String smolHam = start;
      while (queue.size() != 0) {
         String word = queue.removeFirst();
         List<String> neighbor = getNeighbors(word);//calls neighbors on all visited words.
         //slowly eliminates all possible words.
         for (int i = 0; i < neighbor.size(); i++) {
            if (!visit.contains(neighbor.get(i))) {//if visit doesnt have the word in it.
               visit.add(neighbor.get(i));//add it to visit. 
               queue.addLast(neighbor.get(i));//adds word to queue?
               if (getHammingDistance(neighbor.get(i), end) < getHammingDistance(smolHam, end)
                     && (getHammingDistance(smolHam, neighbor.get(i)) == 1)){
                  //If the distance between the target word and the end is less than smolHam and end target
                  //gets added to ladder and becomes new smolHam. however if both items are the same distance away.
                  ladder.add(neighbor.get(i));
                  smolHam = neighbor.get(i);
               }
               else if (getHammingDistance(neighbor.get(i), end) == getHammingDistance(smolHam,end)) {
                  smolHam = neighbor.get(i);
                  ladder.add(neighbor.get(i));
                  for (int j = i; j < neighbor.size(); j++) {
                     if (!visit.contains(neighbor.get(j))) {
                        visit.add(neighbor.get(j));
                        queue.addLast(neighbor.get(j));
                     }
                  }
                  break;
               }
            }
         }
         
      }
      return ladder;
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

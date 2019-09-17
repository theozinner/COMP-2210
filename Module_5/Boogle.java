import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;



/**
 * booooooogle. 
 *
 * @author Theo Zinner (tvz0001@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 11/24/18
 */
public class Boogle implements WordSearchGame {

//fields
   private String[][] board;
   private boolean[][] seentIt;
   private int[] position;
   private TreeSet<String> lexicon = new TreeSet();
   private SortedSet<String> words;
   private int boardSize = 0; //side
   private boolean ll = false;
   private List<Integer> wae;
   private List<Integer> daWae;
   private boolean isWord = false;
   private int min = 0;
   private SortedSet<String> vaildWords;

   
   //constructor
 /**
  * constructor.
  */
   public Boogle() {
      wae = new ArrayList<Integer>();
      daWae = new ArrayList<Integer>();
      vaildWords = new TreeSet<String>();
   }
   
  /**
    * Loads the lexicon into a data structure for later use. 
    * 
    * @param fileName A string containing the name of the file to be opened.
    * @throws IllegalArgumentException if fileName is null
    * @throws IllegalArgumentException if fileName cannot be opened.
    */
   public void loadLexicon(String fileName) throws IllegalArgumentException {
      ll = true;
      Scanner filer;
      Scanner lineS;
      String line;
      try {
         filer = new Scanner(new FileReader(fileName));
         while (filer.hasNext()) {
            line = filer.nextLine();
            lineS = new Scanner(line);
            lineS.useDelimiter(" "); //spaces
            
            while (lineS.hasNext()) {
               lexicon.add(lineS.next().toUpperCase()); 
            }
         
         }
      } 
      catch (Exception e) {
         throw new IllegalArgumentException("file not found");
      }
   }
   
   /**
    * Stores the incoming array of Strings in a data structure that will make
    * it convenient to find words.
    * 
    * @param letterArray This array of length N^2 stores the contents of the
    *     game board in row-major order. Thus, index 0 stores the contents of board
    *     position (0,0) and index length-1 stores the contents of board position
    *     (N-1,N-1). Note that the board must be square and that the strings inside
    *     may be longer than one character.
    * @throws IllegalArgumentException if letterArray is null, or is  not
    *     square.
    */
   public void setBoard(String[] letterArray) throws IllegalArgumentException {
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
      int n = (int) Math.sqrt(letterArray.length); //square root integer
      if (Math.pow(n, 2) != letterArray.length) {
         throw new IllegalArgumentException();
      }
      int k = 0;
      boolean[][] boardSeent = new boolean[n][n]; //2d array
      int[] numpost = new int[letterArray.length];
      String[][] boardIndex = new String[n][n]; //2d array
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
            boardIndex[i][j] = letterArray[k].toUpperCase();
            boardSeent[i][j] = false; 
            k++;
         }
      }
      for (int i = 0; i < letterArray.length; i++) {
         numpost[i] = i;
      }
      board = boardIndex;
      boardSize = n;
      seentIt = boardSeent;
      position = numpost;
   }
   
   /**
    * Creates a String representation of the board, suitable for printing to
    *   standard out. Note that this method can always be called since
    *   implementing classes should have a default board.
    */
   public String getBoard() {
      String output = "";
      for (int i = 0; i < boardSize; i++) {
         for (int j = 0; j < boardSize; j++) {
            output += board[i][j];
         }
      }
      return output;
   }
   
   /**
    * Retrieves all valid words on the game board, according to the stated game
    * rules.
    * 
    * @param minimumWordLength The minimum allowed length (i.e., number of
    *     characters) for any word found on the board.
    * @return java.util.SortedSet which contains all the words of minimum length
    *     found on the game board and in the lexicon.
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public SortedSet<String> getAllValidWords(int minimumWordLength)  throws IllegalArgumentException {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (!ll) {
         throw new IllegalStateException();
      }
      vaildWords.clear();
      min = minimumWordLength;
      for (int i = 0; i < boardSize; i++) {
         for (int j = 0; j < boardSize; j++) {
            findWord(board[i][j], i, j);
         }
      }
      return vaildWords;
   }
   
    /**
     * find the words to get all valid words.
     */
   private void findWord(String word, int x, int y) {
   
      if (!isValidPrefix(word.toUpperCase())) {
         return;
      }
      seentIt[x][y] = true;
      if (isValidWord(word) && word.length() >= min) {
         vaildWords.add(word.toUpperCase());
      }
   
      for (int i = -1; i < 2; i++) {
         for (int j = -1; j < 2; j++) {
            if (((x + i) < boardSize && (y + j) < boardSize) && (x + i) > -1 && (y + j) > -1 && !seentIt[x + i][y + j]) {
               seentIt[x + i][y + j] = true;
               findWord(word + board[x + i][y + j], x + i, y + j);
               seentIt[x + i][y + j] = false;
            }
         }
      }
      seentIt[x][y] = false;
   }
   
  /**
   * Computes the cummulative score for the scorable words in the given set.
   * To be scorable, a word must (1) have at least the minimum number of characters,
   * (2) be in the lexicon, and (3) be on the board. Each scorable word is
   * awarded one point for the minimum number of characters, and one point for 
   * each character beyond the minimum number.
   *
   * @param words The set of words that are to be scored.
   * @param minimumWordLength The minimum number of characters required per word
   * @return the cummulative score of all scorable words in the set
   * @throws IllegalArgumentException if minimumWordLength < 1
   * @throws IllegalStateException if loadLexicon has not been called.
   */  
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) throws IllegalArgumentException {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (!ll) {
         throw new IllegalStateException();
      }
      int output = 0;
      Iterator<String> itr = words.iterator();
      while (itr.hasNext()) {
         int that = itr.next().length();
         if (that >= minimumWordLength) {
            output += that + 1 - minimumWordLength;
         }
      }
      return output;
   }
   
   /**
    * Determines if the given word is in the lexicon.
    * 
    * @param wordToCheck The word to validate
    * @return true if wordToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidWord(String wordToCheck) throws IllegalArgumentException {
      if (!ll || wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (lexicon.contains(wordToCheck.toUpperCase())) {
         return true;
      }
      return false;
   }

   
   /**
    * Determines if there is at least one word in the lexicon with the 
    * given prefix.
    * 
    * @param prefixToCheck The prefix to validate
    * @return true if prefixToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if prefixToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidPrefix(String prefixToCheck) throws IllegalArgumentException {
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (!ll) {
         throw new IllegalStateException();
      }
      String dingy = lexicon.ceiling(prefixToCheck.toUpperCase());
      if (dingy == null) {
         return false;
      }
      return lexicon.ceiling(prefixToCheck.toUpperCase()).startsWith(prefixToCheck.toUpperCase());

   }
      
   /**
    * Determines if the given word is in on the game board. If so, it returns
    * the path that makes up the word.
    * @param wordToCheck The word to validate
    * @return java.util.List containing java.lang.Integer objects with  the path
    *     that makes up the word on the game board. If word is not on the game
    *     board, return an empty list. Positions on the board are numbered from zero
    *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
    *     board, the upper left position is numbered 0 and the lower right position
    *     is numbered N^2 - 1.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public List<Integer> isOnBoard(String wordToCheck)  throws IllegalArgumentException {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (!ll) {
         throw new IllegalStateException();
      }
      daWae.clear();
      wae.clear();
      for (int i = 0; i < boardSize; i++) {
         for (int j = 0; j < boardSize; j++) {
            if (board[i][j].charAt(0) == (wordToCheck.charAt(0))) {
               int spot = (i * boardSize) + j;
               wae.add(spot);
               nearby(spot, board[i][j], wordToCheck);
               if (!daWae.isEmpty()) {
                  isWord = true;
                  return daWae;
               }
               daWae.clear();
               wae.clear();
            }
         }
      }
      return wae;
   }
   
   
   
   private void nearby(int input, String currentPiece, String wordToCheck) { //recursive method
      int n = input / boardSize;
      int m = input % boardSize;
      seentIt[n][m] = true;
      if (!isValidPrefix(currentPiece)) {
         return;
      }
      if (currentPiece.toUpperCase().equals(wordToCheck.toUpperCase())) {
         daWae = new ArrayList(wae);
         return;
      }
      for (int i = -1; i < 2; i++) {
         for (int  j = -1; j < 2; j++) {
            if (wordToCheck.equals(currentPiece)) {
               return;
            }
            if (((n + i) < boardSize) && ((m + j) < boardSize) && ((n + i) > -1)
               && ((m + j) > -1) && (!seentIt[n + i][m + j])) {
               seentIt[n + i][m + j] = true;
               int thingy = ((n + i) * boardSize) + m + j;
               wae.add(thingy);
               nearby(thingy, currentPiece + board[n + i][m + j], wordToCheck);
               seentIt[n + i][m + j] = false;
               wae.remove(wae.size() - 1);
            }
         }
      }
      seentIt[n][m] = false;
      return;
   }
   
   //I seent it!
   
   private void resetSeentIt() {
      for (int i = 0; i < boardSize; i++) {
         for (int j = 0; j < boardSize; j++) {
            seentIt[i][j] = false;
         }
      }   
   }

}
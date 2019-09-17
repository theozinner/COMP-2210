import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 * ExampleClient.java
 * Provides example calls to WordLadderGame methods in an instance of
 * the Doublets class.
 *
 * The word list files must be extracted into the current directory
 * before running this class.
 *
 *      jar xf WordLists.jar
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2018-10-23
 */
public class ExampleClient2 {

    /** Drives execution. */
    public static void main(String[] args) throws FileNotFoundException {
        WordLadderGame doublets2 = new Doublets2(new FileInputStream(new File("sowpods.txt")));

        System.out.println(doublets2.getHammingDistance("tiger", "tiger"));
        System.out.println(doublets2.getHammingDistance("tiger", "eagle"));
        System.out.println(doublets2.getHammingDistance("war", "eagle"));
        System.out.println(doublets2.getHammingDistance("barner", "bammer"));

        System.out.println(doublets2.isWord("tiger"));
        System.out.println(doublets2.isWord("eagle"));
        System.out.println(doublets2.isWord("aubie"));

        System.out.println(doublets2.getWordCount());

        System.out.println(doublets2.isWordLadder(Arrays.asList("cat", "cot", "zot", "dot")));
        System.out.println(doublets2.isWordLadder(Arrays.asList("cat", "cot", "pot", "dot")));

        System.out.println(doublets2.getNeighbors("tiger"));

        System.out.println(doublets2.getMinLadder("cat", "dog"));
    }
}

/*

RUNTIME OUTPUT

0
4
-1
2
true
true
false
267751
false
true
[liger, niger, tiler, timer, titer, tiges]
[cat, hat]

 */


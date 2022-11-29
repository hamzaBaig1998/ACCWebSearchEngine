package websearchengine;
/**
 * <h1>AutoCorrect</h1>
 * A word correction program similar to those found in cell phone or email
 * clients. User may enter a word, press ENTER, and if the word is spelled
 * correctly, the program prints "Correct." If not, and the word is merely
 * mispelled, the program suggests words that the user might have intended.
 * Lastly, if the word does not resemble any dictionary words, the program
 * prints "No suggestions." Dictionary words are loaded from words.txt.
 * <p>
 * By: Matthew Levan<br>
 * Professor: Dr. Amlan Chatterjee<br>
 * Course: CSC 311 Data Structure<br>
 * Assignment: Project 4<br>
 * Due Date: 11/25/2015 by 11:59 PM.
 * 
 *
 * @author Matthew Levan
 * @version 11/20/2015
 */

import java.net.URI;
import java.util.*;
import java.io.*;

public class AutoCorrect {
    /**
     * GLOBAL ATTRIBUTES
     */
    private static String wordsFileName = "src/websearchengine/words.txt"; // Word list text file
    private static Scanner scanner = new Scanner(System.in);
    public static String[] words; // Word list array
    private static String userWord = ""; // Empty by default
    private static Integer distance = 0; // Zero by default
    private static Search search = new Search(); // For binary search
    private static Stack<String> suggestions = new Stack<String>(50);
    private static ArrayList<String> tempSuggestion = new ArrayList<>();
    /**
     * Main method for execution of the AutoCorrect program.
     *
     * @param args Command line arguments, String[].
     */
    public static void main(String[] args) {
        System.out.println("\nAUTOCORRECT SIMULATION\n");
        loadWords();
        sort(words);
        startSimulation();
    }

    /**
     * Loads a word list text file into memory for fast access.
     *
     */
    public static void loadWords() {
        String line = null; // Temp variable for storing one line at a time
        ArrayList<String> temp = new ArrayList<String>();

        try {

            FileReader fileReader = new FileReader(wordsFileName);
            BufferedReader buffReader = new BufferedReader(fileReader);

            while ((line = buffReader.readLine()) != null) {
                temp.add(line.trim());
            }

            buffReader.close();
            words = new String[temp.size()];
            temp.toArray(words);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sorts any E[] using insertion sort.
     *
     * @param <E> Type of Object to use.
     * @param array The E[] to be sorted in place.
     */
    public static <E extends Comparable<E>> void sort(E[] array) {
        int n = array.length; // Get length of array

        // Insertion sort
        for (int i = 1; i < n; i++) {
            E temp = array[i]; // Save the element at index i
            int j = i - 1; // Let j be the element one index before i

            // Iterate through array
            while (j > -1 && (array[j].compareTo(temp) > 0)) {
                // Insert element at array[j] in proper place
                array[j + 1] = array[j];
                j--;
            }

            // Complete swap
            array[j + 1] = temp;
        }
    }


    /**
     * Sorts the global, static String[] words, based on insertion sort.
     *
     */
    /*
    public static void sortWords() {
        int n = words.length; // Get length of words array

        // Insertion sort
        for (int i = 1; i < n; i++) {
            String temp = words[i]; // Save the element at index i
            int j = i - 1; // Let j be the element index one before i

            // Iterate through array
            while (j > -1 && (words[j].compareTo(temp) > 0)) {
                // Insert elemnt at words[j] in proper place
                words[j + 1] = words[j];
                j--;
            }

            // Complete the swap..
            words[j + 1] = temp;
        }
    }
    */

    /**
     * Retrieves a word from the user via Scanner.
     *
     */
    public static void startSimulation() {
        System.out.println("Type \"DONE\" when finished.\n");

        // Loop repeats until user types "done", case insensitive
        while (true) {
            System.out.print("Enter a word: ");
            userWord = scanner.next().trim().toLowerCase();

            System.out.print("Enter the distance: ");
            distance = Integer.valueOf(scanner.next().trim().toLowerCase());

            // Break out of the while loop if user enters "done"
            if (userWord.equalsIgnoreCase("done")) {
                System.out.println("Goodbye.\n");
                break;
            }

            // If user enters anything else, call autoCorrect()
            autoCorrect();
        }
    }

    /**
     * Searches for userWord in word list.<br>
     * Prints "Correct." if word is spelled correctly.<br>
     * Prints a suggestion if word is close to a correct word.<br>
     * Prints "No suggestions." if word is clearly obscure.
     *
     */
    public static void autoCorrect() {
        int result = Search.binarySearch(words, userWord);

        // First, check for an exact match
        if (result != -1) {
            System.out.println("Correct. Congratulations, you spelt it right!"
                + "\n");
        }
        // Else, check if the user's word is an anagram of a dictionary word
        else {
            for (String word : words) {
                char wordStart; // First char of word
                char userWordStart; // First char of userWord
                String tempUserWord, tempSysWord;
                if (!word.isEmpty()) { // If word is NOT empty

                        if (userWord.length() == word.length()) {
                            // Same length
                            if (containsAllChars(userWord, word)) // Same chars
                                suggestions.push(word);
                        }
                        tempUserWord = userWord.substring(0, userWord.length()-distance);
                        if (tempUserWord.length() == word.length()) // Same length
                            if (containsAllChars(tempUserWord, word)) // Same chars
                                suggestions.push(word);


                    tempUserWord = removeDuplicates(userWord);
                    tempSysWord = removeDuplicates(word);
                    if (tempUserWord.length() == tempSysWord.length()) // Same length
                        if (tempUserWord.equals(tempSysWord)) // Same chars
                            suggestions.push(word);
                }
            }

            if (suggestions.isEmpty()) {
                System.out.println("No suggestions.\n");

                System.out.print("Enter a word to add to word bank: ");
                String newWord = scanner.next().trim().toLowerCase();

                try(PrintWriter output = new PrintWriter(new FileWriter("src/websearchengine/words.txt",true)))
                {
                    output.printf("%s\r\n", newWord);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                loadWords();
                sort(words);

                // If user enters anything else, call autoCorrect()
                autoCorrect();
            }
            else {
                System.out.print("Suggestions: ");
                while (!suggestions.isEmpty()) {
                    String temp = suggestions.pop();
                    if(!tempSuggestion.contains(temp))
                        System.out.print(temp + " ");
                    tempSuggestion.add(temp);
                }
                System.out.println("\n");
            }
        }
    }

    public static int toInt(String str) {
        int asciiValue = 0;
        for (int i = 0; i < str.length(); i++) {
            asciiValue += str.charAt(i);
        }

        return asciiValue;
    }

    public static boolean containsAllChars(String strOne, String strTwo) {
        Character[] one = strToCharArray(strOne);
        Character[] two = strToCharArray(strTwo);

        sort(one);
        sort(two);

        for (int i = 0; i < one.length; i++) {
            if (Search.binarySearch(two, one[i]) == -1)
                return false;
            two[i] = '0';
        }

        two = strToCharArray(strTwo);
        sort(two);

        for (int i = 0; i < two.length; i++) {
            if (Search.binarySearch(one, two[i]) == -1)
                return false;
            one[i] = '0';
        }

        return true;
    }

    public static Character[] strToCharArray(String str) {
        Character[] charArray = new Character[str.length()];
        for (int i = 0; i < str.length(); i++) {
            charArray[i] = new Character(str.charAt(i));
        }

        return charArray;
    }

    public static String removeDuplicates(String input){
        String result = "";
        for (int i = 0; i < input.length(); i++) {
            if(!result.contains(String.valueOf(input.charAt(i)))) {
                result += String.valueOf(input.charAt(i));
            }
        }
        return result;
    }
}
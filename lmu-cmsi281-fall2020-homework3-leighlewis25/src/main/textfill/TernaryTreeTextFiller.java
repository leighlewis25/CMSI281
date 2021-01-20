package main.textfill;



import java.util.*;

/**
 * A ternary-search-tree implementation of a text-autocompletion
 * trie, a simplified version of some autocomplete software.
 * @author Leigh Lewis
 */
public class TernaryTreeTextFiller implements TextFiller {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    private TTNode root;
    private int size;

    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    public TernaryTreeTextFiller () {
        this.root = null;
        this.size = 0;
    }


    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    /**
     * Checks the number of stored terms inside of the TextFiller
     * @return the number of stored terms inside of the TextFiller
     */
    public int size () {
        return this.size;
    }

    /**
     * Checks whether or not there are 0 stored terms inside of the TextFiller
     * @return true if the number of stored terms inside of the TextFiller, false otherwise
     */
    public boolean empty () {
        return this.root == null;
    }

    /**
     * Adds the given search term toAdd to the TextFiller; if the desired toAdd 
     * already exists inside of the TextFiller, does nothing
     * @param toAdd The given search term we would like to add
     */
    public void add (String toAdd) {
        normalizeTerm(toAdd);
        if (!contains(toAdd)) {
            int position = 0;
            int priority = 0;
            this.root = add(toAdd, position, this.root, priority);
            this.size++;
        }
    }

    /**
     * Checks whether or not the query is contained within the TextFiller
     * @param query The search term we are checking whether or not is contained within
     * the TextFiller
     * @return true if the given query exists within the TextFiller,
     * false otherwise
     */
    public boolean contains (String query) {
        normalizeTerm(query);
        int position = 0;
        TTNode newNode = getFinalCharNode(query, position, this.root);
        if (newNode == null) {
            return false;
        }  
        return newNode.wordEnd;
    }

    /**
     * Fills in the rest of the word contained in the TextFiller with the prefix query
     * @param query the prefix we are looking for a search term that contains it
     * @return a String of the first search term contained in the TextFiller that
     * possesses the query as a prefix; if the query is not a prefix of any contained
     * terms, returns null
     */
    public String textFill (String query) {
        normalizeTerm(query);
        int position = 0;
        TTNode currentNode = getFinalCharNode(query, position, this.root);
        if (currentNode == null) {
            return null;
        }
        while (!currentNode.wordEnd) {
            query += currentNode.mid.letter; 
            currentNode = currentNode.mid;
        }
        return query;

    }

    /**
     * Gets an alphabetically getSortedListed list of terms contained in the TextFiller
     * @return an ArrayList of Strings consisting of the alphabetically getSortedListed
     * search terms within this TextFiller
     */
    public List<String> getSortedList () {
        ArrayList<String> wordList = new ArrayList<>();
        getSortedList(this.root, wordList, "");
        return wordList;
    }


    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------

    /**
     * Normalizes a term to either add or search for in the tree,
     * since we do not want to allow the addition of either null or
     * empty strings within, including empty spaces at the beginning
     * or end of the string (spaces in the middle are fine, as they
     * allow our tree to also store multi-word phrases).
     * @param s The string to sanitize
     * @return The sanitized version of s
     */
    private String normalizeTerm (String s) {
        // Edge case handling: empty Strings illegal
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        return s.trim().toLowerCase();
    }

    /**
     * Given two characters, c1 and c2, determines whether c1 is
     * alphabetically less than, greater than, or equal to c2
     * @param c1 The first character
     * @param c2 The second character
     * @return
     *   - some int less than 0 if c1 is alphabetically less than c2
     *   - 0 if c1 is equal to c2
     *   - some int greater than 0 if c1 is alphabetically greater than c2
     */
    private int compareChars (char c1, char c2) {
        return Character.toLowerCase(c1) - Character.toLowerCase(c2);
    }

    // [!] Add your own helper methods here!

    /**
     * Recursive helper method for getSortedList(), that gets an alphabetically sorted
     * list of terms in the TextFiller
     * @param currentNode The currentNode being visited
     * @param wordList an arrayList containing the terms in alphabetical order
     * @param word The word being added to the arrayList
     */
    private void getSortedList (TTNode currentNode, ArrayList<String> wordList, String word) {
        if (currentNode != null) {
            getSortedList(currentNode.left, wordList, word);
            word += currentNode.letter;

            if (currentNode.wordEnd) {
                wordList.add(word);
            }

            getSortedList(currentNode.mid, wordList, word);
            word = word.substring(0, word.length() - 1);
            getSortedList(currentNode.right, wordList, word);
        }
    }

    /**
     * Checks (recursively) whether or not the String query is contained within the TextFiller and 
     * gets the Node containing the final char in the String query (if it exists)
     * @param query The string are checking to see whether or not it is contained
     * @param position The index of the string
     * @param currentNode
     * @return
     */
    private TTNode getFinalCharNode (String query, int position, TTNode currentNode) {
        if (currentNode == null) {
            return null;
        }

        int direction = compareChars(query.charAt(position), currentNode.letter);

        if (direction < 0) {
            return getFinalCharNode(query, position, currentNode.left);
        } else if (direction > 0) {
            return getFinalCharNode(query, position, currentNode.right);
        } else {
            if (position == query.length() - 1) {
                return currentNode;
            } else {
                return getFinalCharNode(query, position + 1, currentNode.mid);
            }
        }
    }
    
    

    /**
     * Adds a string to the TernaryTreeTextFiller recursively
     * @param toAdd the string to be added
     * @param position The position / index of the character in the string]
     * being added
     * @param currentNode The node at which we are checking and adding
     */
    private TTNode add (String toAdd, int position, TTNode currentNode, int priority) {
        if (currentNode == null) {
            currentNode = new TTNode(toAdd.charAt(position), false); 
        }

        int direction = compareChars(toAdd.charAt(position), currentNode.letter);

        if (direction < 0) {
            currentNode.left = add(toAdd, position, currentNode.left, priority);
        } else if (direction > 0) {
            currentNode.right = add(toAdd, position, currentNode.right, priority);
        } else {
            if (priority > currentNode.priority) {
                currentNode.priority = priority;
            }
            
            if (position < toAdd.length() - 1) {
                currentNode.mid = add(toAdd, position + 1, currentNode.mid, priority);
            } else {
                currentNode.highestPriority = priority;
                currentNode.priority = priority;
                currentNode.wordEnd = true;
            }
        }
        return currentNode;
    }
    
    
    
    

    // -----------------------------------------------------------
    // Extra Credit Methods
    // -----------------------------------------------------------

    /**
     * Adds the given string toAdd to the TernaryTreeTextFiller and associates
     * a wordPriority with the given terminal wordEnd node
     * @param toAdd The string we would like to add
     * @param priority The priority to be associated with the terminal wordEnd
     * node
     */
    public void add (String toAdd, int priority) {
        normalizeTerm(toAdd);
        if (!contains(toAdd)) {
            int position = 0;
            this.root = add(toAdd, position, this.root, priority);
            this.size++;
        }
    }

    /**
     * Fills in the rest of the highest priority word contained in the TextFiller 
     * with the prefix query
     * @param query The prefix we are looking to fill in / recommend a word for
     * @return The highest priority search term contained in the TextFiller
     * that possesses the query as a prefix
     */
    public String textFillPremium (String query) {
        normalizeTerm(query);
        int position = 0;
        String suffix = "";

        TTNode currentNode = getFinalCharNode(query, position, this.root);

        if (currentNode == null) {
            return null;
        }

        int priority = currentNode.priority;   

        while (priority != currentNode.highestPriority) {

            if (currentNode.mid != null && currentNode.mid.priority == priority) {
                currentNode = currentNode.mid;
                suffix += currentNode.letter;
                continue;
            }

            if (currentNode.mid.left != null && currentNode.mid.left.priority == priority) {
                currentNode = currentNode.mid.left;
                suffix += currentNode.letter;
                continue;
            }

            if (currentNode.mid.right != null && currentNode.mid.right.priority == priority) {
                currentNode = currentNode.mid.right;
                suffix += currentNode.letter;
                continue;
            }
        }    
        return query + suffix;
    }


    // -----------------------------------------------------------
    // TTNode Internal Storage
    // -----------------------------------------------------------

    /**
     * Internal storage of autocompleter search terms
     * as represented using a Ternary Tree with TTNodes
     */
    private class TTNode {

        boolean wordEnd;
        char letter;
        TTNode left, mid, right;
        int priority;
        int highestPriority;

        /**
         * Constructs a new TTNode containing the given character
         * and whether or not it represents a word-end, which can
         * then be added to the existing tree.
         * @param c Letter to store at this node
         * @param w Whether or not this is a word-end
         */
        TTNode (char c, boolean w) {
            letter  = c;
            wordEnd = w;
        }

    }

}

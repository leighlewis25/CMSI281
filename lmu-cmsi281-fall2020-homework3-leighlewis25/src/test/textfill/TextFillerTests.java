package test.textfill;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;

import main.textfill.*;
import java.util.*;

public class TextFillerTests {
    
    // =================================================
    // Test Configuration
    // =================================================
    
    // Global timeout to prevent infinite loops from
    // crashing the test suite
    @Rule
    public Timeout globalTimeout = Timeout.seconds(2);
    
    // Used for grading, reports the total number of tests
    // passed over the total possible
    static int possible = 0, passed = 0;

    // Used as the basic empty TextFiller to test; 
    // the @Before method is run before every @Test
    TernaryTreeTextFiller tf;
    @Before
    public void init () {
        possible++;
        tf = new TernaryTreeTextFiller();
    }
    
    // Each time you pass a test, you get a point! Yay!
    // [!] Requires JUnit 4+ to run
    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            passed++;
        }
    };
    
    @AfterClass
    public static void gradeReport () {
        System.out.println("============================");
        System.out.println("Tests Complete");
        System.out.println(passed + " / " + possible + " passed!");
        if ((1.0 * passed / possible) >= 0.9) {
            System.out.println("[!] Nice job!"); // Automated acclaim!
        }
        System.out.println("============================");
    }
    
    
    // =================================================
    // Unit Tests
    // =================================================
    
    // Initialization Tests
    // -------------------------------------------------
    @Test
    public void testAutocompleter() {
        assertTrue(tf.empty());
    }

    // Basic Tests
    // -------------------------------------------------
    @Test
    public void testAdd_t0() {
        tf.add("is");
        tf.add("it");
        tf.add("as");
        tf.add("ass");
        tf.add("at");
        tf.add("bat");
    }
    
    @Test
    public void testSize_t0() {
        tf.add("is");
        tf.add("it");
        tf.add("as");
        assertEquals(3, tf.size());
        tf.add("as");
        assertEquals(3, tf.size());
    }

    @Test
    public void testContains_t0() {
        tf.add("is");
        tf.add("it");
        tf.add("as");
        tf.add("ass");
        tf.add("at");
        tf.add("bat");
        assertTrue(tf.contains("is"));
        assertTrue(tf.contains("it"));
        assertTrue(tf.contains("as"));
        assertTrue(tf.contains("ass"));
        assertTrue(tf.contains("at"));
        assertTrue(tf.contains("bat"));
        assertFalse(tf.contains("ii"));
        assertFalse(tf.contains("i"));
        assertFalse(tf.contains("zoo"));
    }

    @Test
    public void testTextFill_t0() {
        tf.add("is");
        tf.add("it");
        tf.add("as");
        tf.add("at");
        tf.add("item");
        tf.add("ass");
        tf.add("bat");
        tf.add("bother");
        tf.add("goat");
        tf.add("goad");
        assertEquals("is", tf.textFill("is"));
        assertEquals("it", tf.textFill("it"));
        assertEquals("item", tf.textFill("ite"));
        assertEquals("as", tf.textFill("as"));
        assertEquals("bat", tf.textFill("ba"));
        assertEquals("bother", tf.textFill("bo"));
        assertEquals(null, tf.textFill("bad"));
        assertEquals(null, tf.textFill("zoo"));
        String result = tf.textFill("go");
        assertTrue(result.equals("goat") || result.equals("goad"));
    }
    
    @Test
    public void testGetSortedList_t0() {
        tf.add("is");
        tf.add("it");
        tf.add("as");
        tf.add("itinerary");
        tf.add("ass");
        tf.add("at");
        tf.add("zoo");
        tf.add("bat");
        tf.add("bother");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "as", "ass", "at", "bat", "bother", "is", "it", "itinerary", "zoo"
        ));
        assertEquals(solution, tf.getSortedList());
    }
    

    @Test
    public void testAdd_2() {
        tf.add("is");
        tf.add("bat");
        tf.add("apple");
        tf.add("am");
        tf.add("at");
        tf.add("bar");
        assertTrue(tf.contains("is"));
        assertTrue(tf.contains("bat"));
        assertTrue(tf.contains("apple"));
        assertTrue(tf.contains("am"));
        assertTrue(tf.contains("at"));
        assertTrue(tf.contains("bar"));
        assertFalse(tf.contains("i"));
        assertFalse(tf.contains("ba"));
        assertFalse(tf.contains("bam"));
    }

    @Test
    public void testAdd_3() {
        tf.add("ba");
        tf.add("bar");
        tf.add("barry");
        assertTrue(tf.contains("ba"));
        assertTrue(tf.contains("bar"));
        assertTrue(tf.contains("barry"));
        assertFalse(tf.contains("barr"));
    }


    @Test
    public void textFill() {
        tf.add("important");
        tf.add("animal");
        tf.add("already");
        tf.add("alright");
        tf.add("item");
        assertEquals("important", tf.textFill("im"));
        assertEquals("animal", tf.textFill("an"));
        assertEquals("already", tf.textFill("alrea"));
        assertEquals("alright", tf.textFill("alri"));
        assertEquals("item", tf.textFill("it"));
        assertEquals(null, tf.textFill("bam"));
        assertEquals(null, tf.textFill("zoom"));
    }
    @Test
    public void textFill_t2() {
        tf.add("ab");
        tf.add("about");
        tf.add("abort");
        String result = tf.textFill("a");
        assertTrue(result.equals("ab") || result.equals("about") || result.equals("abort"));
        result = tf.textFill("ab");
        assertTrue(result.equals("ab") || result.equals("about") || result.equals("abort"));
        result = tf.textFill("abo");
        assertTrue(result.equals("about") || result.equals("abort"));
        assertEquals("about", tf.textFill("abou"));
        assertEquals("abort", tf.textFill("abor"));
        assertEquals("abort", tf.textFill("abort"));
        assertEquals(null, tf.textFill("abortting"));
        assertEquals(null, tf.textFill("zzz"));
    }
    @Test
    public void textFill_t3() {
        assertEquals(null, tf.textFill("a"));
        assertEquals(null, tf.textFill("aa"));
    }

    @Test
    public void getSortedList_t2() {
        ArrayList<String> solution = new ArrayList<String>();
        assertEquals(solution, tf.getSortedList());
    }

 

   
}

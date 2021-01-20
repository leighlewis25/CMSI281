package test.textfill;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import main.textfill.*;

/**
 * Note!
 * 
 * These tests are only for those endeavoring to complete the extra
 * credit portion of the assignment!
 */
public class TextFillerExtraTests {
    
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
    
    // Basic Tests
    // -------------------------------------------------
    @Test
    public void testAddPriority_t0() {
        tf.add("is", 2);
        tf.add("it", 3);
        tf.add("as", 3);
        tf.add("ass", 4);
        tf.add("at", 2);
        tf.add("bat", 1);
        assertEquals(6, tf.size());
    }
    
    @Test
    public void testTextFillPremium_t0() {
        tf.add("is", 2);
        tf.add("it", 3);
        tf.add("as", 3);
        tf.add("ass", 4);
        tf.add("at", 2);
        tf.add("bat", 1);
        assertEquals(null, tf.textFillPremium("z"));
        assertEquals(null, tf.textFillPremium("zap"));
        assertEquals("it", tf.textFillPremium("i"));
        assertEquals("is", tf.textFillPremium("is"));
        assertEquals("ass", tf.textFillPremium("a"));
        assertEquals("ass", tf.textFillPremium("as"));
        assertEquals("ass", tf.textFillPremium("ass"));
        assertEquals("at", tf.textFillPremium("at"));
        assertEquals("bat", tf.textFillPremium("b"));
    }
    
    @Test
    public void testTextFillPremium_t1() {
        tf.add("is", 2);
        tf.add("it", 3);
        tf.add("as", 3);
        tf.add("ass", 4);
        tf.add("at", 2);
        tf.add("bat", 1);
        tf.add("batch", 3);
        tf.add("irk", 5);
        tf.add("art", 5);
        tf.add("asp", 3);
        System.out.println(tf.getSortedList());
        assertEquals("batch", tf.textFillPremium("bat"));
        assertEquals("irk", tf.textFillPremium("i"));
        assertEquals("art", tf.textFillPremium("a"));
        assertEquals("ass", tf.textFillPremium("as"));
    }
    
    @Test
    public void testTextFillPremium_t2() {
        tf.add("a", 1);
        tf.add("ab", 2);
        tf.add("abate", 3);
        tf.add("act", 4);
        tf.add("activate", 5);
        tf.add("active", 4);
        assertEquals("activate", tf.textFillPremium("a"));
        assertEquals("activate", tf.textFillPremium("act"));
        assertEquals("activate", tf.textFillPremium("acti"));
        assertEquals("active", tf.textFillPremium("active"));
        assertEquals("abate", tf.textFillPremium("ab"));
        assertEquals("abate", tf.textFillPremium("aba"));
        assertEquals(null, tf.textFillPremium("activae"));
    }
    
    @Test
    public void testTextFillPremium_t3() {
        tf.add("mat", 1);
        tf.add("match", 1);
        tf.add("merch", 1);
        tf.add("ab", 1);
        tf.add("act", 1);
        tf.add("absolve", 1);
        tf.add("zen", 1);
        tf.add("zee", 1);
        System.out.println(tf.getSortedList());
        assertEquals("match", tf.textFillPremium("matc"));
        assertEquals("merch", tf.textFillPremium("me"));
        assertEquals(null, tf.textFillPremium("zeee"));
        assertEquals(null, tf.textFillPremium("mab"));
        String result = tf.textFillPremium("ma");
        assertTrue(result.equals("mat") || result.equals("match"));
        result = tf.textFillPremium("ab");
        assertTrue(result.equals("ab") || result.equals("absolve"));
        result = tf.textFillPremium("z");
        assertTrue(result.equals("zen") || result.equals("zee"));
    }
    
    @Test
    public void testTextFillPremium_t4() {
        tf.add("m", 1);
        tf.add("a", 2);
        tf.add("z", 2);
        tf.add("b", 3);
        tf.add("c", 3);
        assertEquals("m", tf.textFillPremium("m"));
        assertEquals("a", tf.textFillPremium("a"));
        assertEquals(null, tf.textFillPremium("x"));
    }
    @Test
    public void testTextFillPremium_t5() {
        tf.add("a", 1);
        tf.add("ab", 3);
        tf.add("absolve", 2);
        tf.add("absolved", 4);
        assertEquals("absolved", tf.textFillPremium("a"));
        assertEquals("absolved", tf.textFillPremium("ab"));
        assertEquals("absolved", tf.textFillPremium("abs"));
    }
    @Test
    public void testTextFillPremium_t6() {
        tf.add("a", 4);
        tf.add("ab", 1);
        tf.add("absolve", 3);
        tf.add("absolved", 2);
        assertEquals("a", tf.textFillPremium("a"));
        assertEquals("absolve", tf.textFillPremium("ab"));
        assertEquals("absolve", tf.textFillPremium("abs"));
        assertEquals("absolved", tf.textFillPremium("absolved"));
    }
    @Test
    public void testTextFillPremium_t7() {
        tf.add("a", 4);
        tf.add("ab", 3);
        tf.add("absolve", 2);
        tf.add("absolved", 1);
        assertEquals("a", tf.textFillPremium("a"));
        assertEquals("ab", tf.textFillPremium("ab"));
        assertEquals("absolve", tf.textFillPremium("abs"));
        assertEquals("absolved", tf.textFillPremium("absolved"));
    }
    @Test
    public void testTextFillPremium_t8() {
        tf.add("m", 1);
        tf.add("a", 2);
        tf.add("z", 2);
        tf.add("b", 3);
        tf.add("y", 3);
        assertEquals("m", tf.textFillPremium("m"));
        assertEquals("a", tf.textFillPremium("a"));
        assertEquals("b", tf.textFillPremium("b"));
        assertEquals("z", tf.textFillPremium("z"));
        assertEquals("y", tf.textFillPremium("y"));
    }
    
    @Test
    public void testGetSortedList_t0() {
        tf.add("is", 0);
        tf.add("it", 0);
        tf.add("as", 0);
        tf.add("itinerary", 0);
        tf.add("ass", 0);
        tf.add("at", 0);
        tf.add("zoo", 0);
        tf.add("bat", 0);
        tf.add("bother", 0);
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "as", "ass", "at", "bat", "bother", "is", "it", "itinerary", "zoo"
        ));
        System.out.println(tf.getSortedList());
        System.out.println(tf.size());
        assertEquals(solution, tf.getSortedList());
    }
    
}

package test.textfill;

import static org.junit.Assert.*;

import java.util.*;
import main.textfill.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;

public class TextFillerGradingTests {
    
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
        assertEquals(0, tf.size());
    }

    // Basic Tests
    // -------------------------------------------------
    @Test
    public void testAddAndSize_t0() {
        tf.add("is");
        tf.add("it");
        tf.add("as");
        tf.add("ass");
        tf.add("at");
        tf.add("bat");
        assertEquals(6, tf.size());
    }
    @Test
    public void testAddAndSize_t1() {
        tf.add("is");
        tf.add("is");
        assertEquals(1, tf.size());
    }
    @Test
    public void testAddAndSize_t2() {
        tf.add("it");
        tf.add("item");
        tf.add("items");
        assertEquals(3, tf.size());
    }
    @Test
    public void testAddAndSize_t3() {
        tf.add("items");
        tf.add("item");
        tf.add("it");
        assertEquals(3, tf.size());
    }
    @Test
    public void testAddAndSize_t4() {
        tf.add("items");
        tf.add("item");
        tf.add("item");
        tf.add("it");
        assertEquals(3, tf.size());
    }

    @Test
    public void testContains() {
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
        assertFalse(tf.contains("ba"));
        assertFalse(tf.contains("zoo"));
    }
    @Test
    public void testContains_t1() {
        assertFalse(tf.contains("a"));
        assertFalse(tf.contains("aa"));
        try {
            tf.add("");
        } catch (Exception e) {
            if (!(e instanceof IllegalArgumentException)) {
                fail();
            }
        }
    }
    @Test
    public void testContains_t2() {
        tf.add("as");
        tf.add("at");
        tf.add("bass");
        tf.add("bat");
        assertTrue(tf.contains("as"));
        assertTrue(tf.contains("at"));
        assertTrue(tf.contains("bass"));
        assertTrue(tf.contains("bat"));
        assertFalse(tf.contains("ba"));
    }
    @Test
    public void testContains_t3() {
        tf.add("a");
        tf.add("at");
        tf.add("attack");
        tf.add("attacking");
        assertTrue(tf.contains("a"));
        assertTrue(tf.contains("at"));
        assertTrue(tf.contains("attack"));
        assertTrue(tf.contains("attacking"));
        assertFalse(tf.contains("attac"));
    }
    @Test
    public void testContains_t4() {
        tf.add("bit");
        tf.add("bite");
        tf.add("bitter");
        tf.add("bit");
        tf.add("bite");
        assertTrue(tf.contains("bit"));
        assertTrue(tf.contains("bite"));
        assertTrue(tf.contains("bitter"));
        assertFalse(tf.contains("bi"));
        assertFalse(tf.contains("bitt"));
    }
    @Test
    public void testContains_t5() {
        tf.add("at");
        tf.add("ab");
        tf.add("about");
        tf.add("abominable");
        tf.add("abomination");
        tf.add("about");
        assertTrue(tf.contains("at"));
        assertTrue(tf.contains("ab"));
        assertTrue(tf.contains("about"));
        assertTrue(tf.contains("abominable"));
        assertTrue(tf.contains("abomination"));
        assertFalse(tf.contains("a"));
        assertFalse(tf.contains("abomin"));
    }
    @Test
    public void testContains_t6() {
        tf.add("attic");
        tf.add("baffled");
        tf.add("catastrophe");
        tf.add("demolished");
        tf.add("eliminated");
        assertTrue(tf.contains("attic"));
        assertTrue(tf.contains("baffled"));
        assertTrue(tf.contains("catastrophe"));
        assertTrue(tf.contains("demolished"));
        assertTrue(tf.contains("eliminated"));
        assertFalse(tf.contains("a"));
        assertFalse(tf.contains("b"));
        assertFalse(tf.contains("c"));
        assertFalse(tf.contains("d"));
        assertFalse(tf.contains("e"));
    }
    @Test
    public void testContains_t7() {
        tf.add("catastrophe");
        tf.add("attic");
        tf.add("demolished");
        tf.add("baffled");
        tf.add("eliminated");
        assertTrue(tf.contains("attic"));
        assertTrue(tf.contains("baffled"));
        assertTrue(tf.contains("catastrophe"));
        assertTrue(tf.contains("demolished"));
        assertTrue(tf.contains("eliminated"));
        assertFalse(tf.contains("a"));
        assertFalse(tf.contains("b"));
        assertFalse(tf.contains("c"));
        assertFalse(tf.contains("d"));
        assertFalse(tf.contains("e"));
    }
    @Test
    public void testContains_t8() {
        tf.add("I");
        tf.add("integer");
        tf.add("integral");
        tf.add("bar");
        tf.add("barometer");
        tf.add("zoo");
        tf.add("zoologist");
        tf.add("zen");
        assertTrue(tf.contains("I"));
        assertTrue(tf.contains("integer"));
        assertTrue(tf.contains("integral"));
        assertTrue(tf.contains("bar"));
        assertTrue(tf.contains("barometer"));
        assertTrue(tf.contains("zoo"));
        assertTrue(tf.contains("zoologist"));
        assertTrue(tf.contains("zen"));
        assertFalse(tf.contains("in"));
        assertFalse(tf.contains("zool"));
        assertFalse(tf.contains("baro"));
    }
    @Test
    public void testContains_t9() {
        tf.add("integral");
        tf.add("integer");
        tf.add("I");
        tf.add("barometer");
        tf.add("bar");
        tf.add("zoologist");
        tf.add("zoo");
        tf.add("zen");
        assertTrue(tf.contains("I"));
        assertTrue(tf.contains("integer"));
        assertTrue(tf.contains("integral"));
        assertTrue(tf.contains("bar"));
        assertTrue(tf.contains("barometer"));
        assertTrue(tf.contains("zoo"));
        assertTrue(tf.contains("zoologist"));
        assertTrue(tf.contains("zen"));
        assertFalse(tf.contains("in"));
        assertFalse(tf.contains("zool"));
        assertFalse(tf.contains("baro"));
    }
    @Test
    public void testContains_t10() {
        tf.add("integral");
        tf.add("integer");
        tf.add("I");
        tf.add("barometer");
        tf.add("bar");
        tf.add("zoologist");
        tf.add("zoo");
        tf.add("zen");
        tf.add("I");
        tf.add("bar");
        tf.add("zoo");
        assertTrue(tf.contains("I"));
        assertTrue(tf.contains("integer"));
        assertTrue(tf.contains("integral"));
        assertTrue(tf.contains("bar"));
        assertTrue(tf.contains("barometer"));
        assertTrue(tf.contains("zoo"));
        assertTrue(tf.contains("zoologist"));
        assertTrue(tf.contains("zen"));
        assertFalse(tf.contains("in"));
        assertFalse(tf.contains("zool"));
        assertFalse(tf.contains("baro"));
    }
    @Test
    public void testContains_t11() {
        tf.add("carve");
        tf.add("canned");
        tf.add("add");
        tf.add("argue");
        tf.add("bar");
        tf.add("bad");
        tf.add("sour");
        tf.add("sorted");
        tf.add("tent");
        tf.add("tended");
        assertTrue(tf.contains("carve"));
        assertTrue(tf.contains("canned"));
        assertTrue(tf.contains("add"));
        assertTrue(tf.contains("argue"));
        assertTrue(tf.contains("bar"));
        assertTrue(tf.contains("bad"));
        assertTrue(tf.contains("sour"));
        assertTrue(tf.contains("sorted"));
        assertTrue(tf.contains("tent"));
        assertTrue(tf.contains("tended"));
        assertFalse(tf.contains("ar"));
        assertFalse(tf.contains("sou"));
        assertFalse(tf.contains("sor"));
    }
    @Test
    public void testContains_t12() {
        tf.add("a");
        tf.add("aa");
        tf.add("aaa");
        tf.add("aaaaa");
        assertTrue(tf.contains("a"));
        assertTrue(tf.contains("aa"));
        assertTrue(tf.contains("aaa"));
        assertFalse(tf.contains("aaaa"));
        assertTrue(tf.contains("aaaaa"));
    }
    @Test
    public void testContains_t13() {
        tf.add("ab");
        tf.add("ace");
        tf.add("academy");
        tf.add("acadeem");
        assertTrue(tf.contains("ab"));
        assertTrue(tf.contains("ace"));
        assertTrue(tf.contains("academy"));
        assertTrue(tf.contains("acadeem"));
        assertFalse(tf.contains("aca"));
        assertFalse(tf.contains("academ"));
    }
    @Test
    public void testContains_t14() {
        tf.add("a");
        tf.add("z");
        tf.add("b");
        tf.add("y");
        tf.add("c");
        tf.add("x");
        assertTrue(tf.contains("a"));
        assertTrue(tf.contains("b"));
        assertTrue(tf.contains("c"));
        assertTrue(tf.contains("x"));
        assertTrue(tf.contains("y"));
        assertTrue(tf.contains("z"));
        assertFalse(tf.contains("ab"));
        assertFalse(tf.contains("az"));
        assertFalse(tf.contains("zb"));
    }

    @Test
    public void textFill() {
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
    public void textFill_t1() {
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
    public void textFill_t2() {
        tf.add("bit");
        tf.add("bite");
        tf.add("bitter");
        tf.add("bit");
        tf.add("bite");
        String result = tf.textFill("bit");
        assertTrue(result.equals("bit") || result.equals("bite") || result.equals("bitter"));
        assertEquals("bite", tf.textFill("bite"));
        assertEquals("bitter", tf.textFill("bitt"));
    }
    @Test
    public void textFill_t3() {
        tf.add("attic");
        tf.add("baffled");
        tf.add("catastrophe");
        tf.add("demolished");
        tf.add("eliminated");
        assertEquals("attic", tf.textFill("a"));
        assertEquals("attic", tf.textFill("attic"));
        assertEquals(null, tf.textFill("attics"));
        assertEquals("baffled", tf.textFill("b"));
        assertEquals("catastrophe", tf.textFill("c"));
        assertEquals("demolished", tf.textFill("d"));
        assertEquals("eliminated", tf.textFill("e"));
    }
    @Test
    public void textFill_t4() {
        tf.add("catastrophe");
        tf.add("attic");
        tf.add("demolished");
        tf.add("baffled");
        tf.add("eliminated");
        assertEquals("attic", tf.textFill("a"));
        assertEquals("attic", tf.textFill("attic"));
        assertEquals(null, tf.textFill("attics"));
        assertEquals("baffled", tf.textFill("b"));
        assertEquals("catastrophe", tf.textFill("c"));
        assertEquals("demolished", tf.textFill("d"));
        assertEquals("eliminated", tf.textFill("e"));
    }
    @Test
    public void textFill_t5() {
        tf.add("I");
        tf.add("integer");
        tf.add("integral");
        tf.add("bar");
        tf.add("barometer");
        tf.add("zoo");
        tf.add("zoologist");
        tf.add("zen");
        String result = tf.textFill("I");
        assertTrue(result.equals("I") || result.equals("i") || result.equals("integer") || result.equals("integral"));
        result = tf.textFill("int");
        assertTrue(result.equals("integer") || result.equals("integral"));
        assertEquals("integer", tf.textFill("intege"));
        assertEquals("integral", tf.textFill("integr"));
        assertEquals(null, tf.textFill("integet"));
        result = tf.textFill("b");
        assertTrue(result.equals("bar") || result.equals("barometer"));
        result = tf.textFill("bar");
        assertTrue(result.equals("bar") || result.equals("barometer"));
        assertEquals("barometer", tf.textFill("baro"));
        assertEquals(null, tf.textFill("barro"));
        result = tf.textFill("zoo");
        assertTrue(result.equals("zoo") || result.equals("zoologist"));
        assertEquals("zoologist", tf.textFill("zoolo"));
        assertEquals("zoologist", tf.textFill("zoologist"));
        assertEquals("zen", tf.textFill("ze"));
        assertEquals(null, tf.textFill("a"));
    }
    @Test
    public void textFill_t6() {
        tf.add("integer");
        tf.add("integral");
        tf.add("barometer");
        tf.add("zoologist");
        tf.add("zen");
        tf.add("I");
        tf.add("bar");
        tf.add("zoo");
        String result = tf.textFill("I");
        assertTrue(result.equals("I") || result.equals("i") || result.equals("integer") || result.equals("integral"));
        result = tf.textFill("int");
        assertTrue(result.equals("integer") || result.equals("integral"));
        assertEquals("integer", tf.textFill("intege"));
        assertEquals("integral", tf.textFill("integr"));
        assertEquals(null, tf.textFill("integet"));
        result = tf.textFill("b");
        assertTrue(result.equals("bar") || result.equals("barometer"));
        result = tf.textFill("bar");
        assertTrue(result.equals("bar") || result.equals("barometer"));
        assertEquals("barometer", tf.textFill("baro"));
        assertEquals(null, tf.textFill("barro"));
        result = tf.textFill("zoo");
        assertTrue(result.equals("zoo") || result.equals("zoologist"));
        assertEquals("zoologist", tf.textFill("zoolo"));
        assertEquals("zoologist", tf.textFill("zoologist"));
        assertEquals("zen", tf.textFill("ze"));
        assertEquals(null, tf.textFill("a"));
    }
    @Test
    public void textFill_t7() {
        try {
            tf.textFill("");
        } catch (Exception e) {
            if (!(e instanceof IllegalArgumentException)) {
                fail();
            }
        }
    }
    @Test
    public void textFill_t8() {
        assertEquals(null, tf.textFill("a"));
        assertEquals(null, tf.textFill("aa"));
    }
    @Test
    public void textFill_t9() {
        tf.add("carve");
        tf.add("canned");
        tf.add("add");
        tf.add("argue");
        tf.add("bar");
        tf.add("bad");
        tf.add("sour");
        tf.add("sorted");
        tf.add("tent");
        tf.add("tended");
        assertEquals("carve", tf.textFill("car"));
        assertEquals("canned", tf.textFill("can"));
        assertEquals("add", tf.textFill("ad"));
        assertEquals("argue", tf.textFill("ar"));
        assertEquals("bar", tf.textFill("bar"));
        assertEquals("sorted", tf.textFill("sor"));
        assertEquals("tended", tf.textFill("tend"));
    }
    @Test
    public void textFill_t10() {
        tf.add("ab");
        tf.add("ace");
        tf.add("academy");
        tf.add("acadeem");
        assertEquals("ab", tf.textFill("ab"));
        assertEquals("ace", tf.textFill("ace"));
        assertEquals("academy", tf.textFill("academ"));
        assertEquals("acadeem", tf.textFill("acadee"));
        String result = tf.textFill("aca");
        assertTrue(result.equals("academy") || result.equals("acadeem"));
        assertEquals(null, tf.textFill("acadeemy"));
    }
    @Test
    public void textFill_t11() {
        tf.add("a");
        tf.add("z");
        tf.add("b");
        tf.add("y");
        tf.add("c");
        tf.add("x");
        assertEquals("a", tf.textFill("a"));
        assertEquals("z", tf.textFill("z"));
        assertEquals("b", tf.textFill("b"));
        assertEquals("y", tf.textFill("y"));
        assertEquals(null, tf.textFill("az"));
        assertEquals(null, tf.textFill("d"));
        assertEquals(null, tf.textFill("ab"));
        assertEquals(null, tf.textFill("by"));
    }
    
    @Test
    public void getSortedList() {
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
    public void getSortedList_t1() {
        ArrayList<String> solution = new ArrayList<String>();
        assertEquals(solution, tf.getSortedList());
    }
    @Test
    public void getSortedList_t2() {
        tf.add("a");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList("a"));
        assertEquals(solution, tf.getSortedList());
    }
    @Test
    public void getSortedList_t3() {
        tf.add("a");
        tf.add("b");
        tf.add("e");
        tf.add("d");
        tf.add("c");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "a", "b", "c", "d", "e"
        ));
        assertEquals(solution, tf.getSortedList());
    }
    @Test
    public void getSortedList_t4() {
        tf.add("catastrophe");
        tf.add("attic");
        tf.add("demolished");
        tf.add("baffled");
        tf.add("eliminated");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "attic", "baffled", "catastrophe", "demolished", "eliminated"
        ));
        assertEquals(solution, tf.getSortedList());
    }
    @Test
    public void getSortedList_t5() {
        tf.add("bit");
        tf.add("bite");
        tf.add("biter");
        tf.add("bitter");
        tf.add("bit");
        tf.add("bite");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "bit", "bite", "biter", "bitter"
        ));
        assertEquals(solution, tf.getSortedList());
    }
    @Test
    public void getSortedList_t6() {
        tf.add("i");
        tf.add("integer");
        tf.add("integral");
        tf.add("bar");
        tf.add("barometer");
        tf.add("zoo");
        tf.add("zoologist");
        tf.add("zen");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "bar", "barometer", "i", "integer", "integral", "zen", "zoo", "zoologist"
        ));
        assertEquals(solution, tf.getSortedList());
    }
    @Test
    public void getSortedList_t7() {
        tf.add("integer");
        tf.add("integral");
        tf.add("barometer");
        tf.add("zoologist");
        tf.add("zen");
        tf.add("i");
        tf.add("bar");
        tf.add("zoo");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "bar", "barometer", "i", "integer", "integral", "zen", "zoo", "zoologist"
        ));
        assertEquals(solution, tf.getSortedList());
    }
    @Test
    public void getSortedList_t8() {
        tf.add("carve");
        tf.add("canned");
        tf.add("add");
        tf.add("argue");
        tf.add("bar");
        tf.add("bad");
        tf.add("sour");
        tf.add("sorted");
        tf.add("tent");
        tf.add("tended");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "add", "argue", "bad", "bar", "canned", "carve", "sorted", "sour", "tended", "tent" 
        ));
        assertEquals(solution, tf.getSortedList());
    }
    @Test
    public void getSortedList_t9() {
        tf.add("a");
        tf.add("z");
        tf.add("b");
        tf.add("y");
        tf.add("c");
        tf.add("x");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "a", "b", "c", "x", "y", "z"
        ));
        assertEquals(solution, tf.getSortedList());
    }
    @Test
    public void getSortedList_t10() {
        tf.add("b");
        tf.add("bb");
        tf.add("a");
        tf.add("aa");
        tf.add("c");
        tf.add("cc");
        ArrayList<String> solution = new ArrayList<String>(Arrays.asList(
            "a", "aa", "b", "bb", "c", "cc"
        ));
        assertEquals(solution, tf.getSortedList());
    }
    
}

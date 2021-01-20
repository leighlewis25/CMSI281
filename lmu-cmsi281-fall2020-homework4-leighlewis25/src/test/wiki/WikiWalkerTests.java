package test.wiki;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;

import main.wiki.*;

public class WikiWalkerTests {
    
    // =================================================
    // Test Configuration
    // =================================================
    
    // Global timeout to prevent infinite loops from
    // crashing the test suite
    @Rule
    public Timeout globalTimeout = Timeout.seconds(1);
    
    // Used for grading, reports the total number of tests
    // passed over the total possible
    static int possible = 0, passed = 0;

    WikiWalker ww;
    @Before
    public void init () {
        possible++;
        ww = new WikiWalker();
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
    
    /**
     * Sets up the structure of the site map for the
     * WikiWalker used in each test
     * @param ww The WikiWalker object to setup with the
     * given 
     */
    private static void setupWW (WikiWalker ww) {
        ww.addArticle("A", Arrays.asList("B", "C"));
        ww.addArticle("B", Arrays.asList("A", "C"));
        ww.addArticle("C", Arrays.asList("D", "E"));
        ww.addArticle("D", Arrays.asList("B", "F"));
        ww.addArticle("E", Arrays.asList("F"));
    }
    private static void setupWWTree (WikiWalker ww) {
        ww.addArticle("A", Arrays.asList("B", "C", "D"));
        ww.addArticle("B", Arrays.asList("E", "F"));
        ww.addArticle("C", Arrays.asList("G", "H"));
        ww.addArticle("D", Arrays.asList("I"));
        ww.addArticle("H", Arrays.asList("K"));
    }
    private static void setupWWCycle (WikiWalker ww) {
        ww.addArticle("A", Arrays.asList("B"));
        ww.addArticle("B", Arrays.asList("C"));
        ww.addArticle("C", Arrays.asList("D"));
        ww.addArticle("D", Arrays.asList("A"));
    }
    private static void setupWWIslands (WikiWalker ww) {
        ww.addArticle("A", Arrays.asList("B", "C"));
        ww.addArticle("B", Arrays.asList("C", "D"));
        ww.addArticle("D", Arrays.asList("E"));
        ww.addArticle("F", Arrays.asList("G", "H"));
        ww.addArticle("G", Arrays.asList("K"));
    }
    
    final static int LATTICE_SIZE = 1000;
    private static void setupBIGLattice (WikiWalker ww) {
        for (int i = 0; i < LATTICE_SIZE; i++) {
            ww.addArticle("" + i, Arrays.asList("" + (i+1), "" + (i+2)));
        }
    }

    
    // =================================================
    // Unit Tests
    // =================================================

    @Test
    public void testAddArticle() {
        setupWW(ww);
    }
    
    @Test
    public void testHasPath() {
        setupWW(ww);
        
        assertTrue(ww.hasPath("A", "A"));
        assertTrue(ww.hasPath("A", "B"));
        assertTrue(ww.hasPath("B", "A"));
        assertTrue(ww.hasPath("A", "F"));
        assertTrue(ww.hasPath("E", "F"));
        assertTrue(ww.hasPath("D", "E"));
        assertFalse(ww.hasPath("F", "E"));
        assertFalse(ww.hasPath("E", "D"));
    }
    @Test
    public void testHasPath_t1() {
        setupWWTree(ww);
        
        assertTrue(ww.hasPath("A", "A"));
        assertTrue(ww.hasPath("A", "B"));
        assertTrue(ww.hasPath("B", "F"));
        assertTrue(ww.hasPath("A", "K"));
        assertTrue(ww.hasPath("C", "K"));
        assertFalse(ww.hasPath("E", "D"));
        assertFalse(ww.hasPath("B", "A"));
        assertFalse(ww.hasPath("E", "F"));
    }
    @Test
    public void testHasPath_t2() {
        setupWWCycle(ww);
        
        assertTrue(ww.hasPath("A", "A"));
        assertTrue(ww.hasPath("A", "B"));
        assertTrue(ww.hasPath("B", "B"));
        assertTrue(ww.hasPath("B", "A"));
        assertTrue(ww.hasPath("C", "A"));
    }
    @Test
    public void testHasPath_t3() {
        setupWWIslands(ww);
        
        assertTrue(ww.hasPath("A", "A"));
        assertTrue(ww.hasPath("A", "B"));
        assertTrue(ww.hasPath("B", "E"));
        assertFalse(ww.hasPath("B", "A"));
        assertFalse(ww.hasPath("A", "F"));
        assertFalse(ww.hasPath("F", "A"));
        assertFalse(ww.hasPath("G", "D"));
        assertFalse(ww.hasPath("D", "G"));
    }
    
    @Test
    public void testClickthroughs() {
        setupWW(ww);
        assertEquals(0, ww.clickthroughs("A", "B"));
        assertEquals(0, ww.clickthroughs("B", "A"));
        assertEquals(-1, ww.clickthroughs("A", "A"));
        assertEquals(-1, ww.clickthroughs("A", "D"));
    }
    @Test
    public void testClickthroughs_t1() {
        setupWWTree(ww);
        assertEquals(0, ww.clickthroughs("A", "B"));
        assertEquals(0, ww.clickthroughs("D", "I"));
        assertEquals(-1, ww.clickthroughs("B", "C"));
        assertEquals(-1, ww.clickthroughs("C", "K"));
        assertEquals(-1, ww.clickthroughs("B", "A"));
        assertEquals(-1, ww.clickthroughs("A", "A"));
    }
    
    @Test
    public void testTrajectories() {
        setupWW(ww);
        
        ww.logTrajectory(Arrays.asList("A", "B", "C", "D"));
        ww.logTrajectory(Arrays.asList("A", "C", "D", "F"));
        assertEquals(1, ww.clickthroughs("A", "B"));
        assertEquals(1, ww.clickthroughs("A", "C"));
        assertEquals(1, ww.clickthroughs("B", "C"));
        assertEquals(2, ww.clickthroughs("C", "D"));
        
        ww.logTrajectory(Arrays.asList("A", "B", "A", "B", "C"));
        assertEquals(3, ww.clickthroughs("A", "B"));
        assertEquals(1, ww.clickthroughs("B", "A"));
        assertEquals(2, ww.clickthroughs("B", "C"));
    }
    @Test
    public void testTrajectories_t1() {
        setupWWTree(ww);
        
        ww.logTrajectory(Arrays.asList("A", "B", "F"));
        ww.logTrajectory(Arrays.asList("A", "B", "E"));
        ww.logTrajectory(Arrays.asList("C", "H", "K"));
        ww.logTrajectory(Arrays.asList("D", "I"));
        assertEquals(2, ww.clickthroughs("A", "B"));
        assertEquals(1, ww.clickthroughs("D", "I"));
        assertEquals(1, ww.clickthroughs("B", "F"));
        assertEquals(1, ww.clickthroughs("B", "E"));
        assertEquals(-1, ww.clickthroughs("B", "A"));
    }
    @Test
    public void testTrajectories_t2() {
        setupWWCycle(ww);
        
        ww.logTrajectory(Arrays.asList("A", "B"));
        ww.logTrajectory(Arrays.asList("B", "C", "D"));
        ww.logTrajectory(Arrays.asList("C", "D", "A", "B", "C", "D", "A", "B", "C"));
        assertEquals(3, ww.clickthroughs("A", "B"));
        assertEquals(3, ww.clickthroughs("B", "C"));
        assertEquals(3, ww.clickthroughs("C", "D"));
        assertEquals(3, ww.clickthroughs("C", "D"));
        assertEquals(2, ww.clickthroughs("D", "A"));
        assertEquals(-1, ww.clickthroughs("B", "A"));
    }
    @Test
    public void testTrajectories_t3() {
        setupWWIslands(ww);
        
        ww.logTrajectory(Arrays.asList("A", "B", "C"));
        ww.logTrajectory(Arrays.asList("A", "B", "D", "E"));
        ww.logTrajectory(Arrays.asList("B", "D", "E"));
        ww.logTrajectory(Arrays.asList("F", "G"));
        ww.logTrajectory(Arrays.asList("G", "K"));
        assertEquals(2, ww.clickthroughs("A", "B"));
        assertEquals(2, ww.clickthroughs("B", "D"));
        assertEquals(1, ww.clickthroughs("F", "G"));
        assertEquals(1, ww.clickthroughs("G", "K"));
        assertEquals(-1, ww.clickthroughs("D", "B"));
        assertEquals(-1, ww.clickthroughs("B", "A"));
    }
    
    @Test
    public void testMostLikelyTrajectory() {
        setupWW(ww);
        
        assertEquals(Arrays.asList("B"), ww.mostLikelyTrajectory("A", 1));
        assertEquals(Arrays.asList("B", "A"), ww.mostLikelyTrajectory("A", 2));
        assertEquals(Arrays.asList("B", "A", "B"), ww.mostLikelyTrajectory("A", 3));
        
        ww.logTrajectory(Arrays.asList("A", "B", "C", "D"));
        ww.logTrajectory(Arrays.asList("A", "C", "D", "F"));
        ww.logTrajectory(Arrays.asList("A", "B", "A", "B", "C"));
        assertEquals(Arrays.asList("B"), ww.mostLikelyTrajectory("A", 1));
        assertEquals(Arrays.asList("B", "C"), ww.mostLikelyTrajectory("A", 2));
        assertEquals(Arrays.asList("B", "C", "D"), ww.mostLikelyTrajectory("A", 3));
        assertEquals(Arrays.asList("B", "C", "D", "F"), ww.mostLikelyTrajectory("A", 4));
        assertEquals(Arrays.asList("B", "C", "D", "F"), ww.mostLikelyTrajectory("A", 5));
        assertEquals(Arrays.asList("B", "C", "D", "F"), ww.mostLikelyTrajectory("A", 100));
    }
    @Test
    public void testMostLikelyTrajectory_t1() {
        setupWWTree(ww);
        
        ww.logTrajectory(Arrays.asList("A", "B", "F"));
        ww.logTrajectory(Arrays.asList("A", "B", "E"));
        ww.logTrajectory(Arrays.asList("C", "H", "K"));
        ww.logTrajectory(Arrays.asList("D", "I"));
        assertEquals(Arrays.asList("B"), ww.mostLikelyTrajectory("A", 1));
        assertEquals(Arrays.asList("B", "E"), ww.mostLikelyTrajectory("A", 2));
        assertEquals(Arrays.asList("B", "E"), ww.mostLikelyTrajectory("A", 3));
        assertEquals(Arrays.asList("H"), ww.mostLikelyTrajectory("C", 1));
        assertEquals(Arrays.asList("H", "K"), ww.mostLikelyTrajectory("C", 2));
        assertEquals(Arrays.asList("H", "K"), ww.mostLikelyTrajectory("C", 3));
        
        ww.logTrajectory(Arrays.asList("A", "B", "F"));
        assertEquals(Arrays.asList("B", "F"), ww.mostLikelyTrajectory("A", 3));
    }
    @Test
    public void testMostLikelyTrajectory_t2() {
        setupWWCycle(ww);
        
        ww.logTrajectory(Arrays.asList("A", "B"));
        ww.logTrajectory(Arrays.asList("B", "C", "D"));
        ww.logTrajectory(Arrays.asList("C", "D", "A", "B", "C", "D", "A", "B", "C"));
        assertEquals(Arrays.asList("B"), ww.mostLikelyTrajectory("A", 1));
        assertEquals(Arrays.asList("B", "C"), ww.mostLikelyTrajectory("A", 2));
        assertEquals(Arrays.asList("B", "C", "D"), ww.mostLikelyTrajectory("A", 3));
        assertEquals(Arrays.asList("B", "C", "D", "A"), ww.mostLikelyTrajectory("A", 4));
        assertEquals(Arrays.asList("D", "A", "B", "C"), ww.mostLikelyTrajectory("C", 4));
    }
    @Test
    public void testMostLikelyTrajectory_t3() {
        setupWWIslands(ww);
        
        ww.logTrajectory(Arrays.asList("A", "C"));
        ww.logTrajectory(Arrays.asList("A", "C"));
        ww.logTrajectory(Arrays.asList("A", "B", "D"));
        ww.logTrajectory(Arrays.asList("F", "G", "K"));
        assertEquals(Arrays.asList("C"), ww.mostLikelyTrajectory("A", 1));
        assertEquals(Arrays.asList("C"), ww.mostLikelyTrajectory("A", 2));
        assertEquals(Arrays.asList("C"), ww.mostLikelyTrajectory("A", 3));
        assertEquals(Arrays.asList("D"), ww.mostLikelyTrajectory("B", 1));
        assertEquals(Arrays.asList("D", "E"), ww.mostLikelyTrajectory("B", 2));
        assertEquals(Arrays.asList("D", "E"), ww.mostLikelyTrajectory("B", 3));
    }
    
    @Test
    public void bigBoiTest_t0() {
        setupBIGLattice(ww);
        assertTrue(ww.hasPath("0", "2"));
        assertTrue(ww.hasPath("0", "3"));
    }
    @Test
    public void bigBoiTest_t1() {
        setupBIGLattice(ww);
        assertTrue(ww.hasPath("0", "999"));
    }
    @Test
    public void bigBoiTest_t2() {
        setupBIGLattice(ww);
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < LATTICE_SIZE; i++) {
            ww.logTrajectory(Arrays.asList("" + i, "" + (i+2)));
        }
        for (int i = 2; i < LATTICE_SIZE; i += 2) {
            result.add(""+i);
        }
        assertEquals(result, ww.mostLikelyTrajectory("0", LATTICE_SIZE/2-1));
    }

}

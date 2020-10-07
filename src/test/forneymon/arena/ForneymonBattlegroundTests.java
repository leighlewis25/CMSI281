package test.forneymon.arena;

import main.forneymon.arena.*;
import main.forneymon.fmtypes.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;

import static org.junit.Assert.*;

import org.junit.AfterClass;

public class ForneymonBattlegroundTests {
    
    // =================================================
    // Test Configuration
    // =================================================
    
    // Global timeout to prevent infinite loops from
    // crashing the test suite
    // [!] Comment out the next 2 lines if you're using
    // the debugger!
    @Rule
    public Timeout globalTimeout = Timeout.seconds(1);
    
    // Grade record-keeping
    static int possible = 0, passed = 0;

    // Each time you pass a test, you get a point! Yay!
    // [!] Requires JUnit 4+ to run
    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            passed++;
        }
    };
    
    // Used as the basic empty Forneymonagerie to test; the @Before
    // method is run before every @Test
    Forneymonagerie fm1;
    @Before
    public void init () {
        possible++;
        fm1 = new Forneymonagerie();
    }
    
    // Used for grading, reports the total number of tests
    // passed over the total possible
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
    
    @Test
    public void testSize_t0() {
        assertEquals(0, fm1.size());
        fm1.collect(new Dampymon(1));
        assertEquals(1, fm1.size());
        fm1.collect(new Dampymon(2));
        assertEquals(1, fm1.size());
        fm1.collect(new Leafymon(2));
        assertEquals(2, fm1.size());
        fm1.collect(new Zappymon(2));
        assertEquals(3, fm1.size());
        fm1.collect(new Thundermon(2));
        assertEquals(4, fm1.size());
        fm1.collect(new Burnymon(2));
        assertEquals(5, fm1.size());
    }


    @Test
    public void testCollect_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        assertTrue(fm1.containsType("Dampymon"));
        assertTrue(fm1.containsType("Burnymon"));
        assertTrue(!fm1.containsType("Zappymon"));
        assertEquals(2, fm1.size());
        assertEquals("Burnymon", fm1.get(1).getFMType());
        fm1.collect(new Thundermon(3));
        assertTrue(fm1.containsType("Thundermon"));
        assertEquals(3, fm1.size());
        assertEquals("Thundermon", fm1.get(2).getFMType());
    }
    
    @Test
    public void testCollect_t1() {
        Dampymon d1 = new Dampymon(1);
        Dampymon d2 = new Dampymon(2);
        Dampymon d3 = new Dampymon(1);
        fm1.collect(d1);
        fm1.collect(d1);
        assertTrue(fm1.containsType("Dampymon"));
        assertEquals(1, fm1.get(0).getLevel());
        assertEquals(1, fm1.size());
        fm1.collect(d2);
        assertEquals(3, fm1.get(0).getLevel());
        assertEquals(1, fm1.size());
        fm1.collect(d3);
        assertEquals(4, fm1.get(0).getLevel());
        assertEquals(1, fm1.size());
    }
    
    @Test
    public void testCollect_t2() {
        Dampymon d1 = new Dampymon(1);
        Doggymon d2 = new Doggymon(2);
        Earthymon d3 = new Earthymon(1);
        Thundermon d4 = new Thundermon(1);
        Leafymon d5 = new Leafymon(1);
        Burnymon d6 = new Burnymon(1);
        fm1.collect(d1);
        fm1.collect(d2);
        fm1.collect(d3);
        fm1.collect(d4);
        fm1.collect(d5);
        fm1.collect(d6);
        assertEquals(6, fm1.size());
    }

    
    @Test
    public void testReleaseType_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        assertEquals(2, fm1.size());
        fm1.releaseType("Dampymon");
        assertEquals(1, fm1.size());
        assertEquals("Burnymon", fm1.get(0).getFMType());
        assertTrue(fm1.containsType("Burnymon"));
        assertTrue(!fm1.containsType("Dampymon"));
    }
    
    @Test
    public void testReleaseType_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Doggymon(3));
        fm1.collect(new Earthymon(4));
        fm1.collect(new Thundermon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Zappymon(1));
        assertEquals(7, fm1.size());
        fm1.releaseType("Dampymon");
        assertEquals(6, fm1.size());
        fm1.releaseType("Doggymon");
        assertEquals(5, fm1.size());
        assertEquals(1, fm1.getTypeIndex("Earthymon"));
        fm1.releaseType("Burnymon");
        fm1.releaseType("Earthymon");
        fm1.releaseType("Thundermon");
        fm1.releaseType("Zappymon");
        assertEquals(1, fm1.size());
        fm1.releaseType("Leafymon");
        assertEquals(0, fm1.size());
    }
    
    
    @Test
    public void testGet_t0() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        assertEquals(d1, fm1.get(0));
        assertEquals(b1, fm1.get(1));
    }
    
    @Test
    public void testGet_t1() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        Doggymon d2 = new Doggymon(1);
        Earthymon e1 = new Earthymon(2);
        Thundermon t1 = new Thundermon(2);
        fm1.collect(d1);
        fm1.collect(b1);
        fm1.collect(d2);
        fm1.collect(e1);
        fm1.collect(t1);
        assertEquals(d1, fm1.get(0));
        assertEquals(b1, fm1.get(1));
        assertEquals(d2, fm1.get(2));
        assertEquals(e1, fm1.get(3));
        assertEquals(t1, fm1.get(4));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetExceptions() {
        fm1.collect(new Thundermon(1));
        fm1.collect(new Zappymon(1));
        fm1.get(-1);
        fm1.get(3);
    }
    
    @Test
    public void testRemove_t0() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        assertEquals(2, fm1.size());
        fm1.remove(0);
        assertEquals(1, fm1.size());
        assertEquals(b1, fm1.get(0));
    }   
    
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveExceptions() {
        fm1.collect(new Thundermon(1));
        fm1.collect(new Zappymon(1));
        fm1.remove(5);
        fm1.remove(-2);
        fm1.remove(3);
    }    
    
    @Test
    public void testGetTypeIndexContainsType_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        assertEquals(0, fm1.getTypeIndex("Dampymon"));
        assertEquals(1, fm1.getTypeIndex("Burnymon"));
        assertEquals(-1, fm1.getTypeIndex("Leafymon"));
        assertTrue(fm1.containsType("Dampymon"));
        assertFalse(fm1.containsType("Zappymon"));
        assertFalse(fm1.containsType("Leafymon"));
    }
    
    @Test
    public void testGetTypeIndexContainsType_t2() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Doggymon(1));
        fm1.collect(new Earthymon(1));
        fm1.collect(new Thundermon(1));
        assertEquals(0, fm1.getTypeIndex("Dampymon"));
        assertEquals(1, fm1.getTypeIndex("Burnymon"));
        assertEquals(2, fm1.getTypeIndex("Leafymon"));
        assertEquals(3, fm1.getTypeIndex("Doggymon"));
        assertEquals(4, fm1.getTypeIndex("Earthymon"));
        assertEquals(5, fm1.getTypeIndex("Thundermon"));
        assertTrue(fm1.containsType("Dampymon"));
        assertTrue(fm1.containsType("Burnymon"));
        assertTrue(fm1.containsType("Leafymon"));
        assertTrue(fm1.containsType("Doggymon"));
        assertTrue(fm1.containsType("Earthymon"));
        assertTrue(fm1.containsType("Thundermon"));
        assertFalse(fm1.containsType("Zappymon"));
        
    }    
    
    @Test
    public void testRearrange_t0() {
        System.out.println("t0");
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.rearrange("Leafymon", 0);
        assertEquals(1, fm1.getTypeIndex("Dampymon"));
        assertEquals(2, fm1.getTypeIndex("Burnymon"));
        assertEquals(0, fm1.getTypeIndex("Leafymon"));
        fm1.collect(new Thundermon(2));
        fm1.rearrange("Thundermon", 2);
        assertEquals(1, fm1.getTypeIndex("Dampymon"));
        assertEquals(2, fm1.getTypeIndex("Thundermon"));
        assertEquals(3, fm1.getTypeIndex("Burnymon"));
        assertEquals(0, fm1.getTypeIndex("Leafymon"));
    }
    
    @Test
    public void testRearrange_t1() {
        fm1.collect(new Thundermon(1));
        fm1.collect(new Zappymon(1));
        fm1.collect(new Leafymon(1));
        fm1.rearrange("Leafymon", 0);
        assertEquals(0, fm1.getTypeIndex("Leafymon"));
        assertEquals(1, fm1.getTypeIndex("Thundermon"));
        assertEquals(2, fm1.getTypeIndex("Zappymon"));
        fm1.rearrange("Leafymon", 2);
        assertEquals(0, fm1.getTypeIndex("Thundermon"));
        assertEquals(2, fm1.getTypeIndex("Leafymon"));
        assertEquals(1, fm1.getTypeIndex("Zappymon"));
    }
    
    @Test
    public void testRearrange_t2() {
        fm1.collect(new Thundermon(1));
        fm1.collect(new Zappymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Dampymon(1));
        fm1.rearrange("Leafymon", 0);
        // Middle to First
        assertEquals(0, fm1.getTypeIndex("Leafymon"));
        assertEquals(1, fm1.getTypeIndex("Thundermon"));
        assertEquals(2, fm1.getTypeIndex("Zappymon"));
        assertEquals(3, fm1.getTypeIndex("Dampymon"));
        fm1.rearrange("Thundermon", 0);
        // Middle to first
        assertEquals(0, fm1.getTypeIndex("Thundermon"));
        assertEquals(1, fm1.getTypeIndex("Leafymon"));
        assertEquals(2, fm1.getTypeIndex("Zappymon"));
        assertEquals(3, fm1.getTypeIndex("Dampymon"));
        fm1.rearrange("Zappymon", 0);
        // Middle to first
        assertEquals(0, fm1.getTypeIndex("Zappymon"));
        assertEquals(1, fm1.getTypeIndex("Thundermon"));
        assertEquals(2, fm1.getTypeIndex("Leafymon"));
        assertEquals(3, fm1.getTypeIndex("Dampymon"));
        fm1.rearrange("Dampymon", 0);
        // Last to First
        assertEquals(0, fm1.getTypeIndex("Dampymon"));
        assertEquals(1, fm1.getTypeIndex("Zappymon"));
        assertEquals(2, fm1.getTypeIndex("Thundermon"));
        assertEquals(3, fm1.getTypeIndex("Leafymon"));
    }
    
    @Test
    public void testRearrange_t3() {
        fm1.collect(new Thundermon(1));
        fm1.collect(new Zappymon(1));
        fm1.collect(new Leafymon(1));
        fm1.rearrange("Zappymon", 2);
        // Middle to last
        assertEquals(0, fm1.getTypeIndex("Thundermon"));
        assertEquals(1, fm1.getTypeIndex("Leafymon"));
        assertEquals(2, fm1.getTypeIndex("Zappymon"));
        fm1.rearrange("Leafymon", 2);
        // Middle to last
        assertEquals(0, fm1.getTypeIndex("Thundermon"));
        assertEquals(2, fm1.getTypeIndex("Leafymon"));
        assertEquals(1, fm1.getTypeIndex("Zappymon"));
        fm1.rearrange("Thundermon", 2);
        // First to Last
        assertEquals(0, fm1.getTypeIndex("Zappymon"));
        assertEquals(2, fm1.getTypeIndex("Thundermon"));
        assertEquals(1, fm1.getTypeIndex("Leafymon"));
    }
    
    @Test
    public void testRearrange_t4() {
        fm1.collect(new Thundermon(1));
        fm1.collect(new Zappymon(1));
        fm1.collect(new Leafymon(1));
        fm1.rearrange("Leafymon", 1);
        // Last to middle
        assertEquals(1, fm1.getTypeIndex("Leafymon"));
        assertEquals(0, fm1.getTypeIndex("Thundermon"));
        assertEquals(2, fm1.getTypeIndex("Zappymon"));
        fm1.rearrange("Zappymon", 0);
        // Last to first
        assertEquals(1, fm1.getTypeIndex("Thundermon"));
        assertEquals(2, fm1.getTypeIndex("Leafymon"));
        assertEquals(0, fm1.getTypeIndex("Zappymon"));
        fm1.rearrange("Zappymon", 1);
        // First to middle
        assertEquals(1, fm1.getTypeIndex("Zappymon"));
        assertEquals(0, fm1.getTypeIndex("Thundermon"));
        assertEquals(2, fm1.getTypeIndex("Leafymon"));
    }
    
    @Test
    public void testRearrange_t5() {
        fm1.collect(new Thundermon(1));
        fm1.collect(new Zappymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Dampymon(1));
        fm1.rearrange("Leafymon", 1);
        // Middle to before middle
        assertEquals(1, fm1.getTypeIndex("Leafymon"));
        assertEquals(0, fm1.getTypeIndex("Thundermon"));
        assertEquals(2, fm1.getTypeIndex("Zappymon"));
        assertEquals(3, fm1.getTypeIndex("Dampymon"));
        fm1.rearrange("Zappymon", 0);
        // Middle to first
        assertEquals(1, fm1.getTypeIndex("Thundermon"));
        assertEquals(2, fm1.getTypeIndex("Leafymon"));
        assertEquals(0, fm1.getTypeIndex("Zappymon"));
        assertEquals(3, fm1.getTypeIndex("Dampymon"));
        fm1.rearrange("Zappymon", 1);
        // First to middle
        assertEquals(1, fm1.getTypeIndex("Zappymon"));
        assertEquals(0, fm1.getTypeIndex("Thundermon"));
        assertEquals(2, fm1.getTypeIndex("Leafymon"));
        assertEquals(3, fm1.getTypeIndex("Dampymon"));
    }
    
    @Test
    public void testRearrange_t6() {
        fm1.collect(new Thundermon(1));
        fm1.collect(new Zappymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Dampymon(1));
        fm1.collect(new Doggymon(5));
        fm1.collect(new Earthymon(2));
        fm1.rearrange("Leafymon", 1);
        // Middle to before middle
        assertEquals(0, fm1.getTypeIndex("Thundermon"));
        assertEquals(1, fm1.getTypeIndex("Leafymon"));
        assertEquals(2, fm1.getTypeIndex("Zappymon"));
        assertEquals(3, fm1.getTypeIndex("Dampymon"));
        assertEquals(4, fm1.getTypeIndex("Doggymon"));
        assertEquals(5, fm1.getTypeIndex("Earthymon"));
        fm1.rearrange("Earthymon", 0);
        // Last to First
        assertEquals(1, fm1.getTypeIndex("Thundermon"));
        assertEquals(2, fm1.getTypeIndex("Leafymon"));
        assertEquals(3, fm1.getTypeIndex("Zappymon"));
        assertEquals(4, fm1.getTypeIndex("Dampymon"));
        assertEquals(5, fm1.getTypeIndex("Doggymon"));
        assertEquals(0, fm1.getTypeIndex("Earthymon"));
        fm1.rearrange("Doggymon", 3);
        // Middle to before middle
        assertEquals(1, fm1.getTypeIndex("Thundermon"));
        assertEquals(2, fm1.getTypeIndex("Leafymon"));
        assertEquals(4, fm1.getTypeIndex("Zappymon"));
        assertEquals(5, fm1.getTypeIndex("Dampymon"));
        assertEquals(3, fm1.getTypeIndex("Doggymon"));
        assertEquals(0, fm1.getTypeIndex("Earthymon"));
        fm1.rearrange("Leafymon", 4);
        // Middle to after middle
        assertEquals(1, fm1.getTypeIndex("Thundermon"));
        assertEquals(4, fm1.getTypeIndex("Leafymon"));
        assertEquals(3, fm1.getTypeIndex("Zappymon"));
        assertEquals(5, fm1.getTypeIndex("Dampymon"));
        assertEquals(2, fm1.getTypeIndex("Doggymon"));
        assertEquals(0, fm1.getTypeIndex("Earthymon"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testRearrangeExceptions() {
        fm1.collect(new Thundermon(1));
        fm1.collect(new Zappymon(1));
        fm1.rearrange("Zappymon", 7);
        fm1.rearrange("Zappymon", -2);
    }
    
    @Test
    public void testClone_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        Forneymonagerie dolly = fm1.clone();
        assertEquals(3, dolly.size());
        fm1.get(0).takeDamage(5, DamageType.BASIC);
        assertEquals(Dampymon.START_HEALTH - 5, fm1.get(0).getHealth());
        assertEquals(Dampymon.START_HEALTH, dolly.get(0).getHealth()); 
        fm1.rearrange("Leafymon", 0);
        assertEquals(0, fm1.getTypeIndex("Leafymon"));
        assertEquals(2, dolly.getTypeIndex("Leafymon"));
    }
    
    @Test
    public void testClone_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Doggymon(2));
        fm1.collect(new Thundermon(5));
        Forneymonagerie dolly = fm1.clone();
        assertEquals(5, dolly.size());
        fm1.get(4).takeDamage(5, DamageType.BASIC);
        assertEquals(Thundermon.START_HEALTH - 5, fm1.get(4).getHealth());
        assertEquals(Thundermon.START_HEALTH, dolly.get(4).getHealth());
        fm1.rearrange("Thundermon", 0);
        assertEquals(0, fm1.getTypeIndex("Thundermon"));
        assertEquals(0, dolly.getTypeIndex("Dampymon"));
    }
   
    @Test
    public void testTrade_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect(new Leafymon(1));
        fm1.trade(fm2);       
        assertEquals(2, fm2.size());
        assertEquals(1, fm1.size());
        assertTrue(fm1.containsType("Leafymon"));
        assertTrue(!fm1.containsType("Dampymon"));
        assertTrue(fm2.containsType("Dampymon"));
        assertTrue(!fm2.containsType("Leafymon"));
    }
    
    @Test
    public void testTrade_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Earthymon(1));
        fm1.collect(new Doggymon(1));
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect(new Leafymon(1));
        fm2.collect(new Doggymon(1));
        fm1.trade(fm2);       
        assertEquals(4, fm2.size());
        assertEquals(2, fm1.size());
        assertTrue(fm1.containsType("Leafymon"));
        assertTrue(!fm1.containsType("Dampymon"));
        assertTrue(!fm1.containsType("Earthymon"));
        assertTrue(fm2.containsType("Dampymon"));
        assertTrue(fm2.containsType("Doggymon"));
        assertTrue(!fm2.containsType("Leafymon"));
    }
    
    @Test
    public void testEquals_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect(new Dampymon(1));
        fm2.collect(new Burnymon(1));
        assertEquals(fm1, fm2);
        fm2.rearrange("Burnymon", 0);
        assertNotEquals(fm1, fm2);
    }
    
    @Test
    public void testEquals_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Earthymon(1));
        fm1.collect(new Doggymon(1));
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect(new Dampymon(1));
        fm2.collect(new Burnymon(1));
        assertNotEquals(fm1, fm2);
        fm2.collect(new Earthymon(2));
        fm2.collect(new Doggymon(2));
        assertNotEquals(fm1, fm2);
        fm2.remove(2);
        fm2.remove(2);
        fm2.collect(new Earthymon(1));
        fm2.collect(new Doggymon(1));
        assertEquals(fm1, fm2);
    }
    
    @Test
    public void testEquals_t2() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Earthymon(1));
        fm1.collect(new Doggymon(1));
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect(new Dampymon(1));
        fm2.collect(new Burnymon(1));
        fm2.collect(new Doggymon(1));
        fm2.collect(new Earthymon(1));
        assertNotEquals(fm1, fm2);
    }
    
    @Test
    public void testArena_t0() {
        fm1.collect(new Dampymon(1));
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect(new Dampymon(1));
        ForneymonArena.fight(fm1, fm2);
        assertEquals(0, fm1.size());
        assertEquals(0, fm2.size());
    }
    
    @Test
    public void testArena_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect(new Burnymon(1));
        fm2.collect(new Dampymon(1));
        ForneymonArena.fight(fm1, fm2);
        assertEquals(0, fm1.size());
        assertEquals(0, fm2.size());
    }
    
    @Test
    public void testArena_t2() {
        fm1.collect(new Dampymon(3));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect(new Burnymon(3));
        fm2.collect(new Dampymon(1));
        fm2.collect(new Zappymon(1));
        
        ForneymonArena.fight(fm1, fm2);
        assertEquals(0, fm1.size());
        assertEquals(1, fm2.size());
    }
    
    @Test
    public void testArena_t3() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Zappymon(1));
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect(new Burnymon(5));
        fm2.collect(new Dampymon(5));
        ForneymonArena.fight(fm1, fm2);
        assertEquals(0, fm1.size());
        assertEquals(1, fm2.size());
    }
    
    @Test
    public void testArena_t4() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Zappymon(1));
        Forneymonagerie fm2 = new Forneymonagerie();
        fm2.collect(new Burnymon(5));
        fm2.collect(new Dampymon(5));
        ForneymonArena.fight(fm1, fm2);
        assertEquals(0, fm1.size());
        assertEquals(1, fm2.size());
    }
}

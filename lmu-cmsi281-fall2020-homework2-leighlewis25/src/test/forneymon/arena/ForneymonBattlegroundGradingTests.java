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

public class ForneymonBattlegroundGradingTests {
    
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
    
    // Used as the basic empty LinkedForneymonagerie to test; the @Before
    // method is run before every @Test
    LinkedForneymonagerie fm1;
    @Before
    public void init () {
        possible++;
        fm1 = new LinkedForneymonagerie();
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
    }
    @Test
    public void testSize_t1() {
        assertEquals(0, fm1.size());
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        assertEquals(2, fm1.size());
    }
    @Test
    public void testSize_t2() {
        assertEquals(0, fm1.size());
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(2));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        assertEquals(5, fm1.size());
    } 
    
    @Test
    public void testSize_t3() {
        assertEquals(0, fm1.size());
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(2));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        assertEquals(9, fm1.size());
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
    }
    @Test
    public void testCollect_t1() {
        Dampymon d1 = new Dampymon(1);
        Dampymon d2 = new Dampymon(2);
        fm1.collect(d1);
        fm1.collect(d1);
        assertTrue(fm1.containsType("Dampymon"));
        assertEquals(1, fm1.get(0).getLevel());
        fm1.collect(d2);
        assertEquals(3, fm1.get(0).getLevel());
        assertEquals(1, fm1.size());
    }
    @Test
    public void testCollect_t2() {
        Dampymon d1 = new Dampymon(1);
        Dampymon d2 = new Dampymon(2);
        fm1.collect(d1);
        assertTrue(fm1.containsType("Dampymon"));
        fm1.collect(d2);
        assertEquals(3, fm1.get(0).getLevel());
        fm1.collect(d1);
        assertEquals(3, fm1.get(0).getLevel());
        assertEquals(1, fm1.size());
    }
    @Test
    public void testCollect_t3() {
        boolean c1 = fm1.collect(new Dampymon(1)),
                c2 = fm1.collect(new Burnymon(2)),
                c3 = fm1.collect(new Zappymon(3)),
                c4 = fm1.collect(new Burnymon(2));
        assertEquals(0, fm1.getTypeIndex("Dampymon"));
        assertEquals(1, fm1.getTypeIndex("Burnymon"));
        assertEquals(2, fm1.getTypeIndex("Zappymon"));
        assertEquals(4, fm1.get(1).getLevel());
        assertTrue(c1);
        assertTrue(c2);
        assertTrue(c3);
        assertFalse(c4);
    }
    @Test
    public void testCollect_t4() {
        Leafymon leafy = new Leafymon(3);
        assertEquals(0, fm1.size());
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(leafy);
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        fm1.collect(new Dampymon(2));
        fm1.collect(new Burnymon(2));
        fm1.collect(leafy);
        assertEquals(3, fm1.get(0).getLevel());
        assertEquals(4, fm1.get(1).getLevel());
        assertEquals(3, fm1.get(2).getLevel());
    }

    
    @Test
    public void testReleaseType_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        assertEquals(2, fm1.size());
        fm1.releaseType("Dampymon");
        assertEquals(1, fm1.size());
        assertTrue(fm1.containsType("Burnymon"));
        assertTrue(!fm1.containsType("Dampymon"));
    }
    @Test
    public void testReleaseType_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        boolean rt1 = fm1.releaseType("Burnymon");
        assertEquals(2, fm1.size());
        assertEquals("Dampymon", fm1.get(0).getFMType());
        assertEquals("Leafymon", fm1.get(1).getFMType());
        assertTrue(!fm1.containsType("Burnymon"));
        boolean rt2 = fm1.releaseType("Burnymon");
        assertTrue(rt1);
        assertFalse(rt2);
    }
    @Test
    public void testReleaseType_t2() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.releaseType("Dampymon");
        assertEquals(2, fm1.size());
        assertEquals("Burnymon", fm1.get(0).getFMType());
        assertEquals("Leafymon", fm1.get(1).getFMType());
        assertEquals(-1, fm1.getTypeIndex("Dampymon"));
        assertTrue(!fm1.containsType("Dampymon"));
        
        try {
            fm1.get(2);
            fail();
        } catch (Exception e) {
            if (! (e instanceof IllegalArgumentException)) {
                fail();
            }
        }
    }
    @Test
    public void testRemoveType_t3() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        fm1.releaseType("Dampymon");
        fm1.releaseType("Zappymon");
        assertEquals(7, fm1.size());
        assertEquals("Burnymon", fm1.get(0).getFMType());
        assertEquals("Leafymon", fm1.get(1).getFMType());
        assertEquals("TestForneymon0", fm1.get(2).getFMType());
        assertEquals("TestForneymon1", fm1.get(3).getFMType());
    }
    @Test
    public void testReleaseType_t4() {
        fm1.releaseType("Dampymon");
        assertTrue(!fm1.containsType("Dampymon"));
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
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        try {
            fm1.get(-1);
            fail();
        } catch (Exception e) {
            if (!(e instanceof IllegalArgumentException)) {
                fail();
            }
        }
    }
    @Test
    public void testGet_t2() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        try {
            fm1.get(2);
            fail();
        } catch (Exception e) {
            if (!(e instanceof IllegalArgumentException)) {
                fail();
            }
        }
    }
    @Test
    public void testGet_t3() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        assertEquals("TestForneymon4", fm1.get(8).getFMType());
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
    @Test
    public void testRemove_t1() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        assertEquals(2, fm1.size());
        assertEquals(d1, fm1.remove(0));
        assertEquals(1, fm1.size());
        assertEquals(b1, fm1.remove(0));
        assertEquals(0, fm1.size());
        
        try {
            fm1.remove(0);
            fail();
        } catch (Exception e) {
            if (!(e instanceof IllegalArgumentException)) {
                fail();
            }
        }
    }
    @Test
    public void testRemove_t2() {
        Burnymon b1 = new Burnymon(2);
        TestForneymon0 t1 = new TestForneymon0(1);
        TestForneymon4 t5 = new TestForneymon4(1);
        fm1.collect(new Dampymon(1));
        fm1.collect(b1);
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(t1);
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(t5);
        assertEquals(b1, fm1.remove(1));
        assertEquals(t1, fm1.remove(3));
        assertEquals(t5, fm1.remove(6));
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
    }
    @Test
    public void testGetTypeIndexContainsType_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Dampymon(1));
        assertEquals(0, fm1.getTypeIndex("Dampymon"));
        assertEquals(1, fm1.getTypeIndex("Burnymon"));
        assertEquals(-1, fm1.getTypeIndex("Leafymon"));
        assertTrue(fm1.containsType("Dampymon"));
        assertFalse(fm1.containsType("Zappymon"));
    }
    @Test
    public void testGetTypeIndexContainsType_t2() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        assertEquals(0, fm1.getTypeIndex("Dampymon"));
        assertEquals(1, fm1.getTypeIndex("Burnymon"));
        assertEquals(2, fm1.getTypeIndex("Leafymon"));
        assertEquals(3, fm1.getTypeIndex("Zappymon"));
        assertEquals(4, fm1.getTypeIndex("TestForneymon0"));
        assertEquals(8, fm1.getTypeIndex("TestForneymon4"));
        assertEquals(-1, fm1.getTypeIndex("TestForneymon5"));
        assertTrue(fm1.containsType("Dampymon"));
        assertTrue(fm1.containsType("TestForneymon2"));
        assertFalse(fm1.containsType("TestForneymon5"));
    }
    
    
    @Test
    public void testRearrange_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.rearrange("Leafymon", 0);
        assertEquals(1, fm1.getTypeIndex("Dampymon"));
        assertEquals(2, fm1.getTypeIndex("Burnymon"));
        assertEquals(0, fm1.getTypeIndex("Leafymon"));
    }
    @Test
    public void testRearrange_t1() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        Leafymon e1 = new Leafymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        fm1.collect(e1);
        fm1.rearrange("Leafymon", 0);
        fm1.collect(new Burnymon(1));
        fm1.collect(new Dampymon(1));
        fm1.collect(d1);
        fm1.collect(b1);
        fm1.collect(e1);
        assertEquals(1, fm1.getTypeIndex("Dampymon"));
        assertEquals(2, fm1.getTypeIndex("Burnymon"));
        assertEquals(0, fm1.getTypeIndex("Leafymon"));
        assertEquals(e1, fm1.get(0));
        assertEquals(d1, fm1.get(1));
        assertEquals(b1, fm1.get(2));
    }
    @Test
    public void testRearrange_t2() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Zappymon(1));
        fm1.rearrange("Dampymon", 0);
        fm1.rearrange("Zappymon", 3);
        assertEquals(0, fm1.getTypeIndex("Dampymon"));
        assertEquals(1, fm1.getTypeIndex("Burnymon"));
        assertEquals(2, fm1.getTypeIndex("Leafymon"));
        assertEquals(3, fm1.getTypeIndex("Zappymon"));
    }
    @Test
    public void testRearrange_t3() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        Leafymon e1 = new Leafymon(1);
        Zappymon z1 = new Zappymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        fm1.collect(e1);
        fm1.collect(z1);
        fm1.rearrange("Burnymon", 1);
        fm1.rearrange("Leafymon", 1);
        assertEquals(d1, fm1.get(0));
        assertEquals(e1, fm1.get(1));
        assertEquals(b1, fm1.get(2));
        assertEquals(z1, fm1.get(3));
    }
    @Test
    public void testRearrange_t4() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        Leafymon e1 = new Leafymon(1);
        Zappymon z1 = new Zappymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        fm1.collect(e1);
        fm1.collect(z1);
        fm1.rearrange("Burnymon", 3);
        assertEquals(d1, fm1.get(0));
        assertEquals(e1, fm1.get(1));
        assertEquals(z1, fm1.get(2));
        assertEquals(b1, fm1.get(3));
    }
    @Test
    public void testRearrange_t5() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        fm1.rearrange("Dampymon", 8);
        fm1.rearrange("TestForneymon4", 0);
        assertEquals(8, fm1.getTypeIndex("Dampymon"));
        assertEquals(1, fm1.getTypeIndex("Burnymon"));
        assertEquals(2, fm1.getTypeIndex("Leafymon"));
        assertEquals(3, fm1.getTypeIndex("Zappymon"));
        assertEquals(4, fm1.getTypeIndex("TestForneymon0"));
        assertEquals(0, fm1.getTypeIndex("TestForneymon4"));
        assertEquals(-1, fm1.getTypeIndex("TestForneymon5"));
    }
    @Test
    public void testRearrange_t6() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        try {
            fm1.rearrange("Leafymon", 4);
            fail();
        } catch (Exception e) {
            if (!(e instanceof IllegalArgumentException)) {
                fail();
            }
        }
    }
    @Test
    public void testRearrange_t7() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        Leafymon e1 = new Leafymon(1);
        Zappymon z1 = new Zappymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        fm1.collect(e1);
        fm1.collect(z1);
        fm1.remove(2);
        fm1.rearrange("Dampymon", 2);
        assertEquals(b1, fm1.get(0));
        assertEquals(z1, fm1.get(1));
        assertEquals(d1, fm1.get(2));
        assertEquals(-1, fm1.getTypeIndex("Leafymon"));
    }
    
    
    @Test
    public void testClone_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie dolly = fm1.clone();
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
        LinkedForneymonagerie dolly = fm1.clone();
        assertEquals(0, dolly.size());
        dolly.collect(new Dampymon(1));
        assertEquals(0, fm1.size());
        assertEquals(0, dolly.getTypeIndex("Dampymon"));
        assertEquals(-1, fm1.getTypeIndex("Dampymon"));
    }
    @Test
    public void testClone_t2() {
        Dampymon d1 = new Dampymon(1);
        fm1.collect(d1);
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        LinkedForneymonagerie dolly = fm1.clone();
        dolly.collect(d1);
        fm1.collect(d1);
        assertEquals(9, dolly.size());
        assertEquals(1, fm1.get(0).getLevel());
        assertEquals(2, dolly.get(0).getLevel());
    }
    
    
    @Test
    public void testTrade_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
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
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm1.trade(fm2);
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        assertTrue(fm1.containsType("Dampymon"));
        assertFalse(fm2.containsType("Dampymon"));
        fm2.trade(fm1);
        assertTrue(fm2.containsType("Dampymon"));
        assertFalse(fm1.containsType("Dampymon"));
    }
    @Test
    public void testTrade_t2() {
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        LinkedForneymonagerie fm3 = new LinkedForneymonagerie();
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        fm1.trade(fm2);
        fm2.trade(fm3);
        assertEquals(0, fm1.size());
        assertEquals(0, fm2.size());
        assertEquals(9, fm3.size());
    }
    
    
    @Test
    public void testEquals_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Dampymon(1));
        fm2.collect(new Burnymon(1));
        
        assertEquals(fm1, fm2);
        fm2.rearrange("Burnymon", 0);
        assertNotEquals(fm1, fm2);
    }
    @Test
    public void testEquals_t1() {
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        assertEquals(fm1, fm2);
    }
    @Test
    public void testEquals_t2() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        LinkedForneymonagerie fm2 = fm1.clone();
        assertEquals(fm1, fm2);
        assertEquals(fm1, fm1);
    }
    @Test
    public void testEquals_t3() {
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        fm2.collect(new Dampymon(1));
        fm2.collect(new Burnymon(1));
        fm2.collect(new Leafymon(3));
        fm2.collect(new Zappymon(2));
        fm2.collect(new TestForneymon0(1));
        fm2.collect(new TestForneymon1(1));
        fm2.collect(new TestForneymon2(1));
        fm2.collect(new TestForneymon3(1));
        fm2.collect(new TestForneymon4(1));
        assertNotEquals(fm1, fm2);
        fm2.collect(new Burnymon(1));
        assertEquals(fm1, fm2);
    }
    @Test
    public void testEquals_t4() {
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(3));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        fm2.collect(new Burnymon(3));
        fm2.collect(new Dampymon(1));
        fm2.collect(new Leafymon(3));
        fm2.collect(new Zappymon(2));
        fm2.collect(new TestForneymon0(1));
        fm2.collect(new TestForneymon1(1));
        fm2.collect(new TestForneymon2(1));
        fm2.collect(new TestForneymon3(1));
        fm2.collect(new TestForneymon4(1));
        fm1.remove(0);
        fm2.remove(1);
        assertEquals(fm1, fm2);
    }
    
    @Test
    public void testArena_t0() {
        fm1.collect(new Dampymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Dampymon(1));
        
        LinkedForneymonArena.fight(fm1, fm2);
        assertEquals(0, fm1.size());
        assertEquals(0, fm2.size());
    }
    @Test
    public void testArena_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Burnymon(1));
        fm2.collect(new Dampymon(1));
        
        LinkedForneymonArena.fight(fm1, fm2);
        assertEquals(0, fm1.size());
        assertEquals(0, fm2.size());
    }
    @Test
    public void testArena_t2() {
        fm1.collect(new Dampymon(3));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Burnymon(3));
        fm2.collect(new Dampymon(1));
        fm2.collect(new Zappymon(1));
        
        LinkedForneymonArena.fight(fm1, fm2);
        assertEquals(0, fm1.size());
        assertEquals(1, fm2.size());
    }
    @Test
    public void testArena_t3() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Zappymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Burnymon(5));
        fm2.collect(new Dampymon(5));
        
        LinkedForneymonArena.fight(fm1, fm2);
        assertEquals(0, fm1.size());
        assertEquals(1, fm2.size());
    }
    @Test
    public void testArena_t4() {
        fm1.collect(new Burnymon(5));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Dampymon(1));
        
        LinkedForneymonArena.fight(fm1, fm2);
        assertEquals(1, fm1.size());
        assertEquals(0, fm2.size());
        assertEquals(3, fm1.get(0).getHealth());
        assertFalse(fm2.containsType("Dampymon"));
    }
    @Test
    public void testArena_t5() {
        fm1.collect(new Dampymon(3));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Zappymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Burnymon(3));
        fm2.collect(new Dampymon(3));
        fm2.collect(new Zappymon(3));
        fm2.collect(new Leafymon(3));
        
        LinkedForneymonArena.fight(fm1, fm2);
        assertEquals(0, fm1.size());
        assertEquals(2, fm2.size());
    }
    @Test
    public void testArena_t6() {
        fm1.collect(new Dampymon(3));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Zappymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Burnymon(3));
        fm2.collect(new Dampymon(3));
        fm2.collect(new Zappymon(3));
        fm2.collect(new Leafymon(3));
        
        LinkedForneymonArena.fight(fm1, fm2);
        assertEquals(8, fm2.get(0).getHealth());
        assertEquals("Zappymon", fm2.get(0).getFMType());
        assertEquals(2, fm2.get(1).getHealth());
        assertEquals("Leafymon", fm2.get(1).getFMType());
    }
    
}

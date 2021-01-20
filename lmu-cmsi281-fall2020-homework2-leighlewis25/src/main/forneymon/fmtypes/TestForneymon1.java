package main.forneymon.fmtypes;

/**
 * Test Forneymon that's used for certain Forneymonagerie methods
 */
public class TestForneymon1 extends Forneymon {
    
    public static final int START_HEALTH = 15;
    public static final DamageType DT = DamageType.BASIC;
    
    public TestForneymon1 (int level) {
        super(START_HEALTH, DT, level);
    }
        
    public TestForneymon1 (TestForneymon1 toCopy) {
        super(toCopy.getHealth(), DT, toCopy.getLevel());
    }
    
    @Override
    public TestForneymon1 clone() {
        return new TestForneymon1(this);
    }
    
}

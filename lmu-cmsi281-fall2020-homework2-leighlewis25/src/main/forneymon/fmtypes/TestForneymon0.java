package main.forneymon.fmtypes;

/**
 * Test Forneymon that's used for certain Forneymonagerie methods
 */
public class TestForneymon0 extends Forneymon {
    
    public static final int START_HEALTH = 15;
    public static final DamageType DT = DamageType.BASIC;
    
    public TestForneymon0 (int level) {
        super(START_HEALTH, DT, level);
    }
     
    public TestForneymon0 (TestForneymon0 toCopy) {
        super(toCopy.getHealth(), DT, toCopy.getLevel());
    }
    
    @Override
    public TestForneymon0 clone() {
        return new TestForneymon0(this);
    }
    
}

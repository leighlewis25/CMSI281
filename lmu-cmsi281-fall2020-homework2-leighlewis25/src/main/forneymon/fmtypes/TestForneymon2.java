package main.forneymon.fmtypes;

/**
 * Test Forneymon that's used for certain Forneymonagerie methods
 */
public class TestForneymon2 extends Forneymon {
    
    public static final int START_HEALTH = 15;
    public static final DamageType DT = DamageType.BASIC;
    
    public TestForneymon2 (int level) {
        super(START_HEALTH, DT, level);
    }
     
    public TestForneymon2 (TestForneymon2 toCopy) {
        super(toCopy.getHealth(), DT, toCopy.getLevel());
    }
    
    @Override
    public TestForneymon2 clone() {
        return new TestForneymon2(this);
    }
    
}

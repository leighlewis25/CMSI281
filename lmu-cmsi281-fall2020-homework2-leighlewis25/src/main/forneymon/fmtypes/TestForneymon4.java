package main.forneymon.fmtypes;

/**
 * Test Forneymon that's used for certain Forneymonagerie methods
 */
public class TestForneymon4 extends Forneymon {
    
    public static final int START_HEALTH = 15;
    public static final DamageType DT = DamageType.BASIC;
    
    public TestForneymon4 (int level) {
        super(START_HEALTH, DT, level);
    }
     
    public TestForneymon4 (TestForneymon4 toCopy) {
        super(toCopy.getHealth(), DT, toCopy.getLevel());
    }
    
    @Override
    public TestForneymon4 clone() {
        return new TestForneymon4(this);
    }
    
}

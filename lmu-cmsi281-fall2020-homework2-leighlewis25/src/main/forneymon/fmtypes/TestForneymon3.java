package main.forneymon.fmtypes;

/**
 * Test Forneymon that's used for certain Forneymonagerie methods
 */
public class TestForneymon3 extends Forneymon {
    
    public static final int START_HEALTH = 15;
    public static final DamageType DT = DamageType.BASIC;
    
    public TestForneymon3 (int level) {
        super(START_HEALTH, DT, level);
    }
     
    public TestForneymon3 (TestForneymon3 toCopy) {
        super(toCopy.getHealth(), DT, toCopy.getLevel());
    }
    
    @Override
    public TestForneymon3 clone() {
        return new TestForneymon3(this);
    }
    
}

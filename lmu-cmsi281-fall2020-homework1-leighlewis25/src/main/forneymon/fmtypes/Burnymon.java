package main.forneymon.fmtypes;

/**
 * Fire-based Forneymon that has little health but can deal bonus
 * damage to other Forneymon of certain types
 */
public class Burnymon extends Forneymon {
    
    public static final int START_HEALTH = 15;
    public static final DamageType DT = DamageType.BURNY;
    
    public Burnymon (int level) {
        super(START_HEALTH, DT, level);
    }
    
    public Burnymon (Burnymon toCopy) {
        super(toCopy.getHealth(), DT, toCopy.getLevel());
    }
    
    @Override
    public Burnymon clone() {
        return new Burnymon(this);
    }
    
}

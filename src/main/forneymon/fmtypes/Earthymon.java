package main.forneymon.fmtypes;

/**
 * Earth-like Forneymon that takes bonus LEAFY damage.
 */
public class Earthymon extends Forneymon {
    
    public static final int START_HEALTH = 15;
    public static final int DMG_MODIFIER = 5;
    public static final DamageType DT = DamageType.EARTHY;
    
    /**
     * Constructs a new Earthymon at the given level
     * @param level The level of this Earthymon
     */
    public Earthymon (int level) {
        super(START_HEALTH, DT, level);
    }
    
    /**
     * Copy constructor for Earthymon that creates a deep-copy
     * of the given other toCopy
     * @param toCopy The other to copy from
     */
    public Earthymon (Earthymon toCopy) {
        super(toCopy.getHealth(), DT, toCopy.getLevel());
    }
    
    /**
     * @see Forneymon
     * Earthymon take bonus Leafymon damage
     */
    @Override
    public int takeDamage (int dmg, DamageType type) {
        if (type == DamageType.LEAFY) {
            dmg += DMG_MODIFIER;
        }
        return super.takeDamage(dmg, type);
    }
    
    @Override
    public Earthymon clone() {
        return new Earthymon(this);
    }
    
}
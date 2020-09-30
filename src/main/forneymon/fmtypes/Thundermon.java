package main.forneymon.fmtypes;

/**
 * Loud Thunder Forneymon that takes bonus ZAPPY damage.
 */
public class Thundermon extends Forneymon {
    
    public static final int START_HEALTH = 15;
    public static final int DMG_MODIFIER = 5;
    public static final DamageType DT = DamageType.THUNDER;
    
    /**
     * Constructs a new Thundermon at the given level
     * @param level The level of this Thundermon
     */
    public Thundermon (int level) {
        super(START_HEALTH, DT, level);
    }
    
    /**
     * Copy constructor for Thundermon that creates a deep-copy
     * of the given other toCopy
     * @param toCopy The other to copy from
     */
    public Thundermon (Thundermon toCopy) {
        super(toCopy.getHealth(), DT, toCopy.getLevel());
    }
    
    /**
     * @see Forneymon
     * Thundermon take bonus ZAPPY damage
     */
    @Override
    public int takeDamage (int dmg, DamageType type) {
        if (type == DamageType.ZAPPY) {
            dmg += DMG_MODIFIER;
        }
        return super.takeDamage(dmg, type);
    }
    
    @Override
    public Thundermon clone() {
        return new Thundermon(this);
    }
    
}

package main.forneymon.fmtypes;

/**
 * Water-pale Forneymon that takes bonus BURNY damage.
 */
public class Dampymon extends Forneymon {
    
    public static final int START_HEALTH = 25;
    public static final int DMG_MODIFIER = 5;
    public static final DamageType DT = DamageType.DAMPY;
    
    /**
     * Constructs a new Dampymon at the given level
     * @param level The level of this Dampymon
     */
    public Dampymon (int level) {
        super(START_HEALTH, DT, level);
    }
    
    /**
     * Copy constructor for Dampymon that creates a deep-copy
     * of the given other toCopy
     * @param toCopy The other to copy from
     */
    public Dampymon (Dampymon toCopy) {
        super(toCopy.getHealth(), DT, toCopy.getLevel());
    }
    
    /**
     * @see Forneymon
     * Dampymon take bonus BURNY damage
     */
    @Override
    public int takeDamage (int dmg, DamageType type) {
        if (type == DamageType.BURNY) {
            dmg += DMG_MODIFIER;
        }
        return super.takeDamage(dmg, type);
    }
    
    @Override
    public Dampymon clone() {
        return new Dampymon(this);
    }
    
}

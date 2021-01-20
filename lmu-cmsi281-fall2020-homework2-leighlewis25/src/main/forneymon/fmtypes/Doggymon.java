package main.forneymon.fmtypes;


/**
 * Dog-like Forneymon that takes bonus damage from BURNY damage
 */
public class Doggymon extends Forneymon {
    
    public static final int START_HEALTH = 20;
    public static final int DMG_MODIFIER = 3;
    public static final DamageType DT = DamageType.DOGGY;
    
    /**
     * Constructs a new Doggymon at the given level
     * @param level The level of this Doggymon
     */
    public Doggymon (int level) {
        super(START_HEALTH, DT, level);
    }
    
    /**
     * Copy constructor for this Forneymon that creates a deep-copy
     * of the given other toCopy
     * @param toCopy The other to copy from
     */
    public Doggymon (Doggymon toCopy) {
        super(toCopy.getHealth(), DT, toCopy.getLevel());
    }
    
    /**
     * @see Forneymon
     * Doggymon take bonus BURNY damage
     */
    @Override
    public int takeDamage (int dmg, DamageType type) {
        if (type == DamageType.BURNY) {
            dmg += DMG_MODIFIER;
        }
        return super.takeDamage(dmg, type);
    }
    
    @Override
    public Doggymon clone() {
        return new Doggymon(this);
    }
    
}

package main.forneymon.fmtypes;

/**
 * Electrical Forneymon that takes bonus damage from DAMPY but
 * reduced damage from LEAFY sources
 */
public class Zappymon extends Forneymon {
    
    public static final int START_HEALTH = 20;
    public static final int DMG_MODIFIER = 3;
    public static final DamageType DT = DamageType.ZAPPY;
    
    /**
     * Constructs a new Zappymon at the given level
     * @param level The level of this Forneymon
     */
    public Zappymon (int level) {
        super(START_HEALTH, DT, level);
    }
    
    /**
     * Copy constructor for this Forneymon that creates a deep-copy
     * of the given other toCopy
     * @param toCopy The other to copy from
     */
    public Zappymon (Zappymon toCopy) {
        super(toCopy.getHealth(), DT, toCopy.getLevel());
    }
    
    /**
     * @see Forneymon
     * Zappymon take bonus DAMPY damage, but
     * reduced LEAFY damage
     */
    @Override
    public int takeDamage (int dmg, DamageType type) {
        if (type == DamageType.LEAFY) {
            dmg -= DMG_MODIFIER;
        }
        if (type == DamageType.DAMPY) {
            dmg += DMG_MODIFIER;
        }
        return super.takeDamage(dmg, type);
    }
    
    @Override
    public Zappymon clone() {
        return new Zappymon(this);
    }
    
}

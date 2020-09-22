package main.forneymon.fmtypes;

/**
 * Plant-like Forneymon that takes reduced damage from DAMPY damage
 * but bonus damage from BURNY damage
 */
public class Leafymon extends Forneymon {
    
    public static final int START_HEALTH = 20;
    public static final int DMG_MODIFIER = 3;
    public static final DamageType DT = DamageType.LEAFY;
    
    /**
     * Constructs a new Leafymon at the given level
     * @param level The level of this Forneymon
     */
    public Leafymon (int level) {
        super(START_HEALTH, DT, level);
    }
    
    /**
     * Copy constructor for this Forneymon that creates a deep-copy
     * of the given other toCopy
     * @param toCopy The other to copy from
     */
    public Leafymon (Leafymon toCopy) {
        super(toCopy.getHealth(), DT, toCopy.getLevel());
    }
    
    /**
     * @see Forneymon
     * Leafymon take bonus BURNY damage, but
     * reduced DAMPY damage
     */
    @Override
    public int takeDamage (int dmg, DamageType type) {
        if (type == DamageType.DAMPY) {
            dmg -= DMG_MODIFIER;
        }
        if (type == DamageType.BURNY) {
            dmg += DMG_MODIFIER;
        }
        return super.takeDamage(dmg, type);
    }
    
    @Override
    public Leafymon clone() {
        return new Leafymon(this);
    }
    
}

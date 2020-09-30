package main.forneymon.fmtypes;

import java.util.Objects;

/**
 * Pseudo-mystical pets locked in combat for the amusement of our
 * viewers! Get your Forneymonagerie ready today!
 */
abstract public class Forneymon implements MinForneymon {
    
    private int health;
    private int level;
    private DamageType damageType;
    
    /**
     * Constructs a new Forneymon with the given starting health,
     * level, and dealt damage type
     * @param health Starting health
     * @param dt Damage type dealt when attacking
     * @param level Starting level
     */
    Forneymon (int health, DamageType dt, int level) {
        this.health = health;
        this.damageType = dt;
        this.level = level;
    }
    
    /**
     * Reduces the Forneymon's health by the amount specified,
     * with the potential for bonuses / penalties based on the
     * damage type
     * @param dmg Amount of damage taken
     * @param type Damage Type of the damage taken
     * @return This Forneymon's health remaining
     */
    public int takeDamage (int dmg, DamageType type) {
        this.health -= dmg;
        return this.health;
    }
    
    /**
     * Adds the given number of levels to this Forneymon
     * @param levelsToAdd Number of levels to add
     */
    public void addLevels (int levelsToAdd) {this.level += levelsToAdd;}
    /**
     * Subtracts the given number of levels from this Forneymon
     * @param levelsToSub Number of levels to subtract
     */
    public void subLevels (int levelsToSub) {this.level -= levelsToSub;}
    
    /**
     * Returns a String representation of this Forneymon's type,
     * e.g., "Burnymon" or "Dampymon"
     * @return This Forneymon's type
     */
    public String getFMType () {return this.getClass().getSimpleName();}
    
    /**
     * @return Returns this Forneymon's remaining health 
     */
    public int getHealth () {return this.health;}
    
    /**
     * @return Returns this Forneymon's current level
     */
    public int getLevel () {return this.level;}
    
    /**
     * @return Returns the damage type that this Forneymon deals in combat
     */
    public DamageType getDamageType () {return this.damageType;}
    
    /**
     * Creates a copy of this Forneymon
     * @return A copy of the calling Forneymon
     */
    public abstract Forneymon clone();
    

    /**
     * Returns a convenient String representation of the Forneymon
     * of the format:
     * FMType [Level] CurrentHealth HP
     * @return A String representing this Forneymon's type, level, and health
     */
    @Override
    public String toString () {
        return this.getFMType() + " [" + this.getLevel() + "]: " + this.getHealth() + "HP";
    }
    
    /**
     * Compares two Forneymon for equivalence based on their type, health, and level
     * @return Whether two Forneymon are the same type, health, and level
     */
    @Override
    public boolean equals (Object other) {
        if (this.getClass() != other.getClass()) { return false; }
        Forneymon otherFM = (Forneymon) other;
        return this.health == otherFM.health && this.level == otherFM.level;
    }
    
    @Override
    public int hashCode () {
        return Objects.hash(this.level, this.health);
    }
    
}

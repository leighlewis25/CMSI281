package main.forneymon.fmtypes;

public interface MinForneymon {
    
    void addLevels (int levelsToAdd);
    void subLevels (int levelsToSub);
    int takeDamage (int dmg, DamageType type);
    public String getFMType ();
    public int getHealth ();
    public int getLevel ();
    public DamageType getDamageType ();
    
}

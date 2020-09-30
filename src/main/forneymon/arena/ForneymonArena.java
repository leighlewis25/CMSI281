package main.forneymon.arena;

import main.forneymon.fmtypes.Forneymon;

/**
 * Contains methods for facing off Forneymonagerie against one another!
 */
public class ForneymonArena {
    
    public static final int BASE_DAMAGE = 5;
    
    /**
     * Conducts a fight between two Forneymonageries, consisting of the following
     * steps:
     * <ol>
     *   <li>Forneymon from each Forneymonagerie are paired to fight, in sequence
     *     starting from index 0.</li>
     *  <li>Forneymon that faint (have 0 or less health) are removed from their
     *    respective Forneymonagerie.</li>
     *  <li>Repeat until one or both Forneymonagerie have no remaining Forneymon.</li>     
     * </ol>
     * @param fm1 One of the fighting Forneymonagerie
     * @param fm2 One of the fighting Forneymonagerie
     */
    public static void fight (Forneymonagerie fm1, Forneymonagerie fm2) {        
        int indexFM1 = 0;
        int indexFM2 = 0;

       
        System.out.println("Combat Starting!");
        
        while (fm1.size() > 0 && fm2.size() > 0) {
            System.out.println("New Round: " + fm1.get(indexFM1) + " vs " + fm2.get(indexFM2));

            fm1.get(indexFM1).takeDamage(BASE_DAMAGE + fm2.get(indexFM2).getLevel(), fm2.get(indexFM2).getDamageType());
            fm2.get(indexFM2).takeDamage(BASE_DAMAGE + fm1.get(indexFM1).getLevel(), fm1.get(indexFM1).getDamageType());
            
            System.out.println("Combat Results: " + fm1.get(indexFM1) + " vs " + fm2.get(indexFM2));

            if (fm1.get(indexFM1).getHealth() <= 0) {
                fm1.remove(indexFM1);
                indexFM1--;
            }
            
            if (fm2.get(indexFM2).getHealth() <= 0) {
                fm2.remove(indexFM2);
                indexFM2--;
            }
            indexFM1++;
            indexFM2++;
            
            if (indexFM1 == fm1.size()) {
                indexFM1 = 0;
            }
            
            if (indexFM2 == fm2.size()) {
                indexFM2 = 0;
            }

            
        }
        
        if (fm1.size() == 0 && fm2.size() > 0) {
            System.out.println("Combat Finished! Victor: Forneymonagerie " + 2);

        }
        
        if (fm1.size() > 0 && fm2.size() == 0) {
            System.out.println("Combat Finished! Victor: Forneymonagerie " + 1);
        }
        
        if (fm1.size() == 0 && fm2.size() == 0) {
            System.out.println("Combat Finished! It was a tie."); 
        }
    }
    
}

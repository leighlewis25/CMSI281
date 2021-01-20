package main.forneymon.arena;

/**
 * Contains methods for facing off LinkedForneymonagerie against one another!
 */
public class LinkedForneymonArena {

    public static final int BASE_DAMAGE = 5;

    /**
     * Conducts a fight between two LinkedForneymonagerie, consisting of the following
     * steps, assisted by Iterators on each LinkedForneymonagerie:
     * <ol>
     *   <li>Forneymon from each LinkedForneymonagerie are paired to fight, in sequence
     *     starting from index 0.</li>
     *  <li>Forneymon that faint (have 0 or less health) are removed from their
     *    respective LinkedForneymonagerie.</li>
     *  <li>Repeat until one or both Forneymonagerie have no remaining Forneymon.</li>     
     * </ol>
     * @param fm1 One of the fighting LinkedForneymonagerie
     * @param fm2 One of the fighting LinkedForneymonagerie
     */
    public static void fight (LinkedForneymonagerie fm1, LinkedForneymonagerie fm2) {
        LinkedForneymonagerie.Iterator fm1Iterator = fm1.getIterator();
        LinkedForneymonagerie.Iterator fm2Iterator = fm2.getIterator();

        System.out.println("Combat Starting!");

        while (fm1.size() > 0 && fm2.size() > 0) {
            fightRound(fm1, fm1Iterator, fm2, fm2Iterator);
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

    // Private helper method
    // -----------------------------------------------------------

    /**
     * Conducts a round of fighting between two Forneymon.
     * @param fm1 The LinkedForneymonagerie that is fighting against fm2
     * @param fm1Iterator The iterator belonging to the LinkedForneymonagerie fm1
     * @param fm2 The LinkedForneymonagerie that is fighting against fm1
     * @param fm2Iterator The iterator belonging to the LinkedForneymonagerie fm2
     */
    private static void fightRound(LinkedForneymonagerie fm1, LinkedForneymonagerie.Iterator fm1Iterator, 
            LinkedForneymonagerie fm2, LinkedForneymonagerie.Iterator fm2Iterator) {

        System.out.println("New Round: " + fm1Iterator.getCurrent() + " vs " + fm2Iterator.getCurrent());

        fm1Iterator.getCurrent().takeDamage(BASE_DAMAGE + fm2Iterator.getCurrent().getLevel(), fm2Iterator.getCurrent().getDamageType());
        fm2Iterator.getCurrent().takeDamage(BASE_DAMAGE + fm1Iterator.getCurrent().getLevel(), fm1Iterator.getCurrent().getDamageType());

        System.out.println("Combat Results: " + fm1Iterator.getCurrent() + " vs " + fm2Iterator.getCurrent());

        if (fm1Iterator.getCurrent().getHealth() <= 0) {
            fm1Iterator.removeCurrent();
        }

        if (!fm1.empty()) {
            fm1Iterator.next();
        }

        if (fm2Iterator.getCurrent().getHealth() <= 0) {
            fm2Iterator.removeCurrent();
        }

        if (!fm2.empty()) {
            fm2Iterator.next();
        }
    }

}

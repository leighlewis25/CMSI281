package main.forneymon.arena;

import java.util.Objects;

import main.forneymon.fmtypes.*;

/**
 * Collections of Forneymon ready to fight in the arena!
 */
public class Forneymonagerie implements ForneymonagerieInterface {

    // Constants
    // ----------------------------------------------------------
    // [!] DO NOT change this START_SIZE for your collection, and
    // your collection *must* be initialized to this size
    private static final int START_SIZE = 4;

    // Fields
    // ----------------------------------------------------------
    private Forneymon[] collection;
    private int size;
    
    
    // Constructor
    // ----------------------------------------------------------
    public Forneymonagerie () {
        // TODO!
    }
    
    
    // Methods
    // ----------------------------------------------------------
    
    // TODO: Remember your documentation on these methods as well!
    
    public boolean empty () {
        // TODO!
        throw new UnsupportedOperationException();
    }
    
    public int size () {
        // TODO!
        throw new UnsupportedOperationException();
    }
    
    public boolean collect (Forneymon toAdd) {
        // TODO!
        throw new UnsupportedOperationException();
    }
    
    public boolean releaseType (String fmType) {
        // TODO!
        throw new UnsupportedOperationException();
    }
    
    public Forneymon get (int index) {
        // TODO!
        throw new UnsupportedOperationException();
    }
    
    public Forneymon remove (int index) {
        // TODO!
        throw new UnsupportedOperationException();
    }
    
    public int getTypeIndex (String fmType) {
        // TODO!
        throw new UnsupportedOperationException();
    }
    
    public boolean containsType (String toCheck) {
        // TODO!
        throw new UnsupportedOperationException();
    }
    
    public void trade (Forneymonagerie other) {
        // TODO!
        throw new UnsupportedOperationException();
    }
    
    public void rearrange (String fmType, int index) {
        // TODO!
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Forneymonagerie clone () {
        // TODO!
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean equals (Object other) {
        // TODO!
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int hashCode () {
        // This one is a freebie, no changes necessary here
        return Objects.hash(this.collection, this.size);
    }
    
    @Override
    public String toString () {
        // This one's also freebie -- you don't have to add or
        // change anything here!
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = collection[i].toString();
        }
        return "[ " + String.join(", ", result) + " ]";
    }
    
    
    // Private helper methods
    // ----------------------------------------------------------
    
    // TODO: Add your helper methods here!
    
}

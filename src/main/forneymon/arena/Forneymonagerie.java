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
        this.size = 0;
        this.collection = new Forneymon[START_SIZE];
    }
    
    
    // Methods
    // ----------------------------------------------------------
    
    // TODO: Remember your documentation on these methods as well!
    
    /**
     * Returns true if the Forneymonagerie has no Forneymon 
     * inside; otherwise, returns false.
     */
    public boolean empty () {
        if (this.size == 0) {
        	return true;
        }
        else {
        	return false;
        }
    }
    
    /**
     * Returns the current size of the Forneymonagerie
     */
    public int size () {
        return this.size;
    }
    
    /**
     * Attempts to add a reference to the given Forneymon to the 
     * Forneymonagerie's collection:
     * - Adds a Forneymon to a Forneymonagerie of a type that
     * doesn't currently exist within adds it to the last open index.
     * - If trying to add a Forneymon that is already stored within 
     * the Forneymonagerie, will return false.
     * - If trying to add the Forneymon of the same subtype as
     * a Forneymon already inside, will increase the level
     * Attempts to add a reference to the given Forneymon to 
     * the Forneymonagerie's collection
     * @param toAdd the Forneymon we are trying to add
     */
   public boolean collect (Forneymon toAdd) {
	 checkAndGrow ();
	 for (int i = 0; i < this.size; i++) {
     	if (this.collection[i] == toAdd) {
        		return false;
        	}
     	if (this.collection[i].getFMType().equals(toAdd.getFMType()) && 
     			this.collection[i].getLevel() != toAdd.getLevel()) {
     		this.collection[i].addLevels(toAdd.getLevel());
     		return false;
     	}
	 }
     this.collection[size] = toAdd;
     this.size++;
     return true;
    }
    
   /**
    * Removes the Forneymon of the given subtype fmType from 
    * the Forneymonagerie, maintaining the relative order of 
    * remaining Forneymon in the collection, and returning true.
    * If the given fmType does not exist in the Forneymonagerie, 
    * do nothing, and return false.
    * @param fmType the type we want to release
    */
    public boolean releaseType (String fmType) {
        int originalSize = this.size();
    	for (int i = 0; i < this.size; i++) {
        	if (this.collection[i].getFMType().equals(fmType)) {
        		shiftLeft(i, this.size - 1);
        		this.size--;
        	}
        }
        if (originalSize == this.size) {
        	return false;
        }
        else {
        	return true;
        }
    }
    
    /**
     * Returns the Forneymon at the given index in the collection, 
     * if valid.
     * @param index Index of Forneymon we want to get 
     */
    public Forneymon get (int index) {
        checkValidIndex(index, 0, this.size);
		return this.collection[index];
	}

    /**
     * Removes and returns the Forneymon at the given index, 
     * if valid, and maintains the relative order of remaining Forneymon 
     * in the collection.
     * @param index Index at which we want to remove the Forneymon
     */
    public Forneymon remove (int index) {
        checkValidIndex(index, 0, this.size);
    	Forneymon originalAtIndex = this.get(index);
    	shiftLeft(index, this.size - 1);
    	this.size--;
    	return originalAtIndex;
    }
    
    /**
     * Returns the index of a Forneymon with the given fmType 
     * in the collection, or -1 if that type is not found within.
     * @param fmType Type of Forneymon of which we want the index
     */
    public int getTypeIndex (String fmType) {
    	int index = -1;
        for (int i = 0; i < this.size; i++) {
        	if (this.collection[i].getFMType().equals(fmType)) {
        		index = i;
        	}
        }
        return index;
    }
    
    /**
     * Returns true if the given fmType is found within the 
     * Forneymonagerie's collection.
     * @param toCheck Type we are checking if the Forneymonagerie contains
     */
    public boolean containsType (String toCheck) {
        for (int i = 0; i < size; i++) {
        	if (this.collection[i].getFMType().equals(toCheck)) {
        		return true;
        	}
        }
        return false;
    }
    
    /**
     * Swaps the contents of the calling Forneymonagerie and the other specified.
     * @param other The content we want to swap
     */
    public void trade (Forneymonagerie other) {
    	int originalSize = this.size();
    	Forneymon[] originalCollection = collection.clone();
    	
    	this.size = other.size;
    	this.collection = other.collection;
    	
    	other.size = originalSize;
    	other.collection = originalCollection;
    	
    }
    
    public void rearrange (String fmType, int index) {
        checkValidIndex(index, 0, this.size);
        int indexOfFMtype = this.getTypeIndex(fmType);
        Forneymon fmFMtype = this.get(indexOfFMtype).clone();
        
        if (index > indexOfFMtype) {
            shiftLeft(indexOfFMtype, index);
            this.collection[index] = fmFMtype;
        } else {
            this.size++;
            checkAndGrow();
            shiftRight(index);
            this.collection[index] = fmFMtype;
            remove(indexOfFMtype + 1);
        } 
    }
    
    @Override
    public Forneymonagerie clone () {
    	Forneymonagerie newForneymonagerie = new Forneymonagerie();
        newForneymonagerie.size = this.size();
        newForneymonagerie.checkAndGrow();

        for (int i = 0; i < newForneymonagerie.size; i++) {
        	newForneymonagerie.collection[i] = this.get(i).clone();
        }     
        return newForneymonagerie;
    }
    
    /**
     * Returns whether or not the given Object other is an equivalent 
     * Forneymonagerie to this one, which we define as meaning that 
     * it contains equal Forneymon in the same order in the collection as this one.
     * @param other the Forneymonagerie that we are checking to see if it equals
     */
    @Override
    public boolean equals (Object other) {
		if (this.getClass() != other.getClass()) {
			return false;
		}
		Forneymonagerie otherAsForneymonagerie = (Forneymonagerie) other;
		
		if (this.size != otherAsForneymonagerie.size) {
			return false;
		}
		
		for (int i = 0; i < this.size; i++) {
			if (!this.collection[i].equals(otherAsForneymonagerie.collection[i])) {
				return false;
			}
		} 
		return true;
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
   
    
    /**
     * Ensures that the requested index is within a accepted/allowed
     * range, as also specified
     * @param index The index to check
     * @param lowerBound The allowed lower bound (exclusive)
     * @param upperBound The allowed upper bound (inclusive)
     */
    private void checkValidIndex (int index, int lowerBound, int upperBound) {
        if (index < lowerBound || index >= upperBound) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    /*
     * Shifts all elements to the right of the given
     * index one right
     * @param index Starting index to shift right from
     */
    private void shiftRight (int index) {
        for (int i = size-1; i >= index; i--) {
            collection[i+1] = collection[i];
        }
    }
    
    /**
	 * Shifts all elements to the right of index
	 * left by 1 space
	 * @param index Starting index to shift left from
	 */
    private void shiftLeft (int startIndex, int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			collection[i] = collection[i+1];
		}
	}
    
    /**
	 * Check before adding a new int, that there's room
	 * to hold it in our items -- if not, "grow" the available
	 * space
	 */
	private void checkAndGrow () {
		if (this.size < this.collection.length) {
			return;
		} else {
			Forneymon[] newCollection = new Forneymon[this.collection.length * 2];
			
			for (int i = 0; i < this.collection.length; i++) {
				newCollection[i] = this.collection[i];
			}
			this.collection = newCollection;
		}
	}
}


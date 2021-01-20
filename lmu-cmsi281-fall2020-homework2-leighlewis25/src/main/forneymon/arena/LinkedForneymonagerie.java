package main.forneymon.arena;

import java.util.Objects;

import main.forneymon.fmtypes.*;


/**
 * Collections of Forneymon ready to fight in the arena!
 */
public class LinkedForneymonagerie implements ForneymonagerieInterface {

    // Fields
    // -----------------------------------------------------------
    private Node sentinel;
    private int size, modCount;


    // Constructor
    // -----------------------------------------------------------
    public LinkedForneymonagerie () {
        // [!] Leave this constructor as-is, you may not modify!
        this.size = this.modCount = 0;
        this.sentinel = new Node(null);
        this.sentinel.next = this.sentinel;
        this.sentinel.prev = this.sentinel;
    }


    // Methods
    // -----------------------------------------------------------

    /**
     * Checks whether or not the LinkedForneymonagerie has any Forneymon.
     * @return true if the LinkedForneymonagerie has no Forneymon, else false
     */
    public boolean empty () {
        return this.sentinel.next == this.sentinel && this.sentinel.prev == this.sentinel;
    }

    /**
     * Finds the number of Forneymon in the LinkedList.
     * @return the current size of the LinkedForneymonagerie
     */
    public int size () {
        return this.size;
    }

    /**
     * Attempts to add a reference to the given Forneymon to the 
     * LinkedForneymonagerie's collection:
     * - Adds a Forneymon to a LinkedForneymonagerie of a type that
     * doesn't currently exist within adds it to the last open index.
     * - If trying to add a Forneymon that is already stored within 
     * the LinkedForneymonagerie, will return false.
     * - If trying to add the Forneymon of the same subtype as
     * a Forneymon already inside, will increase the level
     * Attempts to add a reference to the given Forneymon to 
     * the Forneymonagerie's collection
     * @param toAdd the Forneymon we are trying to add
     * @return false if the Forneymon toAdd (or the given subtype of the Forneymon)
     * has already been stored, otherwise true.
     */
    public boolean collect (Forneymon toAdd) {
        int existingIndex = getTypeIndex(toAdd.getFMType());

        if (existingIndex != -1) {
            Forneymon existingFM = get(existingIndex);
            if (existingFM != toAdd) {
                existingFM.addLevels(toAdd.getLevel());
            }      
            return false;
        } else {
            return this.append(toAdd);
        }
    }

    /**
     * Removes the Forneymon of the given subtype fmType from the 
     * LinkedForneymonagerie, maintaining the relative order of 
     * remaining Forneymon in the LinkedList, and returning true.
     * @param fmType The subtype of Forneymon that will be removed.
     * @return true if the type being released exists within the collection,
     * else false
     */
    public boolean releaseType (String fmType) {
        Node current = this.sentinel.next;

        while (current != this.sentinel) {
            if (current.fm.getFMType().equals(fmType)) {
                remove(getTypeIndex(current.fm.getFMType()));
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Gets the Forneymon at the given index in the LinkedList, 
     * if valid.
     * @param index The index of the Forneymon we would like to get.
     * @return the Forneymon at the given index. 
     */
    public Forneymon get (int index) {
        checkValidIndex(index, 0, this.size - 1);
        return getCurrentNode(index).fm;
    }

    /**
     * Removes the Forneymon at the given index, if valid, 
     * and maintains the relative order of remaining Forneymon in the LinkedList.
     * @param index Index of Forneymon we want to remove
     * @return the Forneymon being removed
     */
    public Forneymon remove (int index) {
        Node current = getCurrentNode(index);
        Forneymon toReturn = current.fm;

        current.next.prev = current.prev;
        current.prev.next = current.next;
        this.modCount++;
        this.size--;
        return toReturn;
    }

    /**
     * Gets the index of a Forneymon with the given fmType
     * @param fmType the type of the Forneymon of which we want the index.
     * @return the index of a Forneymon with the given fmType in the LinkedList,
     * or returns -1 if that type is not found within
     */
    public int getTypeIndex (String fmType) {
        int track = 0;
        int index = -1;
        Node current = this.sentinel.next;

        while (current != this.sentinel) {
            if (current.fm.getFMType().equals(fmType)) {
                index = track;
            }
            current = current.next;
            track++;
        }
        return index;
    }

    /**
     * Checks whether or not the LinkedForneymonagerie contains the given type.
     * @param toCheck The type we will check if the LinkedForneymonagerie contains.
     * @return true if the given fmType is found within the LinkedForneymonagerie,
     * else returns false
     */
    public boolean containsType (String toCheck) {
        return getTypeIndex(toCheck) != -1;
    }

    /**
     * Swaps the contents of the calling LinkedForneymonagerie and the other specified.
     * @param other The content we want to swap / trade 
     */
    public void trade (LinkedForneymonagerie other) {
        int thisOriginalSize = this.size();
        int otherModCount = this.getModCount() + 1;
        LinkedForneymonagerie temp = new LinkedForneymonagerie();

        temp.sentinel = other.sentinel;        
        other.sentinel = this.sentinel;
        this.sentinel = temp.sentinel;

        this.size = other.size;
        other.size = thisOriginalSize;
        this.modCount = other.modCount + 1;
        other.modCount = otherModCount;
    }

    /**
     * Moves the Forneymon of the given fmType from its current position in the LinkedForneymonagerie
     * to the one specified by the index, shifting any existing Forneymon around the requested 
     * index so that the relative indexing is preserved.
     * @param fmType The type of the Forneymon we hope to move to the index.
     * @param index The index at which we would like to insert fmType.
     */
    public void rearrange (String fmType, int index) {
        this.checkValidIndex(index, 0, this.size);
        this.checkContainsType(fmType);     
        int originalIndex = this.getTypeIndex(fmType);
        Node nodeToMove = getCurrentNode(originalIndex);

        remove(originalIndex);
        insertAt(nodeToMove, index);
    }

    /**
     * Makes a new Iterator on the calling LinkedForneymonagerie that begins on the first Node in 
     * the sequence.
     * @return A new iterator
     */
    public LinkedForneymonagerie.Iterator getIterator () {
        return new Iterator(this);
    }

    /**
     * Produces a copy of the calling LinkedForneymonagerie that operates independently 
     * from the original.
     * @return a deep copy of this LinkedForneymonagerie
     */
    @Override
    public LinkedForneymonagerie clone () {
        LinkedForneymonagerie newLinkedForneymonagerie = new LinkedForneymonagerie();
        Node current = this.sentinel.next;
        newLinkedForneymonagerie.modCount = this.getModCount();

        while (current != this.sentinel) {
            newLinkedForneymonagerie.collect(current.fm.clone());
            current = current.next;
        }
        return newLinkedForneymonagerie;
    }

    /**
     * Checks whether or not this LinkedForneymonagerie is equivalent to the given 
     * Object other.
     * @param other The Object we are comparing to this LinkedForneymonagerie to check
     * equivalence.
     * @return true if the given Object other is an equivalent LinkedForneymonagerie
     * to this one or false if not.
     */
    @Override
    public boolean equals (Object other) {
        Node current = this.sentinel.next;

        if (this.getClass() != other.getClass()) {
            return false;
        }

        LinkedForneymonagerie otherAsLinked = (LinkedForneymonagerie) other;
        Node otherCurrent = otherAsLinked.sentinel.next;

        if (this.size != otherAsLinked.size) {
            return false;
        }

        while (current != this.sentinel) {
            if (!current.fm.equals(otherCurrent.fm)) {
                return false;
            }
            current = current.next;
            otherCurrent = otherCurrent.next;
        }
        return true;
    }

    @Override
    public int hashCode () {
        return Objects.hash(this.sentinel, this.size, this.modCount);
    }

    @Override
    public String toString () {
        String[] result = new String[size];
        int i = 0;
        for (Node curr = this.sentinel.next; curr != this.sentinel; curr = curr.next, i++) {
            result[i] = curr.fm.toString();
        }
        return "[ " + String.join(", ", result) + " ]";
    }


    // Private helper methods
    // -----------------------------------------------------------

    /**
     * Ensures that the requested index is within a accepted/allowed
     * range, as also specified
     * @param index The index to check
     * @param lowerBound The allowed lower bound (exclusive)
     * @param upperBound The allowed upper bound (inclusive)
     */
    private void checkValidIndex (int index, int lowerBound, int upperBound) {
        if (index < lowerBound || index > upperBound || this.empty()) {
            throw new IllegalArgumentException("Index " + index +" not valid for linked list of size " + size);
        }
    }

    /**
     * Gets the modification count of the current LinkedForneymonagerie
     * @return The modification count
     */
    private int getModCount() {
        return this.modCount;
    }

    /**
     * Gets the Node at the given index
     * @param index the index at which we want to get the Node
     * @return the Node at the index
     */
    private Node getCurrentNode(int index) {
        checkValidIndex(index, 0, this.size);
        Node current = this.sentinel.next;
        while (index > 0) {
            current = current.next;
            index--;
        }
        return current;
    }

    /**
     * Checks whether or not the fmType is contained within the
     * LinkedForneymonagerie and throws IllegalArgumentException 
     * if not found within
     * @param fmType The type of Forneymon we are checking to see
     * if the LinkedForneymonagerie contains
     */
    private boolean checkContainsType(String fmType) {
        if (!containsType(fmType)) {
            throw new IllegalArgumentException();
        }
        return true;
    }

    /**
     * Adds a Forneymon to the end of the LinkedForneymonagerie regardless
     * of type
     * @param toAdd The Forneymon to be added to the LinkedForneymonagerie
     * @return true after Forneymon has been added
     */
    private boolean append(Forneymon toAdd) {
        Node toAddNode = new Node(toAdd);

        if (this.size == 0) {
            this.sentinel.next = toAddNode;
        }

        toAddNode.prev = this.sentinel.prev;
        this.sentinel.prev.next = toAddNode;
        this.sentinel.prev = toAddNode;
        toAddNode.next = this.sentinel;
        this.modCount++;
        this.size++;
        return true;
    }
    /**
     * Adds a Forneymon to the beginning of the LinkedForneymonagerie
     * regardless of type
     * @param toAdd The forneymon to be added to the Linkedforneymonagerie
     */
    private void prepend(Forneymon toAdd) {
        Node toAddNode = new Node(toAdd);

        if (this.empty()) {
            toAddNode.prev = this.sentinel;
            toAddNode.next = this.sentinel;  
            this.sentinel.next = toAddNode;
            this.sentinel.prev = toAddNode;
        } else {
            toAddNode.prev = this.sentinel;
            toAddNode.next = this.sentinel.next;
            this.sentinel.next.prev = toAddNode;
            this.sentinel.next = toAddNode;
        }
        this.size++;	
    }

    /**
     * Inserts a new forneymon into the LinkedForneymonagerie at the given index.
     * @param toAdd The Node with the Forneymon we want to add
     * @param index The index at which we want to insert the forneymon.
     */
    private void insertAt(Node toAdd, int index) {
        Node original = getCurrentNode(index);

        if (index == 0) {
            prepend(toAdd.fm);
        } else if (index == size) {
            append(toAdd.fm);
            this.modCount--;
        } else {
            toAdd.next = original;
            original.prev.next = toAdd;
            toAdd.prev = original.prev;
            original.prev = toAdd;
            this.size++;
        }
    }


    // Inner Classes
    // -----------------------------------------------------------

    public class Iterator {
        private LinkedForneymonagerie host;
        private Node current;
        private int itModCount;

        Iterator (LinkedForneymonagerie host) {
            this.host = host;
            this.current = host.sentinel.next;
            itModCount = host.modCount;
        }

        /**
         * Checks whether or not the iterator has reached the end of the host's
         * LinkedForneymonagerie (at the last node before the sentinel node)
         * @return true if the iterator is valid and its current.next is the 
         * host's sentinel node; false otherwise
         */
        public boolean atEnd () {
            return this.isValid() && this.current.next == host.sentinel;
        }

        /**
         * Checks whether or not the iterator is at the beginning of its host (at the first node
         * after the sentinel node)
         * @return true if the iterator is valid and its current.prev is the host's
         * sentinel node; false otherwise
         */
        public boolean atStart () {
            return this.isValid() && this.current.prev == host.sentinel;
        }

        /**
         * Checks whether or not the iterator is in the right place
         * @return true if the iterator's itModCount agrees with that of its
         * host's modCount and if the host LinkedForneymonagerie has at least one element;
         * false otherwise
         */
        public boolean isValid () {
            return itModCount == host.modCount && !host.empty();
        }

        /**
         * Gets the current Forneymon
         * @return the Forneymon stored in the Node that the iterator is currently pointing to
         * (i.e. returns the fm field of the Node it is referring to)
         */
        public Forneymon getCurrent () {
            checkState();
            return this.current.fm;
        }

        /**
         * Advances the iterator's current reference to point to the next node in the sequence.
         * If the next node is the sentinel, it will advance next a second time.
         */
        public void next () {
            checkState();
            if (atEnd()) {
                this.current = host.sentinel.next;
            } else {
                this.current = this.current.next;
            }
        }

        /**
         * Advances the iterator's current reference to point to the previous node in the sequence.
         * If the previous node is the sentinel, it will advance previous a second time.
         */
        public void prev () {
            checkState();
            if (atStart()) {
                this.current = host.sentinel.prev;
            } else {
                this.current = this.current.prev;
            }
        }

        /**
         * Removes the node that the iterator is referencing and moves the iterator to the node
         * previous to the one deleted.
         * @return a reference to the Forneymon of the removed node
         */
        public Forneymon removeCurrent () {
            checkState();
            Forneymon toRemove = this.current.fm;
            this.current.next.prev = this.current.prev;
            this.current.prev.next = this.current.next;
            this.current = this.current.prev;
            host.modCount++;
            this.itModCount++;
            host.size--;
            return toRemove;
        }

        // Private helper method
        // -----------------------------------------------------------

        /**
         * Throws an Illegal State Exception if the iterator is no longer valid.
         */
        private void checkState() {
            if (!this.isValid()) {
                throw new IllegalStateException();
            }
        }
    }

    private class Node {
        Node next, prev;
        Forneymon fm;

        Node (Forneymon fm) {
            this.fm = fm;
        }
    }

}

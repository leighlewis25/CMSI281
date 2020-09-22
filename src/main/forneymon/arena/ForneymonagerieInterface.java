package main.forneymon.arena;

import main.forneymon.fmtypes.*;

public interface ForneymonagerieInterface {

    boolean empty ();
    int size ();
    boolean collect (Forneymon toAdd);
    boolean releaseType (String fmType);
    Forneymon get (int index);
    Forneymon remove (int index);
    int getTypeIndex (String fmType);
    boolean containsType (String fmType);
    void rearrange (String fmType, int index);
    void trade (Forneymonagerie other);
    Forneymonagerie clone ();
    
}

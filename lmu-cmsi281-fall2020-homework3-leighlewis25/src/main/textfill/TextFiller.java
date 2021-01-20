package main.textfill;

import java.util.*;

public interface TextFiller {

    int size ();
    boolean empty ();
    void add (String toAdd);
    boolean contains (String query);
    String textFill (String query);
    List<String> getSortedList ();

}

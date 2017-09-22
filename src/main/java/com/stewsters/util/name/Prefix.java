package com.stewsters.util.name;

import java.util.ArrayList;

public class Prefix<K> {
    public ArrayList<K> pref;    // prefixLength adjacent words from input
    static final int MULTIPLIER = 31;    // for hashCode()

    // Prefix constructor: duplicate existing prefix
    public Prefix(Prefix<K> p) {
        pref = (ArrayList<K>) p.pref.clone();
    }

    // Prefix constructor: n copies of str
    public Prefix(int n, K str) {
        pref = new ArrayList<K>();
        for (int i = 0; i < n; i++) {
            pref.add(str);
        }
    }

    // Prefix hashCode: generate hash from all prefix words
    public int hashCode() {
        int h = 0;

        for (int i = 0; i < pref.size(); i++) {
            h = MULTIPLIER * h + pref.get(i).hashCode();
        }
        return h;
    }

    // Prefix equals: compare two prefixes for equal words
    public boolean equals(Prefix<K> p) {

        for (int i = 0; i < pref.size(); i++) {
            if (!pref.get(i).equals(p.pref.get(i))) {
                return false;
            }
        }
        return true;
    }


    public boolean equals(Object other) {
        Prefix<K> p = (Prefix<K>) other;

        for (int i = 0; i < pref.size(); i++) {
            if (!pref.get(i).equals(p.pref.get(i))) {
                return false;
            }
        }
        return true;
    }
}

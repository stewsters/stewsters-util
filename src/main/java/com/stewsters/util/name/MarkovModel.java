package com.stewsters.util.name;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class MarkovModel<K> {

    private Random rand = new Random();     // initial prefix
    private final int prefixLength;    // size of prefix
    private final K NONWORD; // "word" that can't appear

    private Hashtable<Prefix<K>, ArrayList<K>> statetab;

    // Prefix to Suffix
    private Prefix<K> prefix;

    public MarkovModel(K nonword, int prefixLength) {
        NONWORD = nonword;
        this.prefixLength = prefixLength;
        statetab = new Hashtable<>();
        prefix = new Prefix<K>(prefixLength, NONWORD);
    }

    public void addSample(K[] list) {
        prefix = new Prefix<K>(prefixLength, NONWORD);
        for (K k : list) {
            add(k);
        }
        add(NONWORD);
    }

    // Chain add: add word to suffix list, update prefix
    private void add(K word) {
        ArrayList<K> suf = statetab.get(prefix);
        if (suf == null) {
            suf = new ArrayList<K>();
            statetab.put(new Prefix<K>(prefix), suf);
        }
        suf.add(word);
        prefix.pref.remove(0);
        prefix.pref.add(word);
    }

    // Chain generate: generate output words
    public List<K> generate(int maxLength) {
        List<K> output = new ArrayList<>();

        prefix = new Prefix(prefixLength, NONWORD);
        for (int i = 0; i < maxLength; i++) {
            ArrayList<K> possibleSuffixes = statetab.get(prefix);
            if (possibleSuffixes == null) {
                System.err.println("Markov: internal error: no state");
                System.exit(1);
            }
            int r = Math.abs(rand.nextInt()) % possibleSuffixes.size();
            K suf = possibleSuffixes.get(r);
            if (suf.equals(NONWORD)) {
                break;
            }
            output.add(suf);

            prefix.pref.remove(0);
            prefix.pref.add(suf);
        }
        return output;
    }
}

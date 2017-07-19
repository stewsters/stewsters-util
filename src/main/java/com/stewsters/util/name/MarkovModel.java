package com.stewsters.util.name;

import java.util.*;

import static com.stewsters.util.math.MatUtils.rand;

public class MarkovModel<T> {

    private Map<T, ArrayList<T>> markovChain;
    final T start;
    final T end;

    public MarkovModel(T start, T end) {
        this.start = start;
        this.end = end;

        markovChain = new Hashtable<>();
        markovChain.put(start, new ArrayList<T>());
    }

    public void addSample(List<T> sample) {

        for (int i = 0; i < sample.size(); i++) {

            if (i == 0) {
                ArrayList<T> startWords = markovChain.get(start);
                startWords.add(sample.get(0));

            } else {
                ArrayList<T> suffix = markovChain.get(sample.get(i));
                if (suffix == null) {
                    suffix = new ArrayList<T>();
                    markovChain.put(sample.get(i), suffix);
                }

                if (i == sample.size() - 1) {
                    suffix.add(end);
                } else
                    suffix.add(sample.get(i + 1));
            }
        }

    }

    public ArrayList<T> generateSample(int min, int max) {

        ArrayList<T> newPhrase = new ArrayList<T>();
        T nextWord = rand(markovChain.get(start));

        // Keep looping through the words until we've reached the end
        while (true) {

            newPhrase.add(nextWord);
            ArrayList<T> nextOptions = markovChain.get(nextWord);

            if (nextOptions == null || nextOptions.size() <= 0)
                break;

            if ( newPhrase.size() < min) {
                nextOptions = (ArrayList<T>) nextOptions.clone();
                nextOptions.removeAll(Collections.singleton(end));
            }

            if (newPhrase.size() >= max)
                break;

            try {
                nextWord = rand(nextOptions);
            } catch (Exception e){
                e.printStackTrace();
            }
            if (nextWord == end) {
                break;
            }
        }

        return newPhrase;
    }
}

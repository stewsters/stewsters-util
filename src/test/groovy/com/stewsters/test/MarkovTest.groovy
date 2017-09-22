package com.stewsters.test

import com.stewsters.util.name.MarkovModel
import groovy.json.internal.ArrayUtils
import groovy.transform.CompileStatic

import org.junit.Test

import static com.stewsters.util.name.StringUtils.stringToCharacterArray

@CompileStatic
class MarkovTest {
    static final int MAX_WORDS_GEN = 10000; // maximum words generated

    @Test
    void testWordGeneration() {
        MarkovModel<Character> chain = new MarkovModel<>(" " as Character, 3)
        List<String> words = getCorpi(['lovecraft','grimms']).join(" ")
                .replaceAll("[^a-zA-Z ]", "")
                .toLowerCase()
                .split("\\s")*.trim()
                .unique()

        words.each { String word ->
            chain.addSample(stringToCharacterArray(word))
        }

        List<String> generated = []
        while (generated.size() < 30) {
            String candidate = chain.generate(MAX_WORDS_GEN).join("")
            if (!generated.contains(candidate) && !words.contains(candidate)) {
                generated << candidate
            } else {
                println "candidate rejected : $candidate"
            }
        }

        generated.each {
            println it.toLowerCase().capitalize()
        }
    }

    @Test
    void testWordGenerationFromDict() {
        MarkovModel<Character> chain = new MarkovModel<>(" " as Character, 3)
        List<String> dictionary = dictCorp
        dictionary.each { word ->
            chain.addSample(stringToCharacterArray(word))
        }

        List<String> generated = []
        while (generated.size() < 30) {
            String candidate = chain.generate(MAX_WORDS_GEN).join("")
            if (!generated.contains(candidate) && !dictionary.contains(candidate)) {
                generated << candidate
            }
        }

        generated.each {
            println it.toLowerCase().capitalize()
        }
    }

    @Test
    void testStoryGeneration() {
        MarkovModel<String> chain = new MarkovModel<String>("\n", 3)

        getCorpi(["lovecraft", "grimms", "prideAndPrejudice"])*.toLowerCase().each {
            it.split('\\.').each { String sentence ->
                String[] sample = sentence
                        .replaceAll("[^a-zA-Z \\n]", "")
                        .trim()
                        .split('\\s')
                if (sample.size() > 1)
                    chain.addSample(sample)
            }
        }

        30.times {
            print chain.generate(MAX_WORDS_GEN).join(" ").capitalize() + '.  '
        }
    }

    @Test
    void createAlchemyNotes() {
        MarkovModel<String> chain = new MarkovModel<String>("\n", 3)

        getCorpi(["naturalAndSupernaturalThings"])*.toLowerCase().each {
            it.split('\\.').each { String sentence ->
                String[] sample = sentence
                        .replaceAll("[^a-zA-Z \\n]", "")
                        .trim()
                        .split('\\s')
                if (sample.size() > 1)
                    chain.addSample(sample)
            }
        }

        5.times {
            println chain.generate(50).join(" ").capitalize() + '.  '
        }
    }


    @Test
    void testGenerateGreekNames() {
        MarkovModel<Character> male = new MarkovModel<>(" " as Character, 2)
        new File("build/resources/test/corpus/greekMaleNames.txt").eachLine {
            male.addSample(stringToCharacterArray(it))
        }

        30.times {
            println male.generate(20).join('')
        }

        MarkovModel<Character> female = new MarkovModel<>(" " as Character, 2)
        new File("build/resources/test/corpus/greekFemaleNames.txt").eachLine {
            female.addSample(stringToCharacterArray(it))
        }
        30.times {
            println female.generate(20).join('')
        }

    }

    private List<String> getCorpi() {
        getCorpi(["lovecraft", "prideAndPrejudice", "grimms", "naturalAndSupernaturalThings"])
    }

    private List<String> getCorpi(List<String> files) {
        files.collect { new File("build/resources/test/corpus/${it}.txt").text }
    }

    private List<String> getDictCorp() {
        new File("build/resources/test/corpus/cmudict/cmudict.dict").text.split("\n").collect { String it ->
            it.split(' ').first()
        }.findAll { !it.contains('(') }
    }

}
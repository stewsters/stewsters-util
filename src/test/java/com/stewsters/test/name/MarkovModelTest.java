package com.stewsters.test.name;

import com.stewsters.util.name.MarkovModel;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.stewsters.util.name.ModernNameGen.capitalize;

public class MarkovModelTest {

    @Test
    public void testMarkovModelWithCharacters() {
        MarkovModel<Character> markovMaleModel = new MarkovModel<>('+', '-');

        Path path = Paths.get("build/resources/main/corpus/greekMaleNames.txt");

        try (Stream<String> stream = Files.lines(path)) {
            stream.forEach(string -> markovMaleModel.addSample(stringToCharacterList(string)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(
                    markovMaleModel.generateSample(5, 8).stream()
                            .map(e -> e.toString()).collect(Collectors.joining()));
        }

    }

    @Test
    public void testMarkovModelWithStrings() throws IOException {
        MarkovModel<String> markovModel = new MarkovModel<>("+", "-");

        Path path = Paths.get("build/resources/main/corpus/grimms.txt");

        String fullText = Files.readAllLines(path)
                .stream()
                .collect(Collectors.joining(" ")).toLowerCase()
                .replaceAll("[\\p{P}&&[^\u002e]]", " "); // Replace non period punctuation with spaces.

        List<String> sentences = Arrays.asList(fullText
                .split("\\."));

        sentences.forEach(sentence ->
                markovModel.addSample(Arrays.asList(sentence.trim().split("\\s+")))
        );

        for (int i = 0; i < 20; i++) {
            System.out.println(
                    capitalize(markovModel.generateSample(3, 12).stream().collect(Collectors.joining(" "))) + ". ");
        }
    }

    private ArrayList<Character> stringToCharacterList(String string) {
        return new ArrayList<>(string.chars().mapToObj(e -> (char) e).collect(Collectors.toList()));
    }
}

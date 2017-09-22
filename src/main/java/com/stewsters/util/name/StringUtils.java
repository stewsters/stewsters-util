package com.stewsters.util.name;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class StringUtils {
    public static Character[] stringToCharacterArray(String string) {
        return string.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
    }

    public static ArrayList<Character> stringToCharacterList(String string) {
        return new ArrayList<>(string.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
    }
}

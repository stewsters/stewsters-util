package com.stewsters.util.types;

import com.stewsters.util.math.MatUtils;

public enum Gender {
    MALE('♂'),
    FEMALE('♀'),
    NEUTER('⚲');

    public char glyph;

    Gender(char glyph) {
        this.glyph = glyph;
    }

    public static Gender random() {
        return MatUtils.getBoolean() ? Gender.MALE : Gender.FEMALE;
    }
}
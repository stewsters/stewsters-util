package com.stewsters.util.types;

import com.stewsters.util.math.MathUtils;

public enum Gender {
    MALE('♂'),
    FEMALE('♀'),
    NEUTER('⚲');

    public char glyph;

    Gender(char glyph) {
        this.glyph = glyph;
    }

    public static Gender random() {
        return MathUtils.getBoolean() ? Gender.MALE : Gender.FEMALE;
    }
}
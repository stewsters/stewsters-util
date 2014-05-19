package com.stewsters.util.types;

import com.stewsters.util.math.MatUtils;

public enum Gender {
    MALE(),
    FEMALE(),
    NEUTER();

    public static Gender random() {
        return MatUtils.getBoolean() ? Gender.MALE : Gender.FEMALE;
    }
}
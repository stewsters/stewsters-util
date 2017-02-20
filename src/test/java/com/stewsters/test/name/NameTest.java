package com.stewsters.test.name;

import com.stewsters.util.name.NameGen;
import org.junit.Test;

public class NameTest {

    @Test
    public void testGeneratingName() {
        String name = NameGen.generate();
        assert name.length() >= 3;
        assert name.contains(" ");
    }

    @Test
    public void testGeneratingMaleName() {
        String name = NameGen.randomMaleFirstName();
        assert name.length() >= 1;
        assert !name.contains(" ");
    }

    @Test
    public void testGeneratingFemaleName() {
        String name = NameGen.randomFemaleFirstName();
        assert name.length() >= 1;
        assert !name.contains(" ");
    }

    @Test
    public void testGeneratingLastName() {
        String name = NameGen.randomLastName();
        assert name.length() >= 1;
        assert !name.contains(" ");
    }

}

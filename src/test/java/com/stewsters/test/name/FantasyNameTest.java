package com.stewsters.test.name;

import com.stewsters.util.name.FantasyNameGen;
import org.junit.Test;

public class FantasyNameTest {

    @Test
    public void testGeneratingName() {
        String name = FantasyNameGen.generate();
        assert name.length() >= 3;
        assert name.contains(" ");
    }

    @Test
    public void testGeneratingMaleName() {
        String name = FantasyNameGen.randomMaleFirstName();
        assert name.length() >= 1;
    }

    @Test
    public void testGeneratingFemaleName() {
        String name = FantasyNameGen.randomFemaleFirstName();
        assert name.length() >= 1;
    }

    @Test
    public void testGeneratingLastName() {
        String name = FantasyNameGen.randomLastName();
        assert name.length() >= 1;
    }

}

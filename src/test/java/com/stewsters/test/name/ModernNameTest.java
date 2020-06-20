package com.stewsters.test.name;

import com.stewsters.util.name.ModernNameGen;
import org.junit.Test;

public class ModernNameTest {

    @Test
    public void testGeneratingName() {
        String name = ModernNameGen.generate();
        assert name.length() >= 3;
        assert name.contains(" ");
    }

    @Test
    public void testGeneratingMaleName() {
        String name = ModernNameGen.randomMaleFirstName();
        assert name.length() >= 1;
        assert !name.contains(" ");
    }

    @Test
    public void testGeneratingFemaleName() {
        String name = ModernNameGen.randomFemaleFirstName();
        assert name.length() >= 1;
        assert !name.contains(" ");
    }

    @Test
    public void testGeneratingLastName() {
        String name = ModernNameGen.randomLastName();
        assert name.length() >= 1;
        assert !name.contains(" ");
    }

}

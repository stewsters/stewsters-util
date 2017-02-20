package com.stewsters.test.types;

import com.stewsters.util.types.ClassicElement;
import com.stewsters.util.types.Gender;
import com.stewsters.util.types.rank.MedievalRank;
import com.stewsters.util.types.rank.UsArmyEnlistedRank;
import com.stewsters.util.types.rank.UsArmyOfficerRank;
import org.junit.Test;

public class EnumTests {

    @Test
    public void makeSureTheyAreCalledOnce() {
        assert Gender.values().length > 0;
        assert ClassicElement.values().length > 0;
        assert MedievalRank.values().length > 0;
        assert UsArmyEnlistedRank.values().length > 0;
        assert UsArmyOfficerRank.values().length > 0;
    }
}

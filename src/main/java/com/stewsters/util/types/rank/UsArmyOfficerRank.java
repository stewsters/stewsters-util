package com.stewsters.util.types.rank;


public enum UsArmyOfficerRank {

    SECOND_LIEUTENANT(1, "2LT", "Second Lieutenant"),
    FIRST_LIEUTENANT(2, "1LT", "First Lieutenant"),
    CAPTAIN(3, "CPT", "Captain"),
    MAJOR(4, "MAJ", "Major"),
    LIEUTENANT_COLONEL(5, "LTC", "Lieutenant Colonel"),
    COLONEL(6, "COL", "COLONEL"),
    BRIGADIER_GENERAL(7, "BG", "Brigadier General"),
    MAJOR_GENERAL(8, "MG", "Major General"),
    LIEUTENANT_GENERAL(9, "BG", "Lieutenant General"),
    GENERAL(10, "GEN", "General");

    int rank;
    String abbreviation;
    String fullName;

    UsArmyOfficerRank(int rank, String abbreviation, String fullName) {
        this.rank = rank;
        this.abbreviation = abbreviation;
        this.fullName = fullName;
    }
}

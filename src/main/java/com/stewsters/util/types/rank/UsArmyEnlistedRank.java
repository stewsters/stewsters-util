package com.stewsters.util.types.rank;


public enum UsArmyEnlistedRank {


    PRIVATE_E1(1, "PV1", "Private E-1"),
    PRIVATE_E2(2, "PV2", "Private E-2"),
    PRIVATE_FIRST_CLASS(3, "PFC", "Private E-1"),
    CORPORAL(4, "CPL", "Corporal"),
    SPECIALIST(4, "SPC", "Specialist"),
    SERGEANT(5, "SGT", "Sergeant"),
    STAFF_SERGEANT(6, "SSG", "Staff Sergeant"),
    SERGENT_FIRST_CLASS(7, "SFC", "Sergeant First Class"),

    MASTER_SERGENT(8, "MSG", "Master Sergeant"),
    FIRST_SERGENT(8, "1SG", "First Sergeant"),
    SERGENT_MAJOR(9, "SGM", "Sergeant Major"),
    COMMAND_SERGEANT_MAJOR(9, "CSM", "Command Sergeant Major"),
    SERGENT_MAJOR_OF_THE_ARMY(10, "SMA", "Sergeant Major of the Army");

    int rank;
    String abbreviation;
    String fullName;

    UsArmyEnlistedRank(int rank, String abbreviation, String fullName) {
        this.rank = rank;
        this.abbreviation = abbreviation;
        this.fullName = fullName;
    }
}

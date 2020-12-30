package cz.cvut.bikesharingproject.model.enums;

public enum CSFType {

    FEEDBACK("FEEDBACK"),
    DAMAGED_BIKE("DAMAGED_BIKE"),
    RENTAL_PROBLEM("RENTAL_PROBLEM");

    private final String name;

    CSFType(String name) {
        this.name = name;
    }
}

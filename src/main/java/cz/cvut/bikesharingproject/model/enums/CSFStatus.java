package cz.cvut.bikesharingproject.model.enums;

public enum CSFStatus {

    OPENED("OPENED"),
    IN_PROGRESS("IN_PROGRESS"),
    CLOSED("CLOSED");

    private final String name;

    CSFStatus(String name) {
        this.name = name;
    }
}

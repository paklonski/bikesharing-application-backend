package cz.cvut.bikesharingproject.model.enums;

public enum CustomerSupportFormState {

    OPENED("OPENED"),
    IN_PROGRESS("IN_PROGRESS"),
    CLOSED("CLOSED");

    private final String name;

    CustomerSupportFormState(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

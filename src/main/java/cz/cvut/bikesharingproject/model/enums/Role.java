package cz.cvut.bikesharingproject.model.enums;

public enum Role {

    ADMIN("ADMIN"),
    USER("USER");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
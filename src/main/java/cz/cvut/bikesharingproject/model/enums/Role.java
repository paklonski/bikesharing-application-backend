package cz.cvut.bikesharingproject.model.enums;

public enum Role {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
package cz.cvut.bikesharingproject.model.enums;

public enum Role {

    ADMIN("ROLE_ADMIN"),
    GUEST("ROLE_GUEST"),
    USER("ROLE_USER");

    private final String name;

    Role(String name) {
        this.name = name;
    }
}
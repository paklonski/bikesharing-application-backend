package cz.cvut.bikesharingproject.model.enums;

public enum District {

    PRAHA_1("PRAHA_1"),
    PRAHA_2("PRAHA_2"),
    PRAHA_3("PRAHA_3"),
    PRAHA_4("PRAHA_4"),
    PRAHA_5("PRAHA_5"),
    PRAHA_6("PRAHA_6");

    private final String name;

    District(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

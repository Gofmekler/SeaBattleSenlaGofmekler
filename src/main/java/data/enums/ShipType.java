package data.enums;

public enum ShipType {
    LINCOR("lincor"),
    CRUISER("cruiser"),
    DESTROYER("destroyer"),
    ARMADILLO("armadillo"),
    YACHT("yacht"),
    BOAT("boat");

    private final String type;

    ShipType(String value) {
        this.type = value;
    }

    public String getType() {
        return type;
    }
}

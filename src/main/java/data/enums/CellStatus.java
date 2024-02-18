package data.enums;

public enum CellStatus {
    HIDDEN("hidden"),
    HIT("hit"),
    MISSED("missed");

    private final String status;

    CellStatus(String value) {
        this.status = value;
    }

    public String getStatus() {
        return status;
    }
}



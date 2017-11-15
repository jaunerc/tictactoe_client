package ch.jaunerc.ttt_client.tictactoe;

public enum FieldValue {
    CROSS("x"), NOUGHT("o"), EMPTY("n");

    private String value;

    FieldValue(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

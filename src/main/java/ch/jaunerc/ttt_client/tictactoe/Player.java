package ch.jaunerc.ttt_client.tictactoe;

public class Player {

    private FieldValue fieldValue;

    public Player(final FieldValue fieldValue) {
        this.fieldValue = fieldValue;
    }

    public FieldValue getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(FieldValue fieldValue) {
        this.fieldValue = fieldValue;
    }
}

package logic;

public class SlotIndex {

    private byte row;
    private byte col;

    public SlotIndex(byte row, byte col) {
        this.row = row;
        this.col = col;
    }

    public byte getRow() {
        return row;
    }

    public byte getCol() {
        return col;
    }

}

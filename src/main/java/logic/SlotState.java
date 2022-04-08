package logic;

public enum SlotState {
    AGENT(0),
    USER(1),
    EMPTY(2);

    private final int id;
    SlotState(int id) {this.id = id;}
    public int getValue() {return id;}
}

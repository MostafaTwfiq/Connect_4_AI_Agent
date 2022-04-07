import java.util.ArrayList;
import java.util.List;

public class StateOperations {

    public static final byte ROW_SIZE = 6;
    public static final byte COL_SIZE = 7;

    public static long playAtCol(long state, int col, SlotState slotState) {
        int colCount = numOfElementsAtCol(state, col);
        int colStateStartBit = (ROW_SIZE + 3) * col + ROW_SIZE;
        int slotIndex = (ROW_SIZE + 3) * col + colCount;
        state = setSlotValue(state, slotIndex, slotState);
        state += (1L << (63 - colStateStartBit + 2));
        return state;
    }

    public static int numOfElementsAtCol(long state, int col) {
        // col state start bit = 9 * col + 6
        return (int) ((state >>> 61 - ((ROW_SIZE + 3) * col + ROW_SIZE)) & 0b00000111);
    }

    private static long setSlotValue(long state, int slotIndex, SlotState slotState){
        state = clearSlot(state, slotIndex);
        return state | (((long) slotState.getValue()) << (63 - slotIndex));
    }

    private static long clearSlot(long state, int slotIndex) {
        return state & (~(1L << (63 - slotIndex)));
    }

    private static SlotState getSlotState(long state, int slotIndex, int col) {
        int colCount = numOfElementsAtCol(state, col);
        if (col * (ROW_SIZE + 3) + colCount >= slotIndex)
            return SlotState.EMPTY;

        return (state & (1L << (63 - slotIndex))) >>> (63 - slotIndex) == SlotState.AGENT.getValue()
                ? SlotState.AGENT
                : SlotState.USER;
    }

    private static int getEmptySlotsCount(long state) {
        int count = 0;
        for (int col = 0; col < COL_SIZE; col++)
            count += ROW_SIZE - numOfElementsAtCol(state, col);

        return count;
    }

    public List<Long> getStateChildren(long state, SlotState slotState) {
        if (slotState == SlotState.EMPTY)
            throw new IllegalArgumentException();

        ArrayList<Long> children = new ArrayList<>();
        for (int col = 0; col < COL_SIZE; col++) {
            if (numOfElementsAtCol(state, col) < ROW_SIZE) {
                children.add(playAtCol(state, col, slotState));
            }
        }

        return children;
    }

    public List<SlotIndex> getAgentSlots(long state) {
        return getPlayerSlots(state, SlotState.AGENT);
    }

    public List<SlotIndex> getUserSlots(long state) {
        return getPlayerSlots(state, SlotState.USER);
    }

    private List<SlotIndex> getPlayerSlots(long state, SlotState player) {
        ArrayList<SlotIndex> slots = new ArrayList<>();
        for (int col = 0, slotIndex; col < COL_SIZE; col++) {
            slotIndex = (ROW_SIZE + 3) * col;
            for (int row = 0; row < ROW_SIZE; row++) {
                if (getSlotState(state, slotIndex + row, col) == player) {
                    slots.add(new SlotIndex((byte) row, (byte) col));
                }
            }
        }

        return slots;
    }

    public static byte getRowSize() {
        return ROW_SIZE;
    }

    public static byte getColSize() {
        return COL_SIZE;
    }
}

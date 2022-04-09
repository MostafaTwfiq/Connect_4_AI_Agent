package logic;

import java.util.ArrayList;
import java.util.List;

public class StateOperations {

    private static final byte ROW_SIZE = 6;
    private static final byte COL_SIZE = 7;

    public static long playAtCol(long state, int col, SlotState slotState) {
        int colCount = numOfElementsAtCol(state, col);
        int colStateStartBit = (ROW_SIZE + 3) * col + ROW_SIZE;
        int slotIndex = (ROW_SIZE + 3) * col + colCount;
        state = setSlotValue(state, slotIndex, slotState);
        state += (1L << (63 - colStateStartBit - 2));
        return state;
    }

    public static int numOfElementsAtCol(long state, int col) {
        // col state start bit = 9 * col + 6
        return (int) ((state >>> 61 - ((ROW_SIZE + 3) * col + ROW_SIZE)) & 0b00000111);
    }

    public static long setSlotValue(long state, int slotIndex, SlotState slotState){
        state = clearSlot(state, slotIndex);
        return state | (((long) slotState.getValue()) << (63 - slotIndex));
    }

    private static long clearSlot(long state, int slotIndex) {
        return state & (~(1L << (63 - slotIndex)));
    }


    public static SlotState getSlotState(long state, int row, int col) {
        int slotIndex = (ROW_SIZE + 3) * col + row;
        int colCount = numOfElementsAtCol(state, col);
        if (slotIndex >= col * (ROW_SIZE + 3) + colCount)
            return SlotState.EMPTY;

        return (state & (1L << (63 - slotIndex))) >>> (63 - slotIndex) == SlotState.AGENT.getValue()
                ? SlotState.AGENT
                : SlotState.USER;
    }

    public static int getEmptySlotsCount(long state) {
        int count = 0;
        for (int col = 0; col < COL_SIZE; col++)
            count += ROW_SIZE - numOfElementsAtCol(state, col);

        return count;
    }

    public static List<Long> getStateChildren(long state, SlotState slotState) {
        if (slotState == SlotState.EMPTY)
            throw new IllegalArgumentException();

        ArrayList<Long> children = new ArrayList<>();
        int[] userOrder = new int[]{0, 6, 1, 5, 2, 4, 3};
        int[] agentOrder = new int[]{3, 2, 4, 1, 5, 0, 6};
        for (int col : slotState == SlotState.AGENT ? agentOrder : userOrder) {
            if (numOfElementsAtCol(state, col) < ROW_SIZE) {
                children.add(playAtCol(state, col, slotState));
            }
        }

        return children;
    }

    public static List<SlotIndex> getAgentSlots(long state) {
        return getPlayerSlots(state, SlotState.AGENT);
    }

    public static List<SlotIndex> getUserSlots(long state) {
        return getPlayerSlots(state, SlotState.USER);
    }

    private static List<SlotIndex> getPlayerSlots(long state, SlotState player) {
        ArrayList<SlotIndex> slots = new ArrayList<>();
        for (int col = 0; col < COL_SIZE; col++) {
            for (int row = 0; row < ROW_SIZE; row++) {
                if (getSlotState(state, row, col) == player) {
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

    public static void printState(long state) {
        for (int row = ROW_SIZE - 1; row >= 0; row--) {
            for (int col = 0; col < COL_SIZE; col++) {
                SlotState slotState = getSlotState(state, row, col);
                if (slotState == SlotState.EMPTY) {
                    System.out.print("#\t");
                } else if (slotState == SlotState.AGENT) {
                    System.out.print("X\t");
                } else if (slotState == SlotState.USER) {
                    System.out.print("O\t");
                } else {
                    System.out.print("R\t");
                }
            }
            System.out.println();
        }
    }
}

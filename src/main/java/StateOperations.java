public class StateOperations {
    public static byte[] setSlotValue(byte[] state, int row, int col, SlotState slotState){
        int num = row * 7 + col;
        int index = getSlotIndex(num);
        int slot_num = num % 4;
        var value = slotState == SlotState.AGENT ? 0b10 : 0b01;
        var shift = slot_num == 0 ? 0 : (8-2*slot_num);
        state[index] |= value << shift;
        return  state;
    }

    public static SlotState getSlotValue(byte[] state,  int row, int col){
        int num = row * 7 + col;
        var index = getSlotIndex(num);
        byte mask[] = {3, -64, 48, 12};
        int slot_num = num % 4;
        var b_arr = state[index];
        var shift = slot_num == 0 ? 0 : (8-2*slot_num);
        var value = state[index] & mask[slot_num] >> shift;
        return  SlotState.values()[value];
    }

    public static int getEmptySlotCount(byte[] state, int board_row, int board_col){
        int count = 0;
        for (int i = 1; i <= board_row; i++) {
            for (int j = 1; j <= board_col; j++) {
                if (getSlotValue(state, i, j) == SlotState.EMPTY)
                    count++;
            }
        }
        return  count;
    }

    private static int getSlotIndex(int num){
        int index = (int) Math.floor(num / 4);
        int slot_num = num % 4 ;
        if (slot_num == 0 && num != 0)
            index = (int) Math.floor((num-1) / 4);
        return index;
    }

}

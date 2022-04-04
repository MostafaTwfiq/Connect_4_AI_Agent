

public class StateOperations {

    public static byte[] setSlotValue(byte[] state, int num, SlotState slotState){
        int index = getSlotIndex(num);
        int slot_num = num % 4;
        var value = slotState == SlotState.AGENT ? 0b10 : 0b01;
        var shift = slot_num == 0 ? 0 : (8-2*slot_num);
        state[index] |= value << shift;
        return  state;
    }

    public static SlotState getSlotValue(byte[] state, int num){
        var index = getSlotIndex(num);
        var b_arr = state[index];
        return  SlotState.EMPTY;
    }

    public static int getEmptySlotCount(byte[] state, int num){
        return  1;
    }

    private static int getSlotIndex(int num){
        int index = (int) Math.floor(num / 4);
        int slot_num = num % 4 ;
        if (slot_num == 0 && num != 0)
            index = (int) Math.floor((num-1) / 4);
        return index;
    }

}

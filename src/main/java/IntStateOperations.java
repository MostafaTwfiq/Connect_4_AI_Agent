public class IntStateOperations {

    // int1
    // col1      col2       col3       col4
    // 100000010 101000011 000000000  00000
    // 31---23    22--14    13--5     4--0
    // int 2
    // col5      col6       col7       col4
    // 100000010 101000011 000000000  00000
    // 31---23    22--14    13---5     4--0
    public static int[] setSlotValue(int statep1, int statep2, int col, SlotState slotState){
        if (col == 4)
            return setSlotColFour(statep1, statep2, slotState);
        int arr[] = {statep1, statep2};
        int from = col < 4 ? (3-col) * 9 + 5: (7-col) * 9 + 5;
        int to = from + 9;
        int bitVal = slotState == SlotState.USER ? 0 : 1;
        int colValue = col < 4 ? bitRange(statep1, from, to) : bitRange(statep2, from, to);
        int countCol = getColumnCount(colValue);
        if (colValue == 6)
            return arr;
        countCol++;
        int ind = col < 4 ? 0 : 1;
        arr[ind] = Integer.rotateRight(arr[ind], from) | countCol;
        arr[ind] = Integer.rotateRight(arr[ind], 3) | bitVal;
        arr[ind] = Integer.rotateRight(arr[ind], 6);
        arr[ind] = Integer.rotateRight(arr[ind],31-to);
        return arr;
    }

    //TODO: Implement The Forth Col. Set
    private static  int[] setSlotColFour(int statep1, int statep2, SlotState slotState){
        int arr[] = {statep1, statep2};
        return  arr;
    }
    public static SlotState getSlotValue(int statep1, int statep2, int col){

        return SlotState.AGENT;
    }

    //TODO: Implement Empty Slot Count
    public static int getEmptySlotCount(byte[] state, int board_row, int board_col){
        int count = 0;
        return  count;
    }

    private static int bitRange(int value, int from, int to) {
        int waste = 31 - to;
        return ((value << waste) >> (waste + from)) & 0x000001FF;
    }

    private static int getColumnCount(int value) {
        int from = 0;
        int to = 2;
        int waste = 31 - to;
        return ((value << waste) >> (waste + from)) & 0x00000007;
    }
}

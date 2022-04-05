public class main {

    public static void main(String[] args) {
        int a = -1581711336;
        int b = 10;
        // 100000010 101000011 000000000  00000
        // 31---23    22-----14    13-------5   4--0
        var res = setSlotValue(a, b, 3, 1);
        System.out.println(res[0]);
    }

    public static int bitRange(int value, int from, int to) {
        int waste = 31 - to;
        return (value << waste) >> (waste + from);
    }

    public static int getColumnCount(int value) {
        int from = 0;
        int to = 2;
        int waste = 31 - to;
        return ((value << waste) >> (waste + from)) & 0x00000007;
    }
    public static int[] setSlotValue(int statep1, int statep2, int col, int slotState){
        if (col == 4)
            return setSlotColFour(statep1, statep2, slotState);
        int arr[] = {statep1, statep2};
        int from = col < 4 ? (3-col) * 9 + 5: (7-col) * 9 + 5;
        int to = from + 9 - 1;
        int bitVal = slotState ;
        int colValue = col < 4 ? bitRange(statep1, from, to) : bitRange(statep2, from, to);
        int countCol = getColumnCount(colValue);
        if (colValue == 6)
            return arr;
        countCol++;
        int ind = col < 4 ? 0 : 1;
        arr[ind] = Integer.rotateRight(arr[ind], from) | countCol;
        arr[ind] = Integer.rotateRight(arr[ind], 3) ;
        arr[ind] = Integer.rotateRight(arr[ind], 6 - countCol)| bitVal;
        arr[ind] = Integer.rotateRight(arr[ind],31- to + 1);
        return arr;
    }
    private static  int[] setSlotColFour(int statep1, int statep2, int slotState){
        int arr[] = {statep1, statep2};
        return  arr;
    }

}
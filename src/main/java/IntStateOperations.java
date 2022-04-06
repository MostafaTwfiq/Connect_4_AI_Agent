public class IntStateOperations {

    public static int[] playAtCol(int statep1, int statep2, int col, SlotState slotState){
        int arr[] = {statep1, statep2};
        int player = SlotState.AGENT == slotState?1:0;
        int nColStartbit = col * 9 + 6;
        int colCont = numOfColCount(statep1, statep2, col);
        int bitPos = getBitStatePos(statep1, statep2, col);
        int ind = (col > 3 || (col == 3 && colCont == 5))? 1 : 0;
        int posCount = nColStartbit < 32? 31-nColStartbit-2: 63-nColStartbit-2;
        arr[ind] |= ((makeBit(bitPos)) * player);
        arr[ind] += makeBit(posCount);
        return arr;
    }

    private static int getBitStatePos(int statep1, int statep2, int col){
        int row = numOfColCount(statep1, statep2, col);
        int numOfbits = col * 9 + row;
        return  numOfbits < 31? 31 - numOfbits : 63 - numOfbits;
    }

    public static int numOfColCount(int statep1, int statep2, int col){
        int row = 0;
        int nColStartbit = col * 9 + 6;
        if (nColStartbit < 32){
            row = Integer.rotateRight(statep1,31-nColStartbit-2) & 7;
        }else {
            row = Integer.rotateRight(statep2, 63-nColStartbit-2) & 7;
        }
        return row;
    }


    public static int[] clear_slot(int statep1, int statep2, int col){
        int arr[] = {statep1, statep2};

        return arr;
    }

    private static int makeBit(int pow){
        return (int) Math.pow(2, pow);
    }

}

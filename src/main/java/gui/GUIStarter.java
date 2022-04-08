package gui;
import logic.*;

import java.util.List;

public class GUIStarter {

    public static long drawState() {
        long state = 0L;
        int[] arr = new int[]{0, 5, 6};
        int[] arr2 = new int[]{2, 3, 4};
        for (int i : arr) {
            state = StateOperations.playAtCol(state, i, SlotState.USER);
            state = StateOperations.playAtCol(state, i, SlotState.USER);
            state = StateOperations.playAtCol(state, i, SlotState.USER);
            state = StateOperations.playAtCol(state, i, SlotState.USER);
            state = StateOperations.playAtCol(state, i, SlotState.USER);
            state = StateOperations.playAtCol(state, i, SlotState.USER);
        }

        for (int i : arr2) {
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
        }

        state = StateOperations.playAtCol(state, 1, SlotState.USER);
        state = StateOperations.playAtCol(state, 1, SlotState.AGENT);
        state = StateOperations.playAtCol(state, 1, SlotState.USER);
        state = StateOperations.playAtCol(state, 1, SlotState.AGENT);
        state = StateOperations.playAtCol(state, 1, SlotState.USER);
        state = StateOperations.playAtCol(state, 1, SlotState.AGENT);

        return state;
    }

    public static long worstConnection() {
        long state = 0;
        int[] arr = new int[]{0, 2, 3, 5};
        int[] arr2 = new int[]{1, 4, 6};
        for (int i : arr) {
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.USER);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.USER);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.USER);
        }

        for (int i : arr2) {
            state = StateOperations.playAtCol(state, i, SlotState.USER);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.USER);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.USER);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
        }

        return state;
    }

    public static void main(final String[] args) {
        long state = 0L;
        /*for (int i = 0; i <= 2; i++) {
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);
            state = StateOperations.playAtCol(state, i, SlotState.AGENT);

            state = StateOperations.playAtCol(state, 6 - i, SlotState.USER);
            state = StateOperations.playAtCol(state, 6 - i, SlotState.USER);
            state = StateOperations.playAtCol(state, 6 - i, SlotState.USER);
            state = StateOperations.playAtCol(state, 6 - i, SlotState.USER);
            state = StateOperations.playAtCol(state, 6 - i, SlotState.USER);
            state = StateOperations.playAtCol(state, 6 - i, SlotState.USER);
        }

        state = StateOperations.playAtCol(state, 3, SlotState.AGENT);
        state = StateOperations.playAtCol(state, 3, SlotState.USER);
        state = StateOperations.playAtCol(state, 3, SlotState.AGENT);
        state = StateOperations.playAtCol(state, 3, SlotState.USER);
        state = StateOperations.playAtCol(state, 3, SlotState.AGENT);
        state = StateOperations.playAtCol(state, 3, SlotState.USER);*/

        /*for (int row = 5, col = 0; row >= 0 && col < 6; row--, col++) {
            for (int i = row; i >= 0; i--)
                state = StateOperations.playAtCol(state, col, SlotState.AGENT);
            for (int i = row; i < 6; i++)
                state = StateOperations.playAtCol(state, col, SlotState.USER);
        }

        state = StateOperations.playAtCol(state, 6, SlotState.USER);
        state = StateOperations.playAtCol(state, 6, SlotState.USER);
        state = StateOperations.playAtCol(state, 6, SlotState.USER);
        state = StateOperations.playAtCol(state, 6, SlotState.USER);
        state = StateOperations.playAtCol(state, 6, SlotState.USER);
        state = StateOperations.playAtCol(state, 6, SlotState.USER);*/

        //state = drawState();
        state = worstConnection();

        List<SlotIndex> slots = StateOperations.getUserSlots(state);
//        System.out.println(Heuristic.calculatePlayerActualScore(state, SlotState.AGENT));
//        System.out.println(Heuristic.calculatePlayerCentering(slots));
//        System.out.println(Heuristic.calculatePlayerElementsConnection(slots));

        StateOperations.printState(state);
        //Main.main(args);
    }
}

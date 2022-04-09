package logic;

import com.sun.javafx.geom.Point2D;

import java.util.List;

public class Heuristic {

    //(x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    public static double map(double score, double inMin, double inMax, double outMin, double outMax) {
        return (score - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }

    public static double getBoardFullRatio(long state) {
        return (StateOperations.getRowSize() * StateOperations.getColSize() - StateOperations.getEmptySlotsCount(state)) * 1.0 / (StateOperations.getRowSize() * StateOperations.getColSize());
    }

    public static float getStateScore(long state) {
        // assuming max score for centring is 58
        // assuming max score for connection is 810
        // assuming max actual score is 18
        // assuming max total score = 69 + 35 + 19 = 123
        // scale from 0 to 100

        double centeringRatio = 0.33;
        double connectionRatio = 0.33;
        double actualScoreRatio = 0.33;
        double boardFullRatio = getBoardFullRatio(state);
        if (boardFullRatio > 0.33) {
            double newRatio = boardFullRatio * 1.3 > 1 ? 1 : boardFullRatio * 1.3;
            centeringRatio = (1 - newRatio) / 2;
            connectionRatio = (1 - newRatio) / 2;
            actualScoreRatio = newRatio;
        }
        List<SlotIndex> agentSlots = StateOperations.getAgentSlots(state);
        List<SlotIndex> userSlots = StateOperations.getUserSlots(state);

        double agentConnectionScore = 810 - calculatePlayerElementsConnection(agentSlots);
        agentConnectionScore = agentConnectionScore < 0 ? 810 : agentConnectionScore;
        double userConnectionScore = 810 - calculatePlayerElementsConnection(userSlots);
        userConnectionScore = userConnectionScore < 0 ? 810 : userConnectionScore;
        double agentCenteringScore = 58 - calculatePlayerCentering(agentSlots);
        agentCenteringScore = agentCenteringScore < 0 ? 58 : agentCenteringScore;
        double userCenteringScore = 58 - calculatePlayerCentering(userSlots);
        userCenteringScore = userCenteringScore < 0 ? 58 : userCenteringScore;
        double agentActualScore = calculatePlayerActualScore(state, SlotState.AGENT);
        double userActualScore = calculatePlayerActualScore(state, SlotState.USER);
        agentConnectionScore = connectionRatio * map(agentConnectionScore, 0, 810, 0, 100);
        userConnectionScore = connectionRatio * map(userConnectionScore, 0, 810, 0, 100);
        agentCenteringScore = centeringRatio * map(agentCenteringScore, 0, 58, 0, 100);
        userCenteringScore = centeringRatio * map(userCenteringScore, 0, 58, 0, 100);
        agentActualScore = actualScoreRatio * map(agentActualScore, 0, 18, 0, 100);
        userActualScore = actualScoreRatio * map(userActualScore, 0, 18, 0, 100);

        double stateScore = (agentActualScore + agentCenteringScore + agentConnectionScore) - (userActualScore + userCenteringScore + userConnectionScore);
        //stateScore = stateScore < 0 ? Math.floor(stateScore) : Math.ceil(stateScore);
        return (float) stateScore;
    }

    /**
     * This function will calculate a score that will represent if the player elements is stacked or not.
     * Note: If the score was smaller that will mean the elements are more stacked.
     * @param slots The player slots indexes
     * @return It will return the score of the current player state
     */

    private static int calculatePlayerElementsConnection(List<SlotIndex> slots) {
        int score = 0;
        SlotIndex slot1, slot2;
        for (int i = 0; i < slots.size(); i++) {
            slot1 = slots.get(i);
            for (int j = i + 1; j < slots.size(); j++) {
                slot2 = slots.get(j);
                score += Math.floor(calculateDistanceBetweenPoints(slot1.getRow(), slot1.getCol(), slot2.getRow(), slot2.getCol()));

            }
        }

        return score;
    }

    private static double calculateDistanceBetweenPoints(
            double x1,
            double y1,
            double x2,
            double y2) {
//        System.out.println(x1 + "-" + y1);
//        System.out.println(x2 + "-" + y2);
//        System.out.println("result: " + Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)));
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    /** This function will calculate a score that will represent if the player elements is centered or not.
     * Note: If the score was smaller that will mean the elements are more centered.
     * @param slots The player slots indexes
     * @return It will return the score of the current player state
     */

    private static int calculatePlayerCentering(List<SlotIndex> slots) {
        int score = 0;
        int centerRow = StateOperations.getRowSize() / 2 - 1;
        int centerCol = StateOperations.getColSize() / 2;
        for (int i = 0; i < slots.size(); i++) {

            score += Math.abs(slots.get(i).getRow() - centerRow) + Math.abs(slots.get(i).getCol() - centerCol);
        }

        return score;
    }

    public static int calculatePlayerActualScore(long state, SlotState player) {
        int score = calculateVerticalScore(state, player);
        score += calculateHorizontalScore(state, player);
        score += calculateDiagonalScore(state, player);
        return score;
    }

    private static int calculateVerticalScore(long state, SlotState player) {
        int score = 0;
        for (int row = 0, count; row < StateOperations.getRowSize(); row++) {
            count = 0;
            for (int col = 0; col < StateOperations.getColSize(); col++) {
                if (StateOperations.getSlotState(state, row, col) == player) {
                    count++;
                } else {
                    score += count > 3 ? count - 3 : 0;
                    count = 0;
                }
            }

            if (count > 3)
                score += count - 3;

        }
        return score;
    }

    private static int calculateHorizontalScore(long state, SlotState player) {
        int score = 0;
        for (int col = 0, count; col < StateOperations.getColSize(); col++) {
            count = 0;
            for (int row = 0; row < StateOperations.getRowSize(); row++) {
                if (StateOperations.getSlotState(state, row, col) == player) {
                    count++;
                } else {
                    score += count > 3 ? count - 3 : 0;
                    count = 0;
                }
            }

            if (count > 3)
                score += count - 3;

        }
        return score;
    }

    private static int calculateDiagonalScore(long state, SlotState player) {
        int score = 0,
                rowMax = StateOperations.getRowSize(),
                colMax = StateOperations.getColSize(),
                currRow, currCol,
                count = 0;

        // bottom left to top right (upper diagonal)
        for (int rowStart = 0; rowStart <= rowMax - 4; rowStart++) {
            count = 0;
            for (currRow = rowStart, currCol = 0; currRow < rowMax && currCol < colMax; currRow++, currCol++) {
                if (StateOperations.getSlotState(state, currRow, currCol) == player) {
                    count++;
                } else {
                    score += count > 3 ? count - 3 : 0;
                    count = 0;
                }
            }

            if (count > 3)
                score += count - 3;

        }

        // bottom left to top right (lower diagonal)
        for (int colStart = 1; colStart <= colMax - 4; colStart++) {
            count = 0;
            for (currRow = 0, currCol = colStart; currRow < rowMax && currCol < colMax; currRow++, currCol++ ) {
                if (StateOperations.getSlotState(state, currRow, currCol) == player) {
                    count++;
                } else {
                    score += count > 3 ? count - 3 : 0;
                    count = 0;
                }
            }

            if (count > 3)
                score += count - 3;
        }

        //-----------------------------------------------------------------
        // top left to bottom right (lower diagonal)
        for (int rowStart = rowMax - 1; rowStart > 2; rowStart--) {
            count = 0;
            for (currRow = rowStart, currCol = 0; currRow >= 0 && currCol < colMax; currRow--, currCol++) {
                if (StateOperations.getSlotState(state, currRow, currCol) == player) {
                    count++;
                } else {
                    score += count > 3 ? count - 3 : 0;
                    count = 0;
                }
            }

            if (count > 3)
                score += count - 3;
        }

        // top left to bottom right (upper diagonal)
        for (int colStart = 1; colStart <= colMax - 4; colStart++) {
            count = 0;
            for (currRow = rowMax - 1, currCol = colStart; currRow >= 0 && currCol < colMax; currRow--, currCol++) {
                if (StateOperations.getSlotState(state, currRow, currCol) == player) {
                    count++;
                } else {
                    score += count > 3 ? count - 3 : 0;
                    count = 0;
                }
            }

            if (count > 3)
                score += count - 3;
        }


        return score;
    }

}

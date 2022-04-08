package logic;

public class Heuristic {

    private static final  int SCORE_4 = 15 ;
    private static final int SCORE_3 = 5;
    private static final int SCORE_2 = 2 ;
    private static final int SCORE_1 = 1 ;
    private static final int  max_slots=4;
    private static int agent_score=0;
    private static int user_score=0;

    public static int getAgent_score(long board) {
        scores(board);
        return agent_score;
    }

    public static int getUser_score(long board) {
        scores(board);
        return user_score;
    }

    public static byte  score_evaluate (long board){
        int score_sum=0;

        // horizontal group
        for(int i=0 ; i<StateOperations.getRowSize() ; i++)
            for(int j=0 ; j<StateOperations.getColSize()-3 ; j++)
                score_sum+=horizontal_score(board,i,j);

        // vertical group
        for(int i=0 ; i<StateOperations.getColSize() ; i++)
            for(int j=0 ; j<StateOperations.getRowSize()-3 ; j++)
                score_sum+=vertical_score(board,i,j);

        // diagonal group
        for(int i=0; i<StateOperations.getRowSize()-3 ; i++){
            for(int j=0 ; j<StateOperations.getColSize()-3 ; j++){
                score_sum+=Diagonal_raising(board , i , j);
                score_sum+=Diagonal_falling(board , i , j+max_slots-1);
            }
        }
        return  (byte) score_sum;
    }

    private static int horizontal_score(long board , int R, int C){
        int agent_slots=0;
        int user_slots=0;
        int empty_slots=0;
        for(int i=C;i<C+max_slots;i++){
            if(StateOperations.getSlotState(board,R,i)==SlotState.AGENT)
                agent_slots++;
            else if(StateOperations.getSlotState(board,R,i)==SlotState.AGENT)
                user_slots++;
            else empty_slots++;
        }
        return  calculate_score(agent_slots,user_slots,empty_slots);

    }

    private static int vertical_score(long board , int R, int C){
        int agent_slots=0;
        int user_slots=0;
        int empty_slots=0;
        for(int i=R ; i<R+max_slots ; i++){
            if(StateOperations.getSlotState(board,i,C)==SlotState.AGENT)
                agent_slots++;
            else if(StateOperations.getSlotState(board,i,C)==SlotState.AGENT)
                user_slots++;
            else empty_slots++;
        }
        return  calculate_score(agent_slots,user_slots,empty_slots);

    }

    private static int Diagonal_raising(long board ,int R, int C){
        int agent_slots=0;
        int user_slots=0;
        int empty_slots=0;

        for(int i=R ; i<R+max_slots ; i++)
        {
            if(StateOperations.getSlotState(board,i,C)==SlotState.USER)
                user_slots++;
            else if(StateOperations.getSlotState(board,i,C)==SlotState.AGENT)
                agent_slots++;
            else empty_slots++;
            C++;
        }
        return calculate_score(agent_slots,user_slots,empty_slots);
    }

    private static int Diagonal_falling(long board ,int R, int C){
        int agent_slots=0;
        int user_slots=0;
        int empty_slots=0;

        for(int i=R ; i<R+max_slots ; i++)
        {
            if(StateOperations.getSlotState(board,i,C)==SlotState.USER)
                user_slots++;
            else if(StateOperations.getSlotState(board,i,C)==SlotState.AGENT)
                agent_slots++;
            else empty_slots++;
            C--;
        }
        return calculate_score(agent_slots,user_slots,empty_slots);
    }


    private static int calculate_score(int agent_slots,int user_slots,int empty_slots)
    {
        if(agent_slots==4)
            return SCORE_4;
        else if(agent_slots==3 && empty_slots==1)
            return  SCORE_3;
        else if(agent_slots==2 && empty_slots==2)
            return SCORE_2;
        else if(agent_slots==1 && empty_slots==3)
            return  SCORE_1;
        else if(user_slots==4)
            return -1*SCORE_4;
        else if(user_slots==3 && empty_slots==1)
            return (SCORE_3*-2);
        else if(user_slots==2 && empty_slots==2)
            return -(SCORE_2/2);
        else if(user_slots==1 && empty_slots==3)
            return -1*SCORE_1;
        else return 0;
    }


    public static void scores(long board){

        agent_score=0;
        user_score=0;
        for(int i=0 ; i<StateOperations.getRowSize() ; i++){
            for(int j=0 ; j<StateOperations.getColSize()-3 ; j++){
                if((StateOperations.getSlotState(board,i,j)==SlotState.AGENT) && (StateOperations.getSlotState(board,i,j+1)==SlotState.AGENT) && (StateOperations.getSlotState(board, i,j+2)==SlotState.AGENT) && (StateOperations.getSlotState(board,i,j+3)==SlotState.AGENT))
                    agent_score++;
                else if((StateOperations.getSlotState(board,i,j)==SlotState.USER) && (StateOperations.getSlotState(board,i,j+1)==SlotState.USER) && (StateOperations.getSlotState(board,i,j+2)==SlotState.USER) && (StateOperations.getSlotState(board,i,j+3)==SlotState.USER))
                    user_score++;
            }
        }


        for(int i=0 ; i<StateOperations.getColSize() ; i++) {
            for(int j=0 ; j<StateOperations.getRowSize()-3 ; j++){
                if((StateOperations.getSlotState(board,j,i)==SlotState.AGENT) && (StateOperations.getSlotState(board,j+1,i)==SlotState.AGENT) && (StateOperations.getSlotState(board,j+2,i)==SlotState.AGENT) && (StateOperations.getSlotState(board,j+3,i)==SlotState.AGENT))
                    agent_score++;
                else if((StateOperations.getSlotState(board,j,i)==SlotState.USER) && (StateOperations.getSlotState(board,j+1,i)==SlotState.USER) && (StateOperations.getSlotState(board,j+2,i)==SlotState.USER) && (StateOperations.getSlotState(board,j+3,i)==SlotState.USER))
                    user_score++;
            }
        }

        for(int i=0; i<StateOperations.getRowSize()-3 ; i++){
            for(int j=0 ; j<StateOperations.getColSize()-3 ; j++){
                if((StateOperations.getSlotState(board,i,j)== SlotState.AGENT) && (StateOperations.getSlotState(board,i+1,j+1)==SlotState.AGENT) && (StateOperations.getSlotState(board,i+2,j+2)==SlotState.AGENT) && (StateOperations.getSlotState(board,i+3,j+3)==SlotState.AGENT))
                    agent_score++;
                else if((StateOperations.getSlotState(board,i,j)==SlotState.USER) && (StateOperations.getSlotState(board,i+1,j+1)==SlotState.USER) && (StateOperations.getSlotState(board,i+2,j+2)==SlotState.USER) && (StateOperations.getSlotState(board,i+3,j+3)==SlotState.USER))
                    user_score++;

            }
        }
        for(int i=0; i<StateOperations.getRowSize()-3 ; i++){
            for(int j=max_slots-1 ; j<StateOperations.getColSize() ; j++){
                if((StateOperations.getSlotState(board,i,j)==SlotState.AGENT) && (StateOperations.getSlotState(board,i+1,j-1)==SlotState.AGENT) && (StateOperations.getSlotState(board,i+2,j-2)==SlotState.AGENT) && (StateOperations.getSlotState(board,i+3,j-3)==SlotState.AGENT))
                    agent_score++;
                else if((StateOperations.getSlotState(board,i,j)==SlotState.USER) && (StateOperations.getSlotState(board,i+1,j-1)==SlotState.USER) && (StateOperations.getSlotState(board,i+2,j-2)==SlotState.USER) && (StateOperations.getSlotState(board,i+3,j-3)==SlotState.USER))
                    user_score++;
            }
        }

    }


}



public class Heuristic {

    private static final  int SCORE_4 = 15 ;
    private static final int SCORE_3 = 5;
    private static final int SCORE_2 = 2 ;
    private static final int SCORE_1 = 1 ;
    private static final int  max_slots=4;

    public static int  score_evaluate (byte[] board){
        int score_sum=0;

        // horizontal group
        for(int i = 0; i< State.ROW_SIZE ; i++)
            for(int j = 0; j< State.COL_SIZE-3 ; j++)
                score_sum+=horizontal_score(board,i,j);

        // vertical group
        for(int i = 0; i< State.COL_SIZE ; i++)
            for(int j = 0; j< State.ROW_SIZE-3 ; j++)
                score_sum+=vertical_score(board,i,j);

        // diagonal group
        for(int i = 0; i< State.ROW_SIZE-3 ; i++){
            for(int j = 0; j< State.COL_SIZE-3 ; j++){
                score_sum+=Diagonal_raising(board , i , j);
                score_sum+=Diagonal_falling(board , i , State.COL_SIZE-j-1);
            }

        }
        return  score_sum;
    }

    private static int horizontal_score(byte[] board , int R, int C){
        int agent_slots=0;
        int user_slots=0;
        int empty_slots=0;
        for(int i=C;i<C+max_slots;i++){
            if(board[i+R]== State.agent_slot)
                agent_slots++;
            else if(board[R+C]== State.user_slot)
                user_slots++;
            else empty_slots++;
        }
        return  calculate_score(agent_slots,user_slots,empty_slots);

    }

    private static int vertical_score(byte[] board , int R, int C){
        int agent_slots=0;
        int user_slots=0;
        int empty_slots=0;
        for(int i=R ; i<R+max_slots ; i++){
            if(board[i+C]== State.agent_slot)
                agent_slots++;
            else if(board[R+C]== State.user_slot)
                user_slots++;
            else empty_slots++;
        }
        return  calculate_score(agent_slots,user_slots,empty_slots);

    }

    private static int Diagonal_raising(byte[] board ,int R, int C){
        int agent_slots=0;
        int user_slots=0;
        int empty_slots=0;

        for(int i=R ; i<R+max_slots ; i++)
        {
            if(board[i+C]== State.user_slot)
                user_slots++;
            else if(board[i+C]== State.agent_slot)
                agent_slots++;
            else empty_slots++;
            C++;
        }
        return calculate_score(agent_slots,user_slots,empty_slots);
    }

    private static int Diagonal_falling(byte[] board ,int R, int C){
        int agent_slots=0;
        int user_slots=0;
        int empty_slots=0;

        for(int i=R ; i<R+max_slots ; i++)
        {
            if(board[i+C]== State.user_slot)
                user_slots++;
            else if(board[i+C]== State.agent_slot)
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


















}

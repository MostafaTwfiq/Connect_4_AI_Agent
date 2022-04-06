import java.util.Vector;

public class IntState {


    public static Vector<Integer> getChildrenStates(int statep1, int statep2, SlotState player){
        Vector<Integer> children = new Vector<>();
        for (int i = 0; i < 6; i++) {
            int arr[] = IntStateOperations.playAtCol(statep1, statep2, i, player);
            if(arr[0] == statep1 && arr[1] == statep2)
                continue;
            else{
                children.add(arr[0]);
                children.add(arr[1]);
            }
        }

        return  children;
    }

    public static Vector<Integer> getAgentSolt(int statep1, int statep2){
        Vector<Integer> pos = new Vector<>();

        return  pos;
    }

    public static Vector<Integer> getPlayerSolt(int statep1, int statep2){
        Vector<Integer> pos = new Vector<>();

        return  pos;
    }
    public static float emptyRatio(int statep1, int statep2){
        return 0.1f;
    }
}

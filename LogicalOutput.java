import java.util.ArrayList;

public class LogicalOutput extends LogicalNode {
    private int state = 2;

    public void SetState(int state) {
        this.state = state;
    }

    public int getState() { return state; }

    protected boolean ComputeOutputInternal() {
        ArrayList<LogicalNode> inputs = GetInputs();
        boolean result = true;

        for (int idx = 0; idx < inputs.size() && result; idx++) {
            result = inputs.get(idx).ComputeOutput();
        }
        if(result) state = 1;
        else state = 0;
        return result;
    }
}
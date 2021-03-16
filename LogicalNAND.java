import java.util.ArrayList;

public class LogicalNAND extends LogicalNode {
    protected boolean ComputeOutputInternal() {
        ArrayList<LogicalNode> inputs = GetInputs();
        boolean result = true;

        for (int idx = 0; idx < inputs.size() && result; idx++) {
            result = result & inputs.get(idx).ComputeOutput();
        }
        return !result;
    }
}
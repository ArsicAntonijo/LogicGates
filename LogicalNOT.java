import java.util.ArrayList;

public class LogicalNOT extends LogicalNode {
    protected boolean ComputeOutputInternal() {
        ArrayList<LogicalNode> inputs = GetInputs();
        if (inputs.size() > 0) {
            return !inputs.get(0).ComputeOutput();
        }
        return false;
    }
}
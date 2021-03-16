import java.util.ArrayList;

public class LogicalOR extends LogicalNode {
    protected boolean ComputeOutputInternal() {
        ArrayList<LogicalNode> inputs = GetInputs();
        boolean result = false;
        for (int idx = 0; idx < inputs.size(); idx++)
        {
            result = inputs.get(idx).ComputeOutput();
            if (result)
                 // If we get one true, that is enough.
                 break;
            }
            return result;
        }
}
import java.util.ArrayList;

public class LogicalNode
{
    private ArrayList<LogicalNode> inputs = new ArrayList<>();
    private String name = "Not Set";
    private boolean st;


    public String ToString() {
        return "Node {0}" + name;
}

    public void Reset()
    {
        inputs.clear();
    }

    public void SetName(String name)
    {
        name = name;
    }

    protected ArrayList<LogicalNode> GetInputs()
    {
        return inputs;
    }

    public void AddInput(LogicalNode node)
    {
        inputs.add(node);
    }

    protected boolean ComputeOutputInternal() {
        return false;
}

    public boolean ComputeOutput() {
        // Console.WriteLine("Computing output on {0}.", _name);
        return ComputeOutputInternal();
    }

    public void SetState(boolean i) {
    }
    public boolean GetState(){
        return false;
    }
    public int getState(){
        return 2;
    }
    public void setState(int i){ }
    public void RemoveInput(LogicalNode node) {
        inputs.remove(node);
    }

}


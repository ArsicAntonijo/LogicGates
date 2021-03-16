public class LogicalInput extends LogicalNode {
    private boolean state = true;

    public void SetState(boolean state) {
        this.state = state;
    }

    public boolean GetState() { return state; }

    protected boolean ComputeOutputInternal() {
        return state;
    }
}
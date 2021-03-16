public class LogicalOutput extends LogicalNode {
    private int state = 2;

    public void SetState(int state) {
        this.state = state;
    }

    public int GetState() { return state; }

}
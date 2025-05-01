// State.java
public class State {
    private final String id;
    private boolean isFinalState;

    public State(String id) {
        this.id = id;
        this.isFinalState = (id.equals("q2"));
    }

    public String getId() {
        return id;
    }
    public boolean isFinalState() {
        return isFinalState;
    }
}
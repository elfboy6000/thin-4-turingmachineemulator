public class Transition {
    private final State currentState;
    private final char readSymbol;
    private final State nextState;
    private final char writeSymbol;
    private final Direction direction;

    public Transition(State currentState, char readSymbol, State nextState, char writeSymbol, Direction direction) {
        this.currentState = currentState;
        this.readSymbol = readSymbol;
        this.nextState = nextState;
        this.writeSymbol = writeSymbol;
        this.direction = direction;
    }

    public State getCurrentState()      { return currentState; }
    public char getReadSymbol()          { return readSymbol; }
    public State getNextState()            { return nextState; }
    public char getWriteSymbol()           { return writeSymbol; }
    public Direction getDirection()        { return direction; }

    @Override
    public String toString() {
        return String.format("δ(%s, %c) → (%s, %c, %s)%n",
                currentState.getId(), readSymbol, nextState.getId(), writeSymbol, direction);
    }
}
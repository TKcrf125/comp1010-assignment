package majorAssignmentTopic1.src.main.java.combat.domain;

/**
 * Recursive node for tracking unit comabt action history.
 */
public class TroopNode {
    private final Troop data;
    private final TroopNode next;

    public TroopNode(Troop data, TroopNode next) {
        this.data = data;
        this.next = next;
    }

    /** Returns a new history entry */
    public TroopNode add(Troop t) {
        return new TroopNode(t, this);
    }

    public Troop getData() { return data; }
    public TroopNode getNext() { return next; }
}

package combat.domain;

/**
 * Recursive node for tracking unit-action history.
 */
// NOTE: AI-generated—please rewrite description and logic comments.
public class TroopNode {
    private final Troop data;
    private final TroopNode next;

    public TroopNode(Troop data, TroopNode next) {
        this.data = data;
        this.next = next;
    }

    /** Returns a new history entry with this unit prepended. */
    public TroopNode add(Troop t) {
        return new TroopNode(t, this);
    }

    public Troop getData() { return data; }
    public TroopNode getNext() { return next; }
}

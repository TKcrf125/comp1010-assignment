package majorAssignmentTopic1.src.test.java.combat;

import combat.domain.Ability;
import combat.domain.Troop;
import combat.domain.TroopNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for TroopNode recursive structure.
 */
class TroopNodeTest {

    @Test
    void addToNullRootCreatesNewHead() {
        TroopNode root = new TroopNode(null, null);
        Troop t = new Troop("X", 10, 5, 2, 0.0, 1.0,
            new Ability("None","",0));
        TroopNode head = root.add(t);

        assertSame(t, head.getData(),
            "New head should contain the added troop");
        assertSame(root, head.getNext(),
            "Next of new head should be the old root");
    }

    @Test
    void chainAndTraverseMaintainsOrder() {
        Troop t1 = new Troop("A", 10, 5, 2, 0.0, 1.0,
            new Ability("None","",0));
        Troop t2 = new Troop("B", 15, 6, 3, 0.0, 1.0,
            new Ability("None","",0));
        TroopNode root = new TroopNode(null, null);
        TroopNode head = root.add(t1).add(t2);

        assertEquals(t2, head.getData());
        assertEquals(t1, head.getNext().getData());
    }
}

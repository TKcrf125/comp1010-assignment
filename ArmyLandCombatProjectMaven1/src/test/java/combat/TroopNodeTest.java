package combat;

import combat.domain.Troop;
import combat.domain.TroopNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for TroopNode recursive structure.
 */
// NOTE: AI-generated—please reword header.
class TroopNodeTest {

    @Test
    void addToNullRoot() {
        TroopNode root = new TroopNode(null, null);
        Troop t1 = new Troop("Alpha", 10, 5, 2);
        TroopNode newHead = root.add(t1);
        assertEquals(t1, newHead.getData(), "New head should hold the added troop");
        assertSame(root, newHead.getNext(), "Next should point to old root");
    }

    @Test
    void chainAndTraverse() {
        Troop t1 = new Troop("Alpha", 10, 5, 2);
        Troop t2 = new Troop("Bravo", 15, 6, 3);
        Troop t3 = new Troop("Charlie", 20, 7, 4);

        TroopNode root = new TroopNode(null, null);
        TroopNode head = root.add(t1).add(t2).add(t3);

        assertEquals(t3, head.getData());
        assertEquals(t2, head.getNext().getData());
        assertEquals(t1, head.getNext().getNext().getData());
        assertNull(head.getNext().getNext().getNext().getData());
    }
}

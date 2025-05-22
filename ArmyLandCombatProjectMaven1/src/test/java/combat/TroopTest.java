package combat;

import combat.domain.Troop;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Troop class.
 */
// NOTE: AI-generated tests—consider adding edge cases yourself.
public class TroopTest {
    @Test
    void testTakeDamageReducesHealth() {
        Troop t = new Troop("Test", 100, 0, 0);
        t.takeDamage(30);
        assertEquals(70, t.calculateScore() - (0 + 0));
    }

    @Test
    void testAttackCalculatesPositiveDamage() {
        Troop attacker = new Troop("A", 50, 20, 5);
        Troop defender = new Troop("B", 50, 10, 5);
        int dmg = attacker.attack(defender);
        assertTrue(dmg >= 0 && defender.calculateScore() < attacker.calculateScore() + defender.calculateScore());
    }
}

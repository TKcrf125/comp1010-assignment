package combat;

import combat.domain.Ability;
import combat.domain.Troop;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Ability class.
 */
class AbilityTest {

    @Test
    void abilityStartsReady() {
        Ability ab = new Ability("Test", "Does stuff", 2);
        assertTrue(ab.isReady(), "New abilities should be ready immediately");
    }

    @Test
    void useResetsCooldownAndTicksDown() {
        Ability ab = new Ability("Test", "Does stuff", 2);
        ab.use();
        assertFalse(ab.isReady(), "Ability should not be ready immediately after use");
        ab.tick();
        assertFalse(ab.isReady(), "After one tick, still cooling down");
        ab.tick();
        assertTrue(ab.isReady(), "After two ticks, cooldown should expire");
    }

    @Test
    void applyDealsDoubleBaseDamageAndReturnsMessage() throws Exception {
        // Set up a user and target with known stats
        Troop user = new Troop("U", 50, 10, 3, 0.0, 1.0,
            new Ability("Dummy", "desc", 1));
        Troop target = new Troop("T", 50, 5, 2, 0.0, 1.0,
            new Ability("None", "desc", 0));

        // New ability that doubles base damage
        Ability ab = new Ability("DoubleStrike", "2× damage", 1);

        // Reflect to inspect target health
        Field healthField = Troop.class.getDeclaredField("health");
        healthField.setAccessible(true);

        String message = ab.apply(user, target);
        int base = Math.max(0, user.getAttackPower() - target.getDefencePower());
        int expected = base * 2;
        assertTrue(message.contains("DoubleStrike"),
            "Message should include ability name");
        assertTrue(message.contains(String.valueOf(expected)),
            "Message should include damage amount");

        int newHealth = (int) healthField.get(target);
        assertEquals(50 - expected, newHealth,
            "Target health should be reduced by expected damage");
    }
}


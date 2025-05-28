package majorAssignmentTopic1.src.test.java.combat;

import combat.domain.Ability;
import combat.domain.Troop;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Troop class, including crits and abilities.
 */
class TroopTest {

    @Test
    void nonCritAttackReducesHealthCorrectly() throws Exception {
        Troop atk = new Troop("Atk", 50, 20, 5, 0.0, 2.0,
            new Ability("None", "desc", 0));
        Troop def = new Troop("Def", 50, 5, 5, 0.0, 2.0,
            new Ability("None", "desc", 0));

        int damage = atk.attack(def);
        assertEquals(15, damage, "Base damage should be attack–defence");

        Field healthField = Troop.class.getDeclaredField("health");
        healthField.setAccessible(true);
        assertEquals(35, (int) healthField.get(def),
            "Defender health should drop by the damage amount");
    }

    @Test
    void critAttackDoublesDamage() throws Exception {
        Troop atk = new Troop("Atk", 50, 20, 5, 1.0, 2.0,
            new Ability("None", "desc", 0));
        Troop def = new Troop("Def", 50, 5, 5, 0.0, 2.0,
            new Ability("None", "desc", 0));

        int damage = atk.attack(def);
        assertEquals((20 - 5) * 2, damage,
            "With critChance=1.0, damage should be doubled base");

        Field healthField = Troop.class.getDeclaredField("health");
        healthField.setAccessible(true);
        assertEquals(50 - damage, (int) healthField.get(def),
            "Defender health should reflect crit damage");
    }

    @Test
    void useAbilityHealsOrDamagesAccordingly() throws Exception {
        Ability heal = new Ability("Heal", "Restore HP", 1);
        Troop user = new Troop("Medic", 50, 0, 0, 0.0, 1.0, heal);
        Troop ally = new Troop("Ally", 30, 0, 0, 0.0, 1.0,
            new Ability("None", "desc", 0));

        // Set ally health low
        Field healthField = Troop.class.getDeclaredField("health");
        healthField.setAccessible(true);
        healthField.set(ally, 10);

        String result = user.useAbility(ally);
        assertTrue(result.contains("Heal"),
            "Result message should mention the ability name");
        int newHealth = (int) healthField.get(ally);
        assertTrue(newHealth > 10,
            "Ally health should increase after Heal ability");
    }

    @Test
    void tickAbilityDecrementsCooldown() {
        Ability ab = new Ability("Test", "desc", 2);
        Troop t = new Troop("X", 50, 0, 0, 0.0, 1.0, ab);
        ab.use();
        t.tickAbility();
        assertFalse(ab.isReady(), "Cooldown should still be >0 after one tick");
        t.tickAbility();
        assertTrue(ab.isReady(), "Cooldown should expire after two ticks");
    }
}

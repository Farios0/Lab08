package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {

    public static final int NUMBER_OF_RULES = 13;
    private final DeathNote libro = new DeathNoteImpl();

    private void testRulesExceptions(final int ruleNumber) {
        assertThrows(IllegalArgumentException.class, 
            new Executable() {
                @Override
                public void execute() throws Throwable {
                    libro.getRule(ruleNumber);
                }
            }
        );
    }

    private String catchRulesExceptionMessage(final int ruleNumber) {
        try {
            libro.getRule(ruleNumber);
        } catch (final IllegalArgumentException e) {
           return e.getMessage();
        }
        return null;
    }

    @Test
    void testGetRules() {
        //CHECKSTYLE: MagicNumber OFF
        //these are real test cases that a user could encounter
        testRulesExceptions(0);
        testRulesExceptions(-8);
        testRulesExceptions(1880);
        //CHECKSTYLE: MagicNumber ON
        assertNotEquals(null, catchRulesExceptionMessage(0));
        assertNotEquals("", catchRulesExceptionMessage(0));
        assertNotEquals(" ", catchRulesExceptionMessage(0));
        for (int i = 1; i <= NUMBER_OF_RULES; i++) {
            assertNotEquals(null, libro.getRule(i));
            assertNotEquals(" ", libro.getRule(i));
        }
    }

    @Test
    void testWriteName() {
        final String casualPerson = "alfredo";
        assertFalse(libro.containsName(casualPerson));
        libro.writeName(casualPerson);
        assertTrue(libro.containsName(casualPerson));
        assertFalse(libro.containsName("armando"));
        assertFalse(libro.containsName(""));
    }

    @Test
    void testWriteCause() throws InterruptedException {
        assertThrows(IllegalStateException.class, 
            new Executable() {
                @Override
                public void execute() {
                    libro.writeDeathCause("burnt alive");
                }
            }
        );
        final String person1 = "armando";
        final String person2 = "giuseppe";
        final String cause = "karting accident";
        libro.writeName(person1);
        assertEquals(libro.getDeathCause(person1), "Heart Attack");
        assertTrue(libro.writeName(person2, cause));
        assertEquals(cause, libro.getDeathCause(person2));
        Thread.sleep(100);
        libro.writeDeathCause("fell from the stairs");
        assertEquals(libro.getDeathCause(person2), cause);

    }

    @Test
    void testWriteDetails() throws InterruptedException {
        assertThrows(IllegalStateException.class, 
            new Executable() {
                @Override
                public void execute() {
                    libro.writeDetails("fell from the stairs");
                }
            }
        );
        final String person = "caio";
        libro.writeName(person);
        assertEquals("", libro.getDeathDetails(person));
        assertTrue(libro.writeDetails("run for too long"));
        assertEquals("run for too long", libro.getDeathDetails(person));
        libro.writeName("tolomeo");
        final long timeToSleep = 6100;
        Thread.sleep(timeToSleep);
        libro.writeDetails("drowned");
        assertEquals("", libro.getDeathDetails("tolomeo"));
    }
}

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
    private DeathNote libro = new DeathNoteImpl();

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
    private void testGetRules() {
        testRulesExceptions(0);
        testRulesExceptions(-8);
        testRulesExceptions(1880);
        assertNotEquals(null, catchRulesExceptionMessage(0));
        assertNotEquals("", catchRulesExceptionMessage(0));
        assertNotEquals(" ", catchRulesExceptionMessage(0));
        for (int i = 1; i <= NUMBER_OF_RULES; i++) {
            assertNotEquals(null, libro.getRule(i));
            assertNotEquals(" ", libro.getRule(i));
        }
    }

    @Test
    private void testWriteName() {
        final String casualPerson = "alfredo";
        assertFalse(libro.containsName(casualPerson));
        libro.writeName(casualPerson);
        assertTrue(libro.containsName(casualPerson));
        assertFalse(libro.containsName("armando"));
        assertFalse(libro.containsName(""));
    }

    @Test
    private void testWriteCause() throws InterruptedException {
        assertThrows(IllegalStateException.class, 
            new Executable() {
                @Override
                public void execute() {
                    libro.writeDeathCause("burnt alive");
                }
            }
        );
        libro.writeName("armando");
        assertEquals(libro.getDeathCause("armando"), "Heart Attack");
        assertTrue(libro.writeName("giuseppe", "karting accident"));
        assertEquals("karting accident", libro.getDeathCause("giuseppe"));
        Thread.sleep(100);
        libro.writeDeathCause("fell from the stairs");
        assertEquals(libro.getDeathCause("giuseppe"), "karting accident");

    }

    @Test
    private void testWriteDetails() throws InterruptedException {
        assertThrows(IllegalStateException.class, 
            new Executable() {
                @Override
                public void execute() {
                    libro.writeDetails("fell from the stairs");
                }
            }
        );
        libro.writeName("caio");
        assertEquals("", libro.getDeathDetails("caio"));
        assertTrue(libro.writeDetails("run for too long"));
        assertEquals("run for too long", libro.getDeathDetails("caio"));
        libro.writeName("tolomeo");
        Thread.sleep(6100);
        libro.writeDetails("drowned");
        assertEquals("", libro.getDeathDetails("tolomeo"));
    }
}

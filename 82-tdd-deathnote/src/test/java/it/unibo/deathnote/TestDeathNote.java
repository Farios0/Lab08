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
    DeathNote libro = new DeathNoteImpl();

    private void testRulesExceptions(int ruleNumber){
        assertThrows(IllegalArgumentException.class, 
            new Executable(){
                @Override
                public void execute() throws Throwable{
                    libro.getRule(ruleNumber);
                }
            }
        );
    }

    private String catchRulesExceptionMessage(int ruleNumber){
        try{
            libro.getRule(0);
        }catch (IllegalArgumentException e){
           return e.getMessage();
        }
        return null;
    }

    @Test
    public void testGetRules(){
        testRulesExceptions( 0);
        testRulesExceptions(-8);
        testRulesExceptions(1880);
        assertNotEquals(null, catchRulesExceptionMessage(0));
        assertNotEquals("", catchRulesExceptionMessage(0));
        assertNotEquals(" ", catchRulesExceptionMessage(0));
        for(int i = 1; i <= NUMBER_OF_RULES; i++){
            assertNotEquals(null, libro.getRule(i));
            assertNotEquals(" ", libro.getRule(i));
        }
    }
    
    @Test
    public void TestWriteName(){
        assertFalse(libro.containsName("alfredo"));
        libro.writeName("alfredo");
        assertTrue(libro.containsName("alfredo"));
        assertFalse(libro.containsName("armando"));
        assertFalse(libro.containsName(""));
    }

    @Test
    public void TestWriteCause() throws InterruptedException{
        assertThrows(IllegalStateException.class, 
            new Executable(){
                @Override
                public void execute(){
                    libro.writeDeathCause("fell from the stairs");
                }
            }
        );
        libro.writeName("armando");
        assertEquals(libro.getDeathCause("armando"),"Heart Attack");
        assertTrue(libro.writeName("giuseppe", "karting accident"));
        assertEquals("karting accident", libro.getDeathCause("giuseppe"));
        Thread.sleep(100);
        libro.writeDeathCause("fell from the stairs");
        assertEquals(libro.getDeathCause("giuseppe"), "karting accident");

    }

    @Test
    public void TestWriteDetails() throws InterruptedException {
        assertThrows(IllegalStateException.class, 
            new Executable(){
                @Override
                public void execute(){
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
        libro.writeDetails("fell from the stairs");        
        assertEquals("", libro.getDeathDetails("tolomeo"));
    }

}
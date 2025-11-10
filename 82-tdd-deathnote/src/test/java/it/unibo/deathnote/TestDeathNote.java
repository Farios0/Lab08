package it.unibo.deathnote;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.function.Executable;
import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;


class TestDeathNote {

    public static final int NUMBER_OF_RULES = 13;

    public static void testRulesExceptions(DeathNote libro, int ruleNumber){
        assertThrows(IllegalArgumentException.class, 
        new Executable(){
            @Override
            public void execute() throws Throwable{
                libro.getRule(ruleNumber);
            }
        }
        );
    }

    public static String catchRulesExceptionMessage(DeathNote libro, int ruleNumber){
        try{
            libro.getRule(0);
        }catch (IllegalArgumentException e){
           return e.getMessage();
        }
        return null;
    }

    public static void main(){
        DeathNote libro = new DeathNoteImpl();
        testRulesExceptions(libro, 0);
        testRulesExceptions(libro, -8);
        testRulesExceptions(libro, 1880);
        assertNotEquals(null, catchRulesExceptionMessage(libro, 0));
        assertNotEquals("", catchRulesExceptionMessage(libro, 0));
        assertNotEquals(" ", catchRulesExceptionMessage(libro, 0));
        for(int i = 1; i <= NUMBER_OF_RULES; i++){
            assertNotEquals(null, libro.getRule(i));
            assertNotEquals(" ", libro.getRule(i));
        }
        assertFalse(libro.containsName("alfredo"));
        libro.writeName("alfredo");
        assertTrue(libro.containsName("alfredo"));


        

    }
}
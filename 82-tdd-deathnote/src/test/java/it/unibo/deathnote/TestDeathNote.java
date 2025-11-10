package it.unibo.deathnote;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.function.Executable;

import org.junit.jupiter.api.Assertions.*;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;


class TestDeathNote {

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
    public static void main(){
        DeathNote libro = new DeathNoteImpl();
        testRulesExceptions(libro, 0);
        testRulesExceptions(libro, -8);
        testRulesExceptions(libro, 1880);
        
    }
}
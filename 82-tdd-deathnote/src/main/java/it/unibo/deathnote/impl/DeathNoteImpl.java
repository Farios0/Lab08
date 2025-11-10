package it.unibo.deathnote.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import javafx.util.Pair;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote{
    private String currentName;
    private long timeKeeper;
    private Map<String, List<String>> book = new HashMap<>();

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber <= 0 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Invalid ruleNumber");
        }
        return RULES.get(ruleNumber);
    }

    @Override
    public void writeName(String name) {
        Objects.requireNonNull(name);
        currentName = name;
        if (book.keySet().contains(name)){
            throw new IllegalArgumentException("the name is already on the DeathNote");
        }
        book.keySet().add(name);
        book.get(currentName).add("Heart Attack");
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (book.get(currentName).size() > 0){
            throw new IllegalStateException("Put a new name before writing the death cause");
        }
        book.get(currentName).add(cause);
        long time2 = System.currentTimeMillis();
        if(time2 - timeKeeper <= 40) {
            return true;
        } else {
            book.get(currentName).remove(cause);
            book.get(currentName).add("Heart Attack");
            return false;
        }
    }

    public void writeName(String name, String cause) {
        Objects.requireNonNull(name);
        currentName = name;
        if (book.keySet().contains(name)){
            throw new IllegalArgumentException("the name is already on the DeathNote");
        }
        book.keySet().add(name);
        timeKeeper = System.currentTimeMillis();
        writeDeathCause(cause);
    }

    @Override
    public boolean writeDetails(String details) {
        if (book.get(currentName).size() > 1){
            throw new IllegalStateException("write a death cause before writing the details");
        }
        long time = System.currentTimeMillis();
        book.get(currentName).add(details);
        long time2 = System.currentTimeMillis();
        if(time2 - time <= 384000) {
            return true;
        } else {
            book.get(currentName).remove(details);
            book.get(currentName).add("No details provided");
            return false;
        }
    }

    @Override
    public String getDeathCause(String name) {
        return book.get(name).get(0);
    }

    @Override
    public String getDeathDetails(String name) {
        return book.get(name).get(1);
    }

    @Override
    public boolean isNameWritten(String name) {
        return book.keySet().contains(name);
    }

    public boolean containsName(String name){
        return book.keySet().contains(name);
    }
    
}

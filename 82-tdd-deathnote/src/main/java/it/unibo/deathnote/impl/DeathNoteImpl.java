package it.unibo.deathnote.impl;
import java.util.ArrayList;
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
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(String name) {
        Objects.requireNonNull(name);
        currentName = name;
        if (book.keySet().contains(name)){
            throw new IllegalArgumentException("the name is already on the DeathNote");
        }
        book.put(name, new ArrayList<>());
        book.get(currentName).addAll(List.of("Heart Attack", ""));
        timeKeeper = System.currentTimeMillis();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (book.get(currentName) == null || currentName == null){
            throw new IllegalStateException("Put a new name before writing the death cause");
        }
        book.get(currentName).addFirst(cause);
        long time2 = System.currentTimeMillis();
        if(time2 - timeKeeper <= 40) {
            book.get(currentName).remove("Heart Attack");
            return true;
        } else {
            book.get(currentName).remove(cause);
            return false;
        }
    }

    @Override
    public boolean writeName(String name, String cause) {
        writeName(name);
        return writeDeathCause(cause);
    }

    @Override
    public boolean writeDetails(String details) {
        if (book.get(currentName) == null || currentName == null){
            throw new IllegalStateException("write a death cause before writing the details");
        }
        book.get(currentName).add(details);
        long time2 = System.currentTimeMillis();
        if(time2 - timeKeeper <= 6040) {
            book.get(currentName).remove("");
            return true;
        } else {
            book.get(currentName).remove(details);
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

    @Override
    public boolean containsName(String name){
        return book.keySet().contains(name);
    }
    
}

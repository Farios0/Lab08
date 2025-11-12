package it.unibo.deathnote.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import it.unibo.deathnote.api.DeathNote;

/**
 * This class is an implementation of the interface {@link DeathNote} and its
 * rules.
 */
public final class DeathNoteImpl implements DeathNote {
    private static final int MAX_CAUSE = 40;
    private static final int MAX_DETAILS = 6040;
    private String currentName;
    private long timeKeeper;
    private final Map<String, List<String>> book = new HashMap<>();

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber <= 0 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Invalid ruleNumber");
        }
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(final String name) {
        Objects.requireNonNull(name);
        currentName = name;
        if (book.keySet().contains(name)) {
            throw new IllegalArgumentException("the name is already on the DeathNote");
        }
        book.put(name, new ArrayList<>());
        book.get(currentName).addAll(List.of("Heart Attack", ""));
        timeKeeper = System.currentTimeMillis();
    }

    @Override
    public boolean writeName(final String name, final String cause) {
        writeName(name);
        return writeDeathCause(cause);
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if (book.get(currentName) == null || currentName == null) {
            throw new IllegalStateException("Put a new name before writing the death cause");
        }
        book.get(currentName).addFirst(cause);
        final long time2 = System.currentTimeMillis();
        if (time2 - timeKeeper <= MAX_CAUSE) {
            book.get(currentName).remove("Heart Attack");
            return true;
        } else {
            book.get(currentName).remove(cause);
            return false;
        }
    }

    @Override
    public boolean writeDetails(final String details) {
        if (book.get(currentName) == null || currentName == null) {
            throw new IllegalStateException("write a death cause before writing the details");
        }
        book.get(currentName).add(details);
        final long time2 = System.currentTimeMillis();
        if (time2 - timeKeeper <= MAX_DETAILS) {
            book.get(currentName).remove("");
            return true;
        } else {
            book.get(currentName).remove(details);
            return false;
        }
    }

    @Override
    public String getDeathCause(final String name) {
        return book.get(name).get(0);
    }

    @Override
    public String getDeathDetails(final String name) {
        return book.get(name).get(1);
    }

    @Override
    public boolean isNameWritten(final String name) {
        return book.keySet().contains(name);
    }

    @Override
    public boolean containsName(final String name) {
        return book.keySet().contains(name);
    }

}

package com.rinehartlabs.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@SuppressWarnings("unused")
public class Stat {
    private final StatType type;
    private final Set<StatModifier> modifiers;
    private int points = 0;

    public Stat(StatType type) {
        this.type = type;
        this.modifiers = new HashSet<>();
    }

    public StatType getType() {
        return type;
    }

    public Set<StatModifier> getModifiers() {
        return Set.copyOf(modifiers);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getValue() {
        return points + modifiers.stream().mapToInt(StatModifier::value).sum();
    }

    public void add(StatModifier modifier) {
        modifiers.add(modifier);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("type", type)
                .append("modifiers", modifiers)
                .append("points", points)
                .append("value", getValue())
                .toString();
    }
}

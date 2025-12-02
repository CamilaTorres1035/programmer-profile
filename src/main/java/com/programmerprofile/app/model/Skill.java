package com.programmerprofile.app.model;

import java.util.Objects;

/**
 *
 * @author camil
 */
public class Skill {

    private String id;
    private String name;
    private String level; // Beginner / Intermediate / Advanced
    private int yearsExperience;

    public Skill() {
        this.id = java.util.UUID.randomUUID().toString();
    }

    public Skill(String name, String level, int yearsExperience) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.level = level;
        this.yearsExperience = yearsExperience;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(int yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    // --- equals & hashCode (importante para listas) ---
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skill)) {
            return false;
        }
        Skill skill = (Skill) o;
        return Objects.equals(id, skill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // --- Para debug ---
    @Override
    public String toString() {
        return "Skill{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", level='" + level + '\''
                + ", yearsExperience=" + yearsExperience
                + '}';
    }
}

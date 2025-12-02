package com.programmerprofile.app.repository.impl;

import com.google.gson.reflect.TypeToken;
import com.programmerprofile.app.model.Skill;
import com.programmerprofile.app.repository.JsonRepository;

import com.programmerprofile.app.repository.ISkillRepository;
import com.programmerprofile.app.repository.JsonStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author camil
 */
public class SkillRepositoryJSON extends JsonRepository<Skill> implements ISkillRepository {

    public SkillRepositoryJSON(JsonStore store, String filePath) {
        super(store, filePath, new TypeToken<List<Skill>>() {
        }.getType());
    }

    @Override
    public List<Skill> findAll() {
        return new ArrayList<>(data); // evitar modificar lista interna
    }

    @Override
    public Optional<Skill> findById(String id) {
        return data.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    @Override
    public void add(Skill skill) {
        boolean exists = data.stream()
                .anyMatch(s -> s.getName().equalsIgnoreCase(skill.getName()));

        if (exists) {
            throw new IllegalArgumentException("La habilidad ya existe.");
        }

        data.add(skill);
        save();
    }

    @Override
    public void update(Skill skill) {
        Skill existing = findById(skill.getId())
                .orElseThrow(() -> new IllegalArgumentException("Skill no encontrada."));

        boolean nameConflict = data.stream().anyMatch(s
                -> !s.getId().equals(skill.getId())
                && s.getName().equalsIgnoreCase(skill.getName())
        );

        if (nameConflict) {
            throw new IllegalArgumentException("Otra skill ya usa ese nombre.");
        }

        existing.setName(skill.getName());
        existing.setLevel(skill.getLevel());
        existing.setYearsExperience(skill.getYearsExperience());

        save();
    }

    @Override
    public void delete(String id) {
        if (!data.removeIf(s -> s.getId().equals(id))) {
            throw new IllegalArgumentException("Skill no encontrada.");
        }

        save();
    }
}

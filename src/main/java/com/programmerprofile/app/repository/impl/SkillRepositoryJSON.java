package com.programmerprofile.app.repository.impl;

import com.google.gson.reflect.TypeToken;
import com.programmerprofile.app.model.Skill;
import com.programmerprofile.app.repository.JsonRepository;

import com.programmerprofile.app.repository.ISkillRepository;
import com.programmerprofile.app.repository.JsonStore;
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
        return data;
    }

    @Override
    public Optional<Skill> findById(String id) {
        return data.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    @Override
    public void add(Skill skill) {
        // Validar que no exista una habilidad con el mismo nombre (case insensitive)
        boolean exists = data.stream()
                .anyMatch(s -> s.getName().equalsIgnoreCase(skill.getName()));

        if (exists) {
            throw new IllegalArgumentException("La habilidad '" + skill.getName() + "' ya existe.");
        }

        data.add(skill);
        save(); // persiste en JSON
    }

    @Override
    public void update(Skill skill) {
        Optional<Skill> existingOpt = findById(skill.getId());

        if (existingOpt.isEmpty()) {
            throw new IllegalArgumentException("La habilidad con ID '" + skill.getId() + "' no existe.");
        }

        Skill existing = existingOpt.get();

        // Validar duplicado en nombre (si cambia el nombre)
        boolean nameExists = data.stream()
                .anyMatch(s -> !s.getId().equals(skill.getId())
                && s.getName().equalsIgnoreCase(skill.getName()));

        if (nameExists) {
            throw new IllegalArgumentException("Otra habilidad ya tiene el nombre '" + skill.getName() + "'.");
        }

        // Actualizar campos
        existing.setName(skill.getName());
        existing.setLevel(skill.getLevel());

        save();
    }

    @Override
    public void delete(String id) {
        boolean removed = data.removeIf(skill -> skill.getId().equals(id));

        if (!removed) {
            throw new IllegalArgumentException("No existe una habilidad con ID '" + id + "'.");
        }

        save();
    }

}

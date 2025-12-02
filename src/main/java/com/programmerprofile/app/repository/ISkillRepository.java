package com.programmerprofile.app.repository;

import com.programmerprofile.app.model.Skill;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author camil
 */
public interface ISkillRepository {
    List<Skill> findAll();
    Optional<Skill> findById(String id);
    void add(Skill skill);
    void update(Skill skill);
    void delete(String id);
}

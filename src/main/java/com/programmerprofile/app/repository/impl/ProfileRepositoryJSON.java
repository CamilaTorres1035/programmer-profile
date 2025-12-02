package com.programmerprofile.app.repository.impl;

import com.programmerprofile.app.model.Profile;
import com.programmerprofile.app.repository.IProfileRepository;
import com.programmerprofile.app.repository.JsonStore;

/**
 *
 * @author camil
 */
public final class ProfileRepositoryJSON implements IProfileRepository {

    private final JsonStore store;
    private final String filePath;
    private Profile profile;

    public ProfileRepositoryJSON(JsonStore store, String filePath) {
        this.store = store;
        this.filePath = filePath;
        this.profile = loadProfile();
    }

    public ProfileRepositoryJSON() {
        this.store = new JsonStore();
        this.filePath = "data/profile.json";
        this.profile = loadProfile();
    }

    /**
     * Carga el perfil desde el JSON.Si está vacío, se crea un perfil por
 defecto.
     * @return 
     */
    @Override
    public Profile loadProfile() {
        profile = store.readFromFile(filePath, Profile.class, new Profile());

        if (profile == null) {
            profile = new Profile();
        }
        return profile;
    }

    @Override
    public void saveProfile(Profile profile) {
        this.profile = profile;
        store.writeToFile(filePath, profile);
    }
}

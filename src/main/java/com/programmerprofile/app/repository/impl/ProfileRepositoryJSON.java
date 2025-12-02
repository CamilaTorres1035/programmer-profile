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
        loadProfile();
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
        return null;
    }

    @Override
    public void saveProfile(Profile profile) {
        store.writeToFile(filePath, profile);
    }
}

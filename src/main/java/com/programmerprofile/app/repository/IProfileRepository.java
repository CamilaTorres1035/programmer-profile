package com.programmerprofile.app.repository;

import com.programmerprofile.app.model.Profile;

/**
 *
 * @author camil
 */
public interface IProfileRepository {
    Profile loadProfile();
    void saveProfile(Profile profile);
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programmerprofile.app.model;

/**
 *
 * @author camil
 */
public class Profile {

    private String name;
    private String bio;
    private String experience;
    private String contact;

    private String profileImagePath;
    private String bannerImagePath;

    public Profile() {
    }

    public Profile(String name, String bio, String experience, String contact,
                   String profileImagePath, String bannerImagePath) {
        this.name = name;
        this.bio = bio;
        this.experience = experience;
        this.contact = contact;
        this.profileImagePath = profileImagePath;
        this.bannerImagePath = bannerImagePath;
    }

    // --- Getters & Setters ---
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public String getBannerImagePath() {
        return bannerImagePath;
    }

    public void setBannerImagePath(String bannerImagePath) {
        this.bannerImagePath = bannerImagePath;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", experience='" + experience + '\'' +
                ", contact='" + contact + '\'' +
                ", profileImagePath='" + profileImagePath + '\'' +
                ", bannerImagePath='" + bannerImagePath + '\'' +
                '}';
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programmerprofile.app.repository;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author camil
 * @param <T>
 */
public abstract class JsonRepository<T> {

    protected final JsonStore store;
    protected final String filePath;
    protected final java.lang.reflect.Type listType;

    protected List<T> data;

    public JsonRepository(JsonStore store, String filePath, java.lang.reflect.Type listType) {
        this.store = store;
        this.filePath = filePath;
        this.listType = listType;

        // Carga inicial del archivo JSON
        this.data = store.readFromFile(filePath, listType, new ArrayList<>());
    }

    /**
     * Guarda la lista actual en el archivo JSON.
     */
    public void save() {
        store.writeToFile(filePath, data);
    }

    /**
     * Recarga la lista desde el archivo JSON, ignorando los cambios en memoria.
     */
    public void reload() {
        this.data = store.readFromFile(filePath, listType, new ArrayList<>());
    }

    /**
     * Devuelve la lista completa de elementos.
     * @return 
     */
    public List<T> getAll() {
        return data;
    }

    /**
     * Limpia la lista actual en memoria y en el archivo.
     */
    public void clear() {
        data.clear();
        save();
    }
}

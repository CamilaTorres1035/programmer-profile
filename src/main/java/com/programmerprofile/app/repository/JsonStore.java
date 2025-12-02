package com.programmerprofile.app.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author camil
 */
public class JsonStore {

    private final Gson gson;

    public JsonStore() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Escribe cualquier objeto T al archivo JSON.
     *
     * @param <T>
     * @param filePath
     * @param data
     */
    public <T> void writeToFile(String filePath, T data) {
        ensureFileExists(filePath, data instanceof List ? "[]" : "{}");

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (Exception e) {
            throw new RuntimeException("Error escribiendo JSON en " + filePath, e);
        }
    }

    /**
     * Lee un JSON que contiene un objeto T o una lista de T.
     *
     * @param <T>
     * @param filePath
     * @param defaultValue El valor retornado si el archivo está vacío o
     * corrupto.
     * @param type
     * @return
     */
    public <T> T readFromFile(String filePath, java.lang.reflect.Type type, T defaultValue) {
        ensureFileExists(filePath, defaultValue instanceof List ? "[]" : "{}");

        try (FileReader reader = new FileReader(filePath)) {
            T result = gson.fromJson(reader, type);
            return (result != null) ? result : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Verifica que el archivo exista, si no lo crea vacío.
     */
    private void ensureFileExists(String filePath, String initialJson) {
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();

                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(initialJson);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear archivo JSON: " + filePath, e);
        }
    }
}

package com.programmerprofile.app.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author camil
 */
class JsonStore {

    private final Gson gson;

    public JsonStore() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Escribe cualquier objeto T al archivo JSON.
     */
    public <T> void writeToFile(String filePath, T data) {
        try {
            ensureFileExists(filePath);

            try (FileWriter writer = new FileWriter(filePath)) {
                gson.toJson(data, writer);
            }

        } catch (JsonIOException | IOException e) {
            throw new RuntimeException("Error escribiendo JSON en " + filePath, e);
        }
    }

    /**
     * Lee un JSON que contiene un objeto T o una lista de T.
     *
     * @param defaultValue El valor retornado si el archivo está vacío o
     * corrupto.
     */
    public <T> T readFromFile(String filePath, java.lang.reflect.Type type, T defaultValue) {
        try {
            ensureFileExists(filePath);

            T result;
            try (FileReader reader = new FileReader(filePath)) {
                result = gson.fromJson(reader, type);
            }

            if (result == null) {
                return defaultValue;
            }

            return result;

        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            return defaultValue; // Permite recuperación segura
        }
    }

    /**
     * Verifica que el archivo exista, si no lo crea vacío.
     */
    private void ensureFileExists(String filePath) {
        try {
            File file = new File(filePath);

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();

                try ( // Inicializar como JSON vacío
                        FileWriter writer = new FileWriter(filePath)) {
                    writer.write("{}");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear archivo JSON: " + filePath, e);
        }
    }
}

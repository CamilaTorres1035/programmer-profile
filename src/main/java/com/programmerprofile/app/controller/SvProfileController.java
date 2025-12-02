package com.programmerprofile.app.controller;

import com.programmerprofile.app.model.Profile;
import com.programmerprofile.app.model.Skill;
import com.programmerprofile.app.repository.IProfileRepository;
import com.programmerprofile.app.repository.ISkillRepository;
import com.programmerprofile.app.repository.impl.ProfileRepositoryJSON;
import com.programmerprofile.app.repository.impl.SkillRepositoryJSON;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kosmo
 */
public class SvProfileController extends HttpServlet {

    private IProfileRepository repository;
    private ISkillRepository skillRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        this.repository = new ProfileRepositoryJSON();
        this.skillRepository = new SkillRepositoryJSON();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        switch (path) {
            case "/profile":
                loadProfileView(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada");
        }
    }

    private void loadProfileView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Profile profile = repository.loadProfile();
        List<Skill> skills = skillRepository.findAll();

        request.setAttribute("profile", profile);
        request.setAttribute("skills", skills);

        request.getRequestDispatcher("/profile.jsp")
                .forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/updateProfile":
                updateProfile(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada");
        }
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<String> errors = new ArrayList<>();

        // --- 1. Obtener datos del formulario ---
        String name = request.getParameter("name");
        String bio = request.getParameter("bio");
        String experience = request.getParameter("experience");
        String contact = request.getParameter("contact");
        String profileImagePath = request.getParameter("profileImagePath");
        String bannerImagePath = request.getParameter("bannerImagePath");

        // --- 2. Validaciones básicas ---
        // Nombre obligatorio
        if (name == null || name.isBlank()) {
            errors.add("El nombre es obligatorio.");
        } else if (name.length() > 40) {
            errors.add("El nombre no debe superar los 40 caracteres.");
        }

        // Email válido
        if (contact == null || contact.isBlank()) {
            errors.add("El correo de contacto es obligatorio.");
        } else if (!contact.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errors.add("El correo electrónico no tiene un formato válido.");
        }

        // Bio con máximo de 300 caracteres
        if (bio != null && bio.length() > 300) {
            errors.add("La biografía no puede superar 300 caracteres.");
        }

        // Experience obligatoria
        if (experience == null || experience.isBlank()) {
            errors.add("La experiencia es obligatoria.");
        }

        // Validación simple de rutas de imagen internas
        if (profileImagePath != null && !profileImagePath.startsWith("images/")) {
            errors.add("La ruta de la imagen de perfil es inválida.");
        }

        if (bannerImagePath != null && !bannerImagePath.startsWith("images/")) {
            errors.add("La ruta de la imagen del banner es inválida.");
        }

        // --- Si hay errores → volver al formulario con mensajes ---
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);

            // Volver a cargar el perfil para rellenar el formulario
            Profile existing = repository.loadProfile();
            request.setAttribute("profile", existing);

            request.getRequestDispatcher("/edit.jsp")
                    .forward(request, response);
            return;
        }

        // --- 3. Sanitización básica ---
        name = name.replace("<", "&lt;").replace(">", "&gt;");
        bio = bio.replace("<", "&lt;").replace(">", "&gt;");

        // --- 4. Crear objeto Profile actualizado ---
        Profile updated = new Profile();
        updated.setName(name);
        updated.setBio(bio);
        updated.setExperience(experience);
        updated.setContact(contact);
        updated.setProfileImagePath(profileImagePath);
        updated.setBannerImagePath(bannerImagePath);

        // --- 5. Guardar ---
        repository.saveProfile(updated);

        // --- 6. Redirigir a la vista principal ---
        response.sendRedirect(request.getContextPath() + "/profile");
    }

}

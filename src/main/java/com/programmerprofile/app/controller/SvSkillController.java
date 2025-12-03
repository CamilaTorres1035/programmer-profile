package com.programmerprofile.app.controller;

import com.programmerprofile.app.model.Skill;
import com.programmerprofile.app.repository.ISkillRepository;
import com.programmerprofile.app.repository.JsonStore;
import com.programmerprofile.app.repository.impl.SkillRepositoryJSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * Controlador para la gestión de Habilidades (Skills).
 * Maneja las peticiones para agregar, eliminar y editar habilidades.
 * Mapeado a /skill para coincidir con el diagrama de secuencia (POST /skill?action=add).
 */
@WebServlet(name = "SvSkillController", urlPatterns = {"/skill"})
public class SvSkillController extends HttpServlet {

    private ISkillRepository skillRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        // Inicializamos el repositorio
        JsonStore store = new JsonStore();
        String dataDir = getServletContext().getRealPath("/WEB-INF/data");
        String skillsPath = dataDir + java.io.File.separator + "skills.json";
        
        this.skillRepository = new SkillRepositoryJSON(store, skillsPath);
    }

    /**
     * Maneja las peticiones GET.
     * Usualmente para eliminar (a través de un link) o cargar un formulario de edición.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "list"; // Acción por defecto

        switch (action) {
            case "delete":
                deleteSkill(request, response);
                break;
            case "edit":
                loadSkillForEditing(request, response);
                break;
            default:
                // Si no hay acción específica, redirigimos al perfil principal
                response.sendRedirect(request.getContextPath() + "/profile");
                break;
        }
    }

    /**
     * Maneja las peticiones POST.
     * Usado para enviar datos de formularios (Agregar y Actualizar).
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtenemos la acción desde el parámetro oculto del formulario o la URL
        // Ejemplo: <form action="skill?action=add" method="POST">
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
            return;
        }

        switch (action) {
            case "add":
                addSkill(request, response);
                break;
            case "update":
                updateSkill(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    // --- MÉTODOS DE LÓGICA DE NEGOCIO ---

    private void addSkill(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        try {
            // Obtener datos
            String name = request.getParameter("name");
            String level = request.getParameter("level");
            String yearsStr = request.getParameter("yearsExperience");

            // Validaciones básicas
            if (name == null || name.isBlank() || yearsStr == null) {
                throw new IllegalArgumentException("Nombre y años de experiencia son obligatorios");
            }

            // Crear Objeto (Generamos ID único porque es una habilidad nueva)
            String id = UUID.randomUUID().toString();
            int years = Integer.parseInt(yearsStr);
            
            Skill newSkill = new Skill();
            newSkill.setName(name);
            newSkill.setLevel(level);
            newSkill.setYearsExperience(years);

            // Guardar en repositorio
            skillRepository.add(newSkill);

            // Redireccionar (Patrón Post-Redirect-Get)
            // Redirigimos a /editProfile para que el usuario vea la lista actualizada
            response.sendRedirect(request.getContextPath() + "/editProfile");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Los años de experiencia deben ser un número.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar la habilidad.");
        }
    }

    private void updateSkill(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            // Obtener ID y datos
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String level = request.getParameter("level");
            int years = Integer.parseInt(request.getParameter("yearsExperience"));

            // Buscar si existe
            Optional<Skill> existingSkill = skillRepository.findById(id);
            
            if (existingSkill.isPresent()) {
                Skill skillToUpdate = existingSkill.get();
                skillToUpdate.setName(name);
                skillToUpdate.setLevel(level);
                skillToUpdate.setYearsExperience(years);

                // Actualizar
                skillRepository.update(skillToUpdate);
            }

            // Redirigir
            response.sendRedirect(request.getContextPath() + "/editProfile");

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar.");
        }
    }

    private void deleteSkill(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String id = request.getParameter("id");

        if (id != null) {
            if (id != null) {
                skillRepository.delete(id); 
            }
        }

        // Redirigir de vuelta al editor
        response.sendRedirect(request.getContextPath() + "/editProfile");
    }
    
    /**
     * Carga los datos de una habilidad y los envía a un JSP para editarlos.
     */
    private void loadSkillForEditing(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String id = request.getParameter("id");
        Optional<Skill> skillOpt = skillRepository.findById(id);

        if (skillOpt.isPresent()) {
            request.setAttribute("skill", skillOpt.get());
            request.getRequestDispatcher("/WEB-INF/views/editSkill.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/editProfile");
        }
    }
}
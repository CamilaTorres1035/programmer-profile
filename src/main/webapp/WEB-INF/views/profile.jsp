<%-- 
    Document   : profile
    Created on : 2/12/2025, 10:16:27 a. m.
    Author     : Usuario
--%>

<%@page import="com.programmerprofile.app.model.Skill"%>
<%@page import="com.programmerprofile.app.model.Profile"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>

<%
    Profile profile = (Profile) request.getAttribute("profile");
    List<Skill> skills = (List<Skill>) request.getAttribute("skills");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Perfil de <%= profile != null ? profile.getName() : "Programador" %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
</head>

<body>
<header class="header">
    <div class="header-content">
        <h1><%= profile.getName() %></h1>
        <h2>Desarrollador/a Web</h2>
        <p class="resumen"><%= profile.getBio() %></p>

        <p class="meta">
            📧 <%= profile.getContact() %> &nbsp; | &nbsp;
            💼 <%= profile.getExperience() %>
        </p>
    </div>
</header>

<main class="contenedor">
    <section class="tarjeta">
        <h3>Habilidades</h3>

        <ul class="skills">
            <% if (skills != null) {
                   for (Skill s : skills) { %>

                <li>
                    <span class="skill-nombre"><%= s.getName() %></span>
                    <span class="skill-nivel"><%= s.getLevel() %> · <%= s.getYearsExperience() %> años</span>
                </li>

            <%   }
               } %>
        </ul>

    </section>

    <p><a href="<%= request.getContextPath() %>/" class="link-volver">← Volver al inicio</a></p>
</main>

<script src="<%= request.getContextPath() %>/assets/js/main.js"></script>
</body>
</html>


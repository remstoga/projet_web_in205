package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.MembreServiceImpl;

@WebServlet("/membre_details")
public class MembreDetailsServlet extends HttpServlet {

    MembreServiceImpl membreService;
    EmpruntServiceImpl empruntService;

    public MembreDetailsServlet() {
        try {
            membreService = MembreServiceImpl.getInstance();
            empruntService = EmpruntServiceImpl.getInstance();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.valueOf(req.getParameter("id"));
            req.setAttribute("membre", membreService.getById(id));
            req.setAttribute("listEmprunt", empruntService.getListCurrentByMembre(id));
            this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_details.jsp").forward(req, resp);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Membre membre = membreService.getById(Integer.valueOf(req.getParameter("id")));
            String nom = req.getParameter("nom");
            String prenom = req.getParameter("prenom");
            String abonnement = req.getParameter("abonnement");
            String adresse = req.getParameter("adresse");
            String email = req.getParameter("email");
            String telephone = req.getParameter("telephone");
            membre.setNom(nom);
            membre.setPrenom(prenom);
            membre.setAbonnement(Abonnement.valueOf(abonnement));
            membre.setAdresse(adresse);
            membre.setEmail(email);
            membre.setTelephone(telephone);
            membreService.update(membre);
            resp.sendRedirect("membre_details?id=" + Integer.toString(membre.getId()));

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}
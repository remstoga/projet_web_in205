package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreServiceImpl;

@WebServlet("/livre_details")
public class LivreDetailsServlet extends HttpServlet {

    LivreServiceImpl livreService;
    EmpruntServiceImpl empruntService;

    public LivreDetailsServlet() {
        try {
            livreService = LivreServiceImpl.getInstance();
            empruntService = EmpruntServiceImpl.getInstance();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.valueOf(req.getParameter("id"));
            req.setAttribute("livre", livreService.getById(id));
            req.setAttribute("listEmprunt", empruntService.getListCurrentByLivre(id));
            this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_details.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Livre livre = livreService.getById(Integer.valueOf(req.getParameter("id")));
            String auteur = req.getParameter("auteur");
            String titre = req.getParameter("titre");
            String isbn = req.getParameter("isbn");
            livre.setAuteur(auteur);
            livre.setTitre(titre);
            livre.setIsbn(isbn);
            livreService.update(livre);
            resp.sendRedirect("livre_details?id="+Integer.toString(livre.getId()));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}
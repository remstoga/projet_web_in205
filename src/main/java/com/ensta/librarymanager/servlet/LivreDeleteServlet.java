package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.LivreServiceImpl;

@WebServlet("/livre_delete")
public class LivreDeleteServlet extends HttpServlet {

    LivreServiceImpl livreService;

    public LivreDeleteServlet() {
        try {
            livreService = LivreServiceImpl.getInstance();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.valueOf(req.getParameter("id"));
            String auteur = livreService.getById(id).getAuteur();
            String titre = livreService.getById(id).getTitre();
            String isbn = livreService.getById(id).getIsbn();
            req.setAttribute("idDuLivre", id);
            req.setAttribute("titre", titre);
            req.setAttribute("auteur", auteur);
            req.setAttribute("isbn", isbn);
            this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_delete.jsp").forward(req, resp);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.valueOf(req.getParameter("id"));
            livreService.delete(id);
            resp.sendRedirect("livre_list");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}

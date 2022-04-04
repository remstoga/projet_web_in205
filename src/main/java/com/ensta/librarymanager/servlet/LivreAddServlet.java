package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.service.LivreServiceImpl;

@WebServlet("/livre_add")
public class LivreAddServlet extends HttpServlet {
    private LivreServiceImpl livreService;

    public LivreAddServlet() {
        try {
            livreService = LivreServiceImpl.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_add.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String titre = req.getParameter("titre");
            String auteur= req.getParameter("auteur");
            String isbn = req.getParameter("isbn");
            int id = livreService.create(titre, auteur, isbn);
            resp.sendRedirect("livre_list?id="+Integer.toString(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

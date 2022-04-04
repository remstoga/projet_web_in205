package com.ensta.librarymanager.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreServiceImpl;
import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.MembreServiceImpl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/emprunt_add")
public class EmpruntAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            LivreServiceImpl livreService = LivreServiceImpl.getInstance();
            request.setAttribute("listDispo", livreService.getListDispo());
            MembreServiceImpl membreService = MembreServiceImpl.getInstance();
            request.setAttribute("listMembreEmpruntPossible", membreService.getListMembreEmpruntPossible());
            this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp").forward(request, response);
        } catch (ServiceException e) {
            e.printStackTrace();
            System.out.println("problème au niveau du service");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idLivre = Integer.valueOf(request.getParameter("idLivre"));
            int idMembre = Integer.valueOf(request.getParameter("idMembre"));
            EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
            empruntService.create(idMembre, idLivre, java.time.LocalDate.now());
            response.sendRedirect("dashboard");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}

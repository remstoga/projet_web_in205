package com.ensta.librarymanager.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreServiceImpl;
import com.ensta.librarymanager.service.MembreServiceImpl;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            MembreServiceImpl membreService = MembreServiceImpl.getInstance();
            request.setAttribute("countMembre", membreService.count());
            LivreServiceImpl livreService = LivreServiceImpl.getInstance();
            request.setAttribute("countLivre", livreService.count());
            EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
            request.setAttribute("countEmprunt", empruntService.count());
            request.setAttribute("listCurrentEmprunt", empruntService.getListCurrent());
            this.getServletContext().getRequestDispatcher("/WEB-INF/View/dashboard.jsp").forward(request, response);
        } catch (ServiceException e) {
            e.printStackTrace();
            System.out.println("problème au niveau du service");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}
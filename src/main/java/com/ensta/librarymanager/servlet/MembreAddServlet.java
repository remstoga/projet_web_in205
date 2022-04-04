package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.MembreServiceImpl;

@WebServlet("/membre_add")
public class MembreAddServlet extends HttpServlet {

    MembreServiceImpl membreService;

    public MembreAddServlet() {
        try {
            membreService = MembreServiceImpl.getInstance();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_add.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String nom = req.getParameter("nom");
            String prenom = req.getParameter("prenom");
            String adresse = req.getParameter("adresse");
            String email = req.getParameter("email");
            String telephone = req.getParameter("telephone");
            int id = membreService.create(nom, prenom, adresse, email, telephone);
            resp.sendRedirect("membre_details?id=" + Integer.toString(id));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}
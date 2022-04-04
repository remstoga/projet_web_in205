package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.MembreServiceImpl;

@WebServlet("/membre_delete")
public class MembreDeleteServlet extends HttpServlet {

    MembreServiceImpl membreService;

    public MembreDeleteServlet() {
        try {
            membreService = MembreServiceImpl.getInstance();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.valueOf(req.getParameter("id"));
            Membre membre = membreService.getById(id);
            req.setAttribute("membre", membre);
            this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_delete.jsp").forward(req, resp);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.valueOf(req.getParameter("id"));
            membreService.delete(id);
            resp.sendRedirect("membre_list");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}
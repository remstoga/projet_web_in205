package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.EmpruntServiceImpl;

@WebServlet("/emprunt_return")
public class EmpruntReturnServlet extends HttpServlet {
    private EmpruntServiceImpl empruntService;

    public EmpruntReturnServlet() {
        try {
            empruntService = EmpruntServiceImpl.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("listEmprunt", empruntService.getListCurrent());
            String idDeLEmprunt = req.getParameter("id");
            if (idDeLEmprunt != null)
                req.setAttribute("idDeLEmprunt", idDeLEmprunt);
            this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp").forward(req, resp);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{ 
            int idEmprunt = Integer.valueOf(req.getParameter("id"));
            empruntService.returnBook(idEmprunt);
            resp.sendRedirect("dashboard");
        }
        catch (ServiceException e){
            e.printStackTrace();
        }

    }
}

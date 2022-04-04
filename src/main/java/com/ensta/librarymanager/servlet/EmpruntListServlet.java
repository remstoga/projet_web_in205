package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.dao.EmpruntDaoImpl;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.EmpruntServiceImpl;

@WebServlet("/emprunt_list")
public class EmpruntListServlet extends HttpServlet {
    private EmpruntServiceImpl empruntService;

    public EmpruntListServlet() {
        try {
            empruntService = EmpruntServiceImpl.getInstance();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String showAll = req.getParameter("show");
            if (showAll != null) {
                req.setAttribute("listEmprunt", empruntService.getList());
            } else
                req.setAttribute("listEmprunt", empruntService.getListCurrent());
            this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp").forward(req, resp);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

}

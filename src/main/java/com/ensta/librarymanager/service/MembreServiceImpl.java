package com.ensta.librarymanager.service;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.MembreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Membre;

public class MembreServiceImpl implements MembreService {
    private static MembreServiceImpl instance;

    public static MembreServiceImpl getInstance() throws ServiceException {
        if (instance == null) {
            instance = new MembreServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Membre> getList() throws ServiceException {
        try {
            MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
            return membreDao.getList();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du MembreService, méthode getList");
        }
    }

    @Override
    public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
        try {
            MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
            EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
            List<Membre> listMembre = membreDao.getList();
            List<Membre> listMembreFiltered = new ArrayList<>();
            for (Membre membre : listMembre) {
                if (empruntService.isEmpruntPossible(membre))
                    listMembreFiltered.add(membre);
            }
            return listMembreFiltered;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du MembreService, méthode getListMembreEmpruntPossible");
        }
    }

    @Override
    public Membre getById(int id) throws ServiceException {
        try {
            MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
            return membreDao.getById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du MembreService, méthode getById");
        }
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone)
            throws ServiceException {
        try {
            MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
            return membreDao.create(nom, prenom, adresse, email, telephone, Abonnement.BASIC);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du MembreService, méthode create");
        }
    }

    @Override
    public void update(Membre membre) throws ServiceException {
        try {
            MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
            membreDao.update(membre);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du MembreService, méthode getList");
        }
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
            membreDao.delete(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du MembreService, méthode getList");
        }
    }

    @Override
    public int count() throws ServiceException {
        try {
            MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
            return membreDao.count();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du MembreService, méthode getList");
        }
    }

}

package com.ensta.librarymanager.service;

import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.dao.EmpruntDaoImpl;
import com.ensta.librarymanager.dao.LivreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;

public class EmpruntServiceImpl implements EmpruntService {
    private static EmpruntServiceImpl instance;

    public static EmpruntServiceImpl getInstance() throws ServiceException {
        if (instance == null) {
            instance = new EmpruntServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Emprunt> getList() throws ServiceException {
        try {
            EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
            return empruntDao.getList();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du EmpruntService, méthode getList");
        }
    }

    @Override
    public List<Emprunt> getListCurrent() throws ServiceException {
        try {
            EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
            return empruntDao.getListCurrent();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du EmpruntService, méthode getListCurrent");
        }
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
        try {
            EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
            return empruntDao.getListCurrentByMembre(idMembre);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du EmpruntService, méthode getListCurrentByMembre");
        }
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
        try {
            EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
            return empruntDao.getListCurrentByLivre(idLivre);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du EmpruntService, méthode getListCurrentByLivre");
        }
    }

    @Override
    public Emprunt getById(int id) throws ServiceException {
        try {
            EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
            return empruntDao.getById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du EmpruntService, méthode getByID");
        }
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
        try {
            EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
            empruntDao.create(idMembre, idLivre, dateEmprunt);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du EmpruntService, méthode create");
        }
    }

    @Override
    public void returnBook(int id) throws ServiceException {
        try {
            EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
            Emprunt empruntToModify = empruntDao.getById(id);
            empruntToModify.setDateRetour(java.time.LocalDate.now());
            empruntDao.update(empruntToModify);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du EmpruntService, méthode returnBook");
        }
    }

    @Override
    public int count() throws ServiceException {
        try {
            EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
            return empruntDao.count();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du EmpruntService, méthode count");
        }
    }

    @Override
    public boolean isLivreDispo(int idLivre) throws ServiceException {
        try {
            EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
            List<Emprunt> currentEmprunt = empruntDao.getListCurrentByLivre(idLivre);
            return currentEmprunt.isEmpty();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du EmpruntService, méthode isLivreDispo");
        }
    }

    @Override
    public boolean isEmpruntPossible(Membre membre) throws ServiceException {
        try {
            EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
            List<Emprunt> currentEmprunt = empruntDao.getListCurrentByMembre(membre.getId());
            int nbEmprunt = currentEmprunt.size();
            if (membre.getAbonnement() == Abonnement.BASIC) {
                return nbEmprunt < 2;
            }
            else if (membre.getAbonnement() == Abonnement.PREMIUM) {
                return nbEmprunt < 5;
            }
            else if (membre.getAbonnement() == Abonnement.VIP) {
                return nbEmprunt < 20;
            }
            else{
                throw new DaoException("Problème avec le format de l'abonnement");
            }
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du EmpruntService, méthode isLivreDispo");
        }
    }

}

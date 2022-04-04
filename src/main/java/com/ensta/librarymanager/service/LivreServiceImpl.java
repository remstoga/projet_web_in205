package com.ensta.librarymanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.ensta.librarymanager.dao.LivreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;

public class LivreServiceImpl implements LivreService {
    private LivreDaoImpl livreDao;
    private static LivreServiceImpl instance;

    public static LivreServiceImpl getInstance() throws ServiceException {
        if (instance == null) {
            instance = new LivreServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Livre> getList() throws ServiceException {
        try {
            LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
            return livreDao.getList();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du LivreService, méthode getList");
        }
    }

    @Override
    public List<Livre> getListDispo() throws ServiceException {
        try {
            LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
            EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
            List<Livre> listLivre = livreDao.getList();
            List<Livre> listLivreFiltered = new ArrayList<>();
            for (Livre livre : listLivre) {
                if (empruntService.isLivreDispo(livre.getId()))
                    listLivreFiltered.add(livre);
            }
            return listLivreFiltered;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du LivreService, méthode getListDispo");
        }
    }

    @Override
    public Livre getById(int id) throws ServiceException {
        try {
            LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
            return livreDao.getById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du LivreService, méthode getByID");
        }
    }

    @Override
    public int create(String titre, String auteur, String isbn) throws ServiceException {
        try {
            LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
            return livreDao.create(titre, auteur, isbn);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du LivreService, méthode create");
        }
    }

    @Override
    public void update(Livre livre) throws ServiceException {
        try {
            LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
            livreDao.update(livre);
            ;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du LivreService, méthode update");
        }
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
            livreDao.delete(id);
            ;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du LivreService, méthode delete");
        }
    }

    @Override
    public int count() throws ServiceException {
        try {
            LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
            return livreDao.count();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur au niveau du LivreService, méthode count");
        }
    }

}

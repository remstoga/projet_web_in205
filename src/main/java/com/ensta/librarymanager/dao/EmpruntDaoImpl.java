package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class EmpruntDaoImpl implements EmpruntDao {
    private static EmpruntDaoImpl instance;
    private final String allQuery;
    private final String currentQuery;
    private final String currentMemberQuery;
    private final String currentLivreQuery;
    private final String idQuery;
    private final String createQuery;
    private final String updateQuery;
    private final String countQuery;
    private Connection connection;

    private EmpruntDaoImpl() throws DaoException {
        allQuery = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
        currentQuery = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,  telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,  dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
        currentMemberQuery = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,  telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,  dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?;";
        currentLivreQuery = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,  telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,  dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?;";
        idQuery = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email,  telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,  dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;";
        createQuery = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
        updateQuery = "UPDATE emprunt  SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ?  WHERE id = ?;";
        countQuery = "SELECT COUNT(id) AS count FROM emprunt;";
    }

    public static EmpruntDaoImpl getInstance() throws DaoException {
        if (instance == null) {
            instance = new EmpruntDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Emprunt> getList() throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(allQuery);
            ResultSet rs = pstmnt.executeQuery();
            List<Emprunt> empruntList = new ArrayList<>();
            while (rs.next()) {
                empruntList.add(new Emprunt(rs.getInt("id"),
                        new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"),
                                rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"),
                                Abonnement.valueOf(rs.getString("abonnement"))),
                        new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"),
                                rs.getString("isbn")),
                        rs.getDate("dateEmprunt").toLocalDate(), (rs.getDate("dateRetour") != null ? rs.getDate("dateRetour").toLocalDate():null)));
            }
            connection.close();
            return empruntList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO emprunt, méthode getList");
        }
    }

    @Override
    public List<Emprunt> getListCurrent() throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(currentQuery);
            ResultSet rs = pstmnt.executeQuery();
            List<Emprunt> empruntList = new ArrayList<>();
            while (rs.next()) {
                empruntList.add(new Emprunt(rs.getInt("id"),
                        new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"),
                                rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"),
                                Abonnement.valueOf(rs.getString("abonnement"))),
                        new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"),
                                rs.getString("isbn")),
                        rs.getDate("dateEmprunt").toLocalDate(), (rs.getDate("dateRetour") != null ? rs.getDate("dateRetour").toLocalDate():null)));
            }
            connection.close();
            return empruntList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO emprunt, méthode getListCurrent");
        }
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(currentMemberQuery);
            pstmnt.setInt(1, idMembre);
            ResultSet rs = pstmnt.executeQuery();
            List<Emprunt> empruntList = new ArrayList<>();
            while (rs.next()) {
                empruntList.add(new Emprunt(rs.getInt("id"),
                        new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"),
                                rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"),
                                Abonnement.valueOf(rs.getString("abonnement"))),
                        new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"),
                                rs.getString("isbn")),
                        rs.getDate("dateEmprunt").toLocalDate(), (rs.getDate("dateRetour") != null ? rs.getDate("dateRetour").toLocalDate():null)));
            }
            connection.close();
            return empruntList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO emprunt, getListCurrentByMembre");
        }
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(currentLivreQuery);
            pstmnt.setInt(1, idLivre);
            ResultSet rs = pstmnt.executeQuery();
            List<Emprunt> membreList = new ArrayList<>();
            while (rs.next()) {
                membreList.add(new Emprunt(rs.getInt("id"),
                        new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"),
                                rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"),
                                Abonnement.valueOf(rs.getString("abonnement"))),
                        new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"),
                                rs.getString("isbn")),
                        rs.getDate("dateEmprunt").toLocalDate(), (rs.getDate("dateRetour") != null ? rs.getDate("dateRetour").toLocalDate():null)));
            }
            connection.close();
            return membreList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO emprunt, méthode getListCurrentByLivre");
        }
    }

    @Override
    public Emprunt getById(int id) throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(idQuery);
            pstmnt.setInt(1, id);
            ResultSet rs = pstmnt.executeQuery();
            if (rs.next()) {
                Emprunt returnMembre = new Emprunt(rs.getInt("id"),
                        new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"),
                                rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"),
                                Abonnement.valueOf(rs.getString("abonnement"))),
                        new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"),
                                rs.getString("isbn")),
                        rs.getDate("dateEmprunt").toLocalDate(), (rs.getDate("dateRetour") != null ? rs.getDate("dateRetour").toLocalDate():null));
                connection.close();
                return returnMembre;
            }
            connection.close();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO emprunt, méthode getById");
        }
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(createQuery);
            pstmnt.setInt(1, idMembre);
            pstmnt.setInt(2, idLivre);
            pstmnt.setDate(3, java.sql.Date.valueOf(dateEmprunt));
            pstmnt.setDate(4, null);
            pstmnt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO emprunt, méthode create");
        }
    }

    @Override
    public void update(Emprunt emprunt) throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(updateQuery);
            pstmnt.setInt(1, emprunt.getMembre().getId());
            pstmnt.setInt(2, emprunt.getLivre().getId());
            pstmnt.setDate(3, java.sql.Date.valueOf(emprunt.getDateEmprunt()));
            pstmnt.setDate(4, java.sql.Date.valueOf(emprunt.getDateRetour()));
            pstmnt.setInt(5, emprunt.getId());
            pstmnt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO emprunt, méthode update");
        }
    }

    @Override
    public int count() throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(countQuery);
            ResultSet rs = pstmnt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                connection.close();
                return count;
            } else {
                connection.close();
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO emprunt, lors du count");
        }
    }

}

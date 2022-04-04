package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class MembreDaoImpl implements MembreDao {
    private static MembreDaoImpl instance;
    private final String allQuery;
    private final String idQuery;
    private final String createQuery;
    private final String updateQuery;
    private final String deleteQuery;
    private final String countQuery;
    private Connection connection;

    private MembreDaoImpl() throws DaoException {
        allQuery = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom;";
        idQuery = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?;";
        createQuery = "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);";
        updateQuery = "UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?;";
        deleteQuery = "DELETE FROM membre WHERE id = ?;";
        countQuery = "SELECT COUNT(id) AS count FROM membre;";
    }

    public static MembreDaoImpl getInstance() throws DaoException {
        if (instance == null) {
            instance = new MembreDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Membre> getList() throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(allQuery);
            ResultSet rs = pstmnt.executeQuery();
            List<Membre> membreList = new ArrayList<>();
            while (rs.next()) {
                membreList.add(new Membre(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"),
                        Abonnement.valueOf(rs.getString("abonnement"))));
            }
            connection.close();
            return membreList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO membre méthode getList");
        }
    }

    @Override
    public Membre getById(int id) throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(idQuery);
            pstmnt.setInt(1, id);
            ResultSet rs = pstmnt.executeQuery();
            if (rs.next()) {
                Membre returnMembre = new Membre(id, rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("adresse"),
                        rs.getString("email"), rs.getString("telephone"),
                        Abonnement.valueOf(rs.getString("abonnement")));
                connection.close();
                return returnMembre;
            }
            connection.close();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO membre méthode getById");
        }
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement)
            throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            pstmnt.setString(1, nom);
            pstmnt.setString(2, prenom);
            pstmnt.setString(3, adresse);
            pstmnt.setString(4, email);
            pstmnt.setString(5, telephone);
            pstmnt.setString(6, abonnement.name());
            pstmnt.executeUpdate();
            ResultSet rs = pstmnt.getGeneratedKeys();
            if (rs.next()) {
                connection.close();
                return rs.getInt(1);
            } else {
                connection.close();
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO membre update create");
        }
    }

    @Override
    public void update(Membre membre) throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(updateQuery);
            pstmnt.setString(1, membre.getNom());
            pstmnt.setString(2, membre.getPrenom());
            pstmnt.setString(3, membre.getAdresse());
            pstmnt.setString(4, membre.getEmail());
            pstmnt.setString(5, membre.getTelephone());
            pstmnt.setString(6, membre.getAbonnement().name());
            pstmnt.setInt(7, membre.getId());
            pstmnt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO membre méthode update");
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(deleteQuery);
            pstmnt.setInt(1, id);
            pstmnt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO membre méthode delete");
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
            throw new DaoException("Problème au niveau de la DAO membre méthode count");
        }
    }

}

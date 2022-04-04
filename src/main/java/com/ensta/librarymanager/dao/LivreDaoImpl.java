package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class LivreDaoImpl implements LivreDao {
    private static LivreDaoImpl instance;
    private final String allQuery;
    private final String idQuery;
    private final String createQuery;
    private final String updateQuery;
    private final String deleteQuery;
    private final String countQuery;
    private Connection connection;

    private LivreDaoImpl() throws DaoException {
        allQuery = "SELECT id, titre, auteur, isbn FROM livre;";
        idQuery = "SELECT id, titre, auteur, isbn FROM livre WHERE id = ?;";
        createQuery = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);";
        updateQuery = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;";
        deleteQuery = "DELETE FROM livre WHERE id = ?;";
        countQuery = "SELECT COUNT(id) AS count FROM livre;";
    }

    public static LivreDaoImpl getInstance() throws DaoException {
        try {
            if (instance == null) {
                instance = new LivreDaoImpl();
            }
            return instance;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new DaoException("Problème lors de la créattion de l'instance de la DAO Livre");
        }
    }

    @Override
    public List<Livre> getList() throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(allQuery);
            ResultSet rs = pstmnt.executeQuery();
            List<Livre> livreList = new ArrayList<>();
            while (rs.next())
                livreList.add(new Livre(rs.getInt("id"), rs.getString("titre"), rs.getString("auteur"),
                        rs.getString("isbn")));
            connection.close();
            return livreList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO livre, lors du getList");
        }
    }

    @Override
    public Livre getById(int id) throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(idQuery);
            pstmnt.setInt(1, id);
            ResultSet rs = pstmnt.executeQuery();
            if (rs.next()) {
                Livre returnMembre = new Livre(id, rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                connection.close();
                return returnMembre;
            }
            connection.close();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO livre, lors du getById");
        }
    }

    @Override
    public int create(String titre, String auteur, String isbn) throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            pstmnt.setString(1, titre);
            pstmnt.setString(2, auteur);
            pstmnt.setString(3, isbn);
            pstmnt.executeUpdate();
            ResultSet rs = pstmnt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                connection.close();
                return id;
            } else {
                connection.close();
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO livre, lors du create");
        }
    }

    @Override
    public void update(Livre livre) throws DaoException {
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement pstmnt = connection.prepareStatement(updateQuery);
            pstmnt.setString(1, livre.getTitre());
            pstmnt.setString(2, livre.getAuteur());
            pstmnt.setString(3, livre.getIsbn());
            pstmnt.setInt(4, livre.getId());
            pstmnt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Problème au niveau de la DAO livre, lors du update");
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
            throw new DaoException("Problème au niveau de la DAO livre, lors du delete");
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
            throw new DaoException("Problème au niveau de la DAO livre, lors du count");
        }
    }

}

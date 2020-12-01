package com.minakov.dao.carcaseDAO;

import com.minakov.dao.DAO;
import com.minakov.entity.carcase.Carcase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CarcaseDAO implements DAO<Carcase> {

    private final Connection connection;

    public CarcaseDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Carcase get(int id) {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT id, name FROM carcase WHERE id = " + id)) {
                while (rs.next()) {
                    return new Carcase(rs.getInt("id"),
                            rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new NoSuchElementException("Record with id = " + id + " not found");
    }

    @Override
    public List<Carcase> getAll() {
        final List<Carcase> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT id, name FROM carcase ORDER BY id")) {
                while (rs.next()) {
                    result.add(new Carcase(rs.getInt("id"),
                            rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(Carcase entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO carcase(id, name) VALUES(?,?)")
        ){
            int count = 1;
            preparedStatement.setInt(count++, entity.getId());
            preparedStatement.setString(count, entity.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(int id, Carcase entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE carcase SET name = ? WHERE id = ?")
        ){
            int count = 1;
            preparedStatement.setString(count++, entity.getName());
            preparedStatement.setInt(count, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM carcase WHERE id = ?")
        ) {
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NoSuchElementException("Record with id = " + id + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

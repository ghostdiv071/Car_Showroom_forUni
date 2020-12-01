package com.minakov.dao.colorDAO;

import com.minakov.dao.DAO;
import com.minakov.entity.color.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ColorDAO implements DAO<Color> {

    private final Connection connection;

    public ColorDAO(Connection connection) {
        this.connection = connection;
    }

    public Color get(int id) {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT id, name FROM color WHERE id = " + id)) {
                while (rs.next()) {
                    return new Color(rs.getInt("id"),
                            rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new NoSuchElementException("Record with id = " + id + " not found");
    }

    public List<Color> getAll() {
        final List<Color> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT id, name FROM color ORDER BY id")) {
                while (rs.next()) {
                    result.add(new Color(rs.getInt("id"),
                            rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void save(Color entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO color(id, name) VALUES(?,?)")
        ){
            int count = 1;
            preparedStatement.setInt(count++, entity.getId());
            preparedStatement.setString(count, entity.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(int id, Color entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE color SET name = ? WHERE id = ?")
        ){
            int count = 1;
            preparedStatement.setString(count++, entity.getName());
            preparedStatement.setInt(count, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM color WHERE id = ?")
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

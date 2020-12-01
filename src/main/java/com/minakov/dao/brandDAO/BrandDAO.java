package com.minakov.dao.brandDAO;

import com.minakov.dao.DAO;
import com.minakov.entity.brand.Brand;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BrandDAO implements DAO<Brand> {

    private final Connection connection;

    public BrandDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Brand get(int id) {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT id, name FROM brand WHERE id = " + id)) {
                while (rs.next()) {
                    return new Brand(rs.getInt("id"),
                            rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new NoSuchElementException("Record with id = " + id + " not found");
    }

    @Override
    public List<Brand> getAll() {
        final List<Brand> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT id, name FROM brand ORDER BY id")) {
                while (rs.next()) {
                    result.add(new Brand(rs.getInt("id"),
                            rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(Brand entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO brand(id, name) VALUES(?,?)")
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
    public void update(int id, Brand entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE brand SET name = ? WHERE id = ?")
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
                "DELETE FROM brand WHERE id = ?")
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

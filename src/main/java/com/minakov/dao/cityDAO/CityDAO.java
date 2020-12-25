package com.minakov.dao.cityDAO;

import com.minakov.dao.DAO;
import com.minakov.entity.city.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CityDAO implements DAO<City> {

    private final Connection connection;

    public CityDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public City get(int id) {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT id, name FROM city WHERE id = " + id)) {
                while (rs.next()) {
                    return new City(rs.getInt("id"),
                            rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new NoSuchElementException("Record with id = " + id + " not found");
    }

    @Override
    public List<City> getAll() {
        final List<City> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT id, name FROM city ORDER BY id")) {
                while (rs.next()) {
                    result.add(new City(rs.getInt("id"),
                            rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(City entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO city(id, name) VALUES(?,?)")
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
    public void update(int id, City entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE city SET name = ? WHERE id = ?")
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
                "DELETE FROM city WHERE id = ?")
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

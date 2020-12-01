package com.minakov.dao.modelDAO;

import com.minakov.dao.DAO;
import com.minakov.dao.brandDAO.BrandDAO;
import com.minakov.entity.brand.Brand;
import com.minakov.entity.model.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ModelDAO implements DAO<Model> {

    private final Connection connection;
    private final BrandDAO brandDAO;

    public ModelDAO(Connection connection) {
        this.connection = connection;
        brandDAO = new BrandDAO(connection);
    }

    @Override
    public Model get(int id) {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("select model.id as id, model.name as name, \n" +
                    "\tbrand.name as brand_name\n" +
                    "\tfrom brand\n" +
                    "\tjoin model on brand.id = model.brand_id\n" +
                    "\twhere model.id = " + id))
            {
                while (rs.next()) {
                    System.out.println(rs.getInt("id"));
                    return new Model(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("brand_name")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new NoSuchElementException("Record with id = " + id + " not found");
    }

    @Override
    public List<Model> getAll() {
        final List<Model> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("select model.id as id, model.name as model, \n" +
                    "\tbrand.name as brand\n" +
                    "\tfrom brand\n" +
                    "\tjoin model on brand.id = model.brand_id"))
            {
                while (rs.next()) {
                    result.add(new Model(rs.getInt("id"),
                            rs.getString("model"),
                            rs.getString("brand"))
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(Model entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO model(id, name, brand_id) VALUES(?,?,?)")
        ){
            int count = 1;
            preparedStatement.setInt(count++, entity.getId());
            preparedStatement.setString(count++, entity.getName());
            for (Brand brand : brandDAO.getAll()) {
                if (entity.getBrand().equals(brand.getName())){
                    preparedStatement.setInt(count, brand.getId());
                    break;
                }
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(int id, Model entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE model SET name = ?, brand_id = ? WHERE id = ?")
        ){
            int count = 1;
            preparedStatement.setString(count++, entity.getName());
            for (Brand brand : brandDAO.getAll()) {
                if (entity.getBrand().equals(brand.getName())){
                    preparedStatement.setInt(count++, brand.getId());
                    break;
                }
            }
            preparedStatement.setInt(count, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM model WHERE id = ?")
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

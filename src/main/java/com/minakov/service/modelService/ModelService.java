package com.minakov.service.modelService;

import com.minakov.utils.ConnectionUtils;
import com.minakov.dao.modelDAO.ModelDAO;
import com.minakov.entity.model.Model;
import com.minakov.service.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ModelService implements Service<Model> {

    private ModelDAO modelDAO;
    private int id;

    public ModelService() {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            modelDAO = new ModelDAO(connection);
            id = modelDAO.getAll().get(modelDAO.getAll().size()-1).getId();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Model get(int id) {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            modelDAO = new ModelDAO(connection);
            return modelDAO.get(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Model> getAll() {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            modelDAO = new ModelDAO(connection);
            return modelDAO.getAll();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void save(Model entity) {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            modelDAO = new ModelDAO(connection);
            if (entity.getId() <= this.id & entity.getId() != 0){
                modelDAO.update(entity.getId(), entity);
            } else if (entity.getId() == 0){
                this.id++;
                entity.setId(this.id);
                modelDAO.save(entity);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            modelDAO = new ModelDAO(connection);
            modelDAO.delete(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

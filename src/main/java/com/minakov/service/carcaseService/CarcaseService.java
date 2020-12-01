package com.minakov.service.carcaseService;

import com.minakov.ConnectionUtils;
import com.minakov.dao.carcaseDAO.CarcaseDAO;
import com.minakov.entity.carcase.Carcase;
import com.minakov.service.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class CarcaseService implements Service<Carcase> {

    private CarcaseDAO carcaseDAO;
    private int id;

    public CarcaseService() {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            carcaseDAO = new CarcaseDAO(connection);
            id = carcaseDAO.getAll().get(carcaseDAO.getAll().size()-1).getId();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Carcase get(int id) {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            carcaseDAO = new CarcaseDAO(connection);
            return carcaseDAO.get(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Carcase> getAll() {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            carcaseDAO = new CarcaseDAO(connection);
            return carcaseDAO.getAll();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void save(Carcase entity) {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            carcaseDAO = new CarcaseDAO(connection);
            if (entity.getId() <= this.id & entity.getId() != 0){
                carcaseDAO.update(entity.getId(), entity);
            } else if (entity.getId() == 0){
                this.id++;
                entity.setId(this.id);
                carcaseDAO.save(entity);
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
            carcaseDAO = new CarcaseDAO(connection);
            carcaseDAO.delete(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

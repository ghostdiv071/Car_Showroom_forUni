package com.minakov.service.carShowroomService;

import com.minakov.ConnectionUtils;
import com.minakov.dao.brandDAO.BrandDAO;
import com.minakov.dao.carShowroomDAO.CarShowroomDAO;
import com.minakov.entity.carShowroom.CarShowroom;
import com.minakov.service.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class CarShowroomService implements Service<CarShowroom> {

    private CarShowroomDAO dao;
    private int id;

    public CarShowroomService() {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value))
        {
            dao = new CarShowroomDAO(connection);
            id = dao.getAll().get(dao.getAll().size()-1).getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CarShowroom get(int id) {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value))
        {
            dao = new CarShowroomDAO(connection);
            return dao.get(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CarShowroom> getAll() {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value))
        {
            dao = new CarShowroomDAO(connection);
            return dao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CarShowroom> getAll(String filterText, int number) {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value))
        {
            dao = new CarShowroomDAO(connection);
            return dao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(CarShowroom entity) {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            dao = new CarShowroomDAO(connection);
            if (entity.getId() <= this.id & entity.getId() != 0){
                dao.update(entity.getId(), entity);
            } else if (entity.getId() == 0){
                this.id++;
                entity.setId(this.id);
                dao.save(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            dao = new CarShowroomDAO(connection);
            dao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

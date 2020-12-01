package com.minakov.service.colorService;

import com.minakov.ConnectionUtils;
import com.minakov.dao.colorDAO.ColorDAO;
import com.minakov.entity.color.Color;
import com.minakov.service.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ColorService implements Service<Color> {

    private ColorDAO colorDAO;
    private int id;

    public ColorService() {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            colorDAO = new ColorDAO(connection);
            id = colorDAO.getAll().get(colorDAO.getAll().size()-1).getId();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Color get(int id) {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            colorDAO = new ColorDAO(connection);
            return colorDAO.get(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Color> getAll() {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            colorDAO = new ColorDAO(connection);
            return colorDAO.getAll();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void save(Color color) {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            colorDAO = new ColorDAO(connection);
            if (color.getId() <= this.id & color.getId() != 0){
                colorDAO.update(color.getId(), color);
            } else if (color.getId() == 0){
                this.id++;
                color.setId(this.id);
                colorDAO.save(color);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id) {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            colorDAO = new ColorDAO(connection);
            colorDAO.delete(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

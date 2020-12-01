package com.minakov.service.brandService;

import com.minakov.ConnectionUtils;
import com.minakov.dao.brandDAO.BrandDAO;
import com.minakov.entity.brand.Brand;
import com.minakov.service.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;


public class BrandService implements Service<Brand> {

    private BrandDAO brandDAO;
    private int id;

    public BrandService() {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            brandDAO = new BrandDAO(connection);
            id = brandDAO.getAll().get(brandDAO.getAll().size()-1).getId();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Brand get(int id) {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            brandDAO = new BrandDAO(connection);
            return brandDAO.get(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Brand> getAll() {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            brandDAO = new BrandDAO(connection);
            return brandDAO.getAll();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void save(Brand entity) {
        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            brandDAO = new BrandDAO(connection);
            if (entity.getId() <= this.id & entity.getId() != 0){
                brandDAO.update(entity.getId(), entity);
            } else if (entity.getId() == 0){
                this.id++;
                entity.setId(this.id);
                brandDAO.save(entity);
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
            brandDAO = new BrandDAO(connection);
            brandDAO.delete(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

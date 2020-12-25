package com.minakov.dao.carShowroomDAO;

import com.minakov.dao.DAO;
import com.minakov.dao.cityDAO.CityDAO;
import com.minakov.entity.carShowroom.CarShowroom;
import com.minakov.entity.city.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CarShowroomDAO implements DAO<CarShowroom> {

    private final Connection connection;
    private final CityDAO cityDAO;

    public CarShowroomDAO(Connection connection) {
        this.connection = connection;
        cityDAO = new CityDAO(connection);
    }

    @Override
    public CarShowroom get(int id) {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("select car_showroom.id as id, car_showroom.street as street,\n" +
                    "\tcar_showroom.house_number as house, city.name as city\n" +
                    "\tfrom city\n" +
                    "\tjoin car_showroom on city.id = car_showroom.city_id\n" +
                    "\twhere car_showroom.id" + id))
            {
                while (rs.next()) {
                    System.out.println(rs.getInt("id"));
                    return new CarShowroom(rs.getInt("id"),
                            rs.getString("street"),
                            rs.getShort("house"),
                            rs.getString("city")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new NoSuchElementException("Record with id = " + id + " not found");
    }

    @Override
    public List<CarShowroom> getAll() {
        final List<CarShowroom> result = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("select car_showroom.id as id, car_showroom.street as street,\n" +
                    "\tcar_showroom.house_number as house, city.name as city\n" +
                    "\tfrom city\n" +
                    "\tjoin car_showroom on city.id = car_showroom.city_id\n")
            ) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id"));
                    result.add(new CarShowroom(rs.getInt("id"),
                            rs.getString("street"),
                            rs.getShort("house"),
                            rs.getString("city"))
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public void save(CarShowroom entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO car_showroom(id, street, house_number, city_id) VALUES(?,?,?,?)")
        ){
            int count = 1;
            preparedStatement.setInt(count++, entity.getId());
            preparedStatement.setString(count++, entity.getStreet());
            preparedStatement.setInt(count++, entity.getHouseNumber());
            for (City city : cityDAO.getAll()) {
                if (entity.getCity().equals(city.getName())){
                    preparedStatement.setInt(count, city.getId());
                    break;
                }
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(int id, CarShowroom entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE car_showroom SET street = ?, house_number = ?, city_id = ? WHERE id = ?")
        ){
            int count = 1;
            preparedStatement.setString(count++, entity.getStreet());
            preparedStatement.setInt(count++, entity.getHouseNumber());
            for (City city : cityDAO.getAll()) {
                if (entity.getCity().equals(city.getName())){
                    preparedStatement.setInt(count, city.getId());
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
                "DELETE FROM car_showroom WHERE id = ?")
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

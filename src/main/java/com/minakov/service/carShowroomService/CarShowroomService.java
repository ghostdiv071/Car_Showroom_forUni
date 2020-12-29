package com.minakov.service.carShowroomService;

import com.minakov.utils.ConnectionUtils;
import com.minakov.dao.carShowroomDAO.CarShowroomDAO;
import com.minakov.entity.carShowroom.CarShowroom;
import com.minakov.service.Service;
import com.minakov.utils.Statistics;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarShowroomService implements Service<CarShowroom> {

    private final String filterQuery = "select car_showroom.id as id, car_showroom.street as street,\n" +
            "\tcar_showroom.house_number as house, city.name as city\n" +
            "\tfrom city\n" +
            "\tjoin car_showroom on city.id = car_showroom.city_id\n" +
            "\twhere ";

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
        if (filterText == null || filterText.isEmpty()) {
            return getAll();
        } else {
            return findAllMatches(filterText, number);
        }
    }

    private List<CarShowroom> findAllMatches(String filterText, int number) {
        List<CarShowroom> showrooms = new ArrayList<>();

        StringBuilder query = new StringBuilder(filterQuery);
        switch (number) {
            case 1:
                query.append("lower(car_showroom.street) ");
                break;
            case 2:
                query.append("lower(car_showroom.house_number) ");
                break;
            case 3:
                query.append("lower(city.name) ");
                break;
        }
        query.append("like lower('%").append(filterText).append("%');");

        System.out.println(query.toString());

        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query.toString())) {
                    while (resultSet.next()){
                        showrooms.add(new CarShowroom(resultSet.getInt("id"),
                                resultSet.getString("street"),
                                resultSet.getShort("house"),
                                resultSet.getString("city"))
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showrooms;
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

    public List<Statistics> statistics() {
        final List<Statistics> statistics = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(ConnectionUtils.URL.value,
                ConnectionUtils.USER.value, ConnectionUtils.PASSWORD.value)
        ) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(Statistics.STAT_QUERY)) {
                    while (resultSet.next()) {
                        statistics.add(new Statistics(
                                resultSet.getString("City"),
                                resultSet.getInt("Taxed_Cars"))
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statistics;
    }
}

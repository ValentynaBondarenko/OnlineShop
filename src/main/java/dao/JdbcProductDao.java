package dao;

import entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private final static String INSERT_NEW = "INSERT INTO users VALUES (?,?,?,?,?)";
    private final static String DELETE = "DELETE FROM users WHERE id=?";
    private final static String SELECT = "SELECT id, name, price, creationdate FROM products";
    private static final String UPDATE = "UPDATE users SET firstName=?,lastName=?, salary=?, dateOfBirth=? WHERE id=?";
    private static final String SEARCH = "SELECT id, firstName, lastName, salary,dateOfBirth FROM users WHERE LOWER (firstName) LIKE ? OR LOWER (lastName) LIKE ?;";

    private ConnectionFactory connectionFactory = new ConnectionFactory();
    private ProductRowMapper productRowMapper = new ProductRowMapper();

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = connectionFactory.connectionToDatabase();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SELECT);
            while (resultSet.next()) {
                Product user = productRowMapper.mapRow(resultSet);
                products.add(user);
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Can not show all products from database", exception);
        }
        return products;
    }


    @Override
    public void addNewProduct(Product product) {
        try (Connection connection = connectionFactory.connectionToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW)) {

            preparedStatement.setString(1, String.valueOf(product.getId()));
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, String.valueOf(product.getPrice()));
            preparedStatement.setString(4, product.getCreationDate());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throw new RuntimeException("Can not insert a new product into database");
        }
    }

    @Override
    public void remove(int id) {

        try (Connection connection = connectionFactory.connectionToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(1, Integer.parseInt(String.valueOf(id)));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cant delete product from a database");
        }
    }

    @Override
    public void edit(Product product) {
        try (Connection connection = connectionFactory.connectionToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, String.valueOf(product.getPrice()));
            preparedStatement.setInt(3, Integer.parseInt(product.getCreationDate()));
            preparedStatement.setInt(4, product.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Cant edit user from database");
        }
    }

    @Override
    public List<Product> search(String searchText) {
        String searchWord = "%" + searchText + "%";
        List<Product> products = new ArrayList<>();

        try (Connection connection = connectionFactory.connectionToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH)) {

            preparedStatement.setString(1, searchWord);
            preparedStatement.setString(2, searchWord);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Product product = productRowMapper.mapRow(resultSet);
                    products.add(product);
                }
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("error. Can't search users with text: " + searchText, e);
        }
    }
}

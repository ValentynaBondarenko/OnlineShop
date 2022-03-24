package dao;

import entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getInt("price"));
        product.setCreationDate(resultSet.getString("creationdate"));
        return product;
    }
}

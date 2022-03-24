package dao;

import entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts();

    void addNewProduct(Product product);

    void remove(int id);

    void edit(Product product);


    List<Product> search(String searchText);
}

package ma.emsi.evaluation.products.dao;

import java.util.List;

import ma.emsi.evaluation.products.entity.Product;

public interface ProductDAO {

    void save(Product product);

    Product findById(Long id);

    List<Product> findAll();

    void delete(Long id);
}

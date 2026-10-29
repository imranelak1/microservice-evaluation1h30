package ma.emsi.evaluation.products.service;

import java.util.List;

import ma.emsi.evaluation.products.dao.ProductDAO;
import ma.emsi.evaluation.products.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void save(Product product) {
        productDAO.save(product);
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productDAO.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    public void delete(Long id) {
        productDAO.delete(id);
    }
}

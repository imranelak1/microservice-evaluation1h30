package ma.emsi.evaluation.products.dao;

import java.util.List;

import ma.emsi.evaluation.products.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAOImpl implements ProductDAO {

    private final SessionFactory sessionFactory;

    public ProductDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Product product) {
        currentSession().persist(product);
    }

    @Override
    public Product findById(Long id) {
        return currentSession().get(Product.class, id);
    }

    @Override
    public List<Product> findAll() {
        return currentSession()
                .createQuery("from Product order by id", Product.class)
                .getResultList();
    }

    @Override
    public void delete(Long id) {
        Session session = currentSession();
        Product product = session.get(Product.class, id);
        if (product != null) {
            session.remove(product);
        }
    }

    private Session currentSession() {
        // La session appartient a la transaction ouverte par ProductService.
        return sessionFactory.getCurrentSession();
    }
}

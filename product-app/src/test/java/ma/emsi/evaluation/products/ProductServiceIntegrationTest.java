package ma.emsi.evaluation.products;

import java.util.List;

import ma.emsi.evaluation.products.entity.Product;
import ma.emsi.evaluation.products.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(properties = "app.demo.enabled=false")
@ActiveProfiles("test")
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @BeforeEach
    void clearTestDatabase() {
        for (Product product : productService.findAll()) {
            productService.delete(product.getId());
        }
    }

    @Test
    void persistsFindsListsAndDeletesProductsAcrossServiceTransactions() {
        Product ordinateur = new Product("Ordinateur", 7500.0, 10);
        Product souris = new Product("Souris", 150.0, 30);
        Product clavier = new Product("Clavier", 250.0, 20);
        productService.save(ordinateur);
        productService.save(souris);
        productService.save(clavier);

        assertThat(ordinateur.getId()).isNotNull();
        assertThat(souris.getId()).isNotEqualTo(ordinateur.getId());
        Product loaded = productService.findById(souris.getId());
        assertThat(loaded).isNotNull();
        assertThat(loaded.getNom()).isEqualTo("Souris");
        assertThat(loaded.getPrix()).isEqualTo(150.0);
        assertThat(loaded.getQuantite()).isEqualTo(30);

        List<Product> all = productService.findAll();
        assertThat(all).extracting(Product::getNom)
                .containsExactly("Ordinateur", "Souris", "Clavier");
        assertThat(all).extracting(Product::getId).isSorted();

        productService.delete(souris.getId());

        assertThat(productService.findById(souris.getId())).isNull();
        assertThat(productService.findAll()).extracting(Product::getNom)
                .containsExactly("Ordinateur", "Clavier");
    }

    @Test
    void missingProductCanBeLookedUpAndDeletedWithoutChangingOtherProducts() {
        Product clavier = new Product("Clavier", 250.0, 20);
        productService.save(clavier);

        assertThat(productService.findById(Long.MAX_VALUE)).isNull();
        assertThatCode(() -> productService.delete(Long.MAX_VALUE)).doesNotThrowAnyException();
        assertThat(productService.findAll()).extracting(Product::getId)
                .containsExactly(clavier.getId());
    }

    @Test
    void rollsBackAnInsertWhenTheSurroundingTransactionFails() {
        TransactionTemplate transaction = new TransactionTemplate(transactionManager);

        assertThatThrownBy(() -> transaction.executeWithoutResult(status -> {
            productService.save(new Product("Produit annulé", 999.0, 1));
            throw new IllegalStateException("Annulation demandée");
        })).isInstanceOf(IllegalStateException.class)
                .hasMessage("Annulation demandée");

        // Cette lecture ouvre une nouvelle transaction, hors de celle qui a echoue.
        assertThat(productService.findAll()).isEmpty();
    }
}

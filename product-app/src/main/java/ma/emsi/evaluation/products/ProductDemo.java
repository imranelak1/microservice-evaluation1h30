package ma.emsi.evaluation.products;

import java.math.BigDecimal;

import ma.emsi.evaluation.products.entity.Product;
import ma.emsi.evaluation.products.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.demo.enabled", havingValue = "true", matchIfMissing = true)
public class ProductDemo implements CommandLineRunner {

    private final ProductService productService;

    public ProductDemo(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) {
        System.out.println("=== Gestion des produits ===");
        System.out.println();

        Product ordinateur = new Product("Ordinateur", 7500.0, 10);
        Product souris = new Product("Souris", 150.0, 30);
        Product clavier = new Product("Clavier", 250.0, 20);
        ajouter(ordinateur);
        ajouter(souris);
        ajouter(clavier);

        System.out.println();
        System.out.println("Liste des produits :");
        for (Product product : productService.findAll()) {
            String prix = BigDecimal.valueOf(product.getPrix()).stripTrailingZeros().toPlainString();
            System.out.printf("%d - %s - %s DH%n", product.getId(), product.getNom(), prix);
        }

        Product resultat = productService.findById(souris.getId());
        System.out.println();
        System.out.println("Recherche : " + (resultat == null ? "introuvable" : resultat.getNom()));

        System.out.println();
        System.out.println("Suppression du produit " + souris.getId());
        productService.delete(souris.getId());

        System.out.println();
        System.out.println("Liste finale :");
        for (Product product : productService.findAll()) {
            System.out.printf("%d - %s%n", product.getId(), product.getNom());
        }
    }

    private void ajouter(Product product) {
        productService.save(product);
        System.out.println("Produit ajouté : " + product.getNom());
    }
}

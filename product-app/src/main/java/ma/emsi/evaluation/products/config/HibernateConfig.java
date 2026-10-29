package ma.emsi.evaluation.products.config;

import java.util.Properties;
import javax.sql.DataSource;

import ma.emsi.evaluation.products.entity.Product;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.hibernate.HibernateTransactionManager;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource, Environment environment) {
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setAnnotatedClasses(Product.class);

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto",
                environment.getProperty("hibernate.hbm2ddl.auto", "update"));
        properties.setProperty("hibernate.show_sql",
                environment.getProperty("hibernate.show_sql", "false"));
        properties.setProperty("hibernate.format_sql",
                environment.getProperty("hibernate.format_sql", "true"));
        factory.setHibernateProperties(properties);
        return factory;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}

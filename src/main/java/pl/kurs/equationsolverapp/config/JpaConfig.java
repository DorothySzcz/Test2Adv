package pl.kurs.equationsolverapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class JpaConfig {

    @Autowired
    private Environment env;

    @Profile(value={"dev", "!prod"})
    @Bean
    public LocalContainerEntityManagerFactoryBean createEmf_dev(JpaVendorAdapter adapter) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", env.getProperty("dev.url"));
        properties.put("javax.persistence.jdbc.driver", env.getProperty("dev.driver"));
        properties.put("javax.persistence.jdbc.user", env.getProperty("dev.user"));
        properties.put("javax.persistence.jdbc.password", env.getProperty("dev.password"));
        properties.put("hibernate.dialect", env.getProperty("dev.dialect"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("dev.hbm2ddl.auto"));
        properties.put("show_sql", env.getProperty("dev.show_sql"));
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", env.getProperty("dev.use_jdbc_metadata_defaults"));
        emf.setJpaPropertyMap(properties);
        emf.setJpaVendorAdapter(adapter);
        emf.setPackagesToScan("pl.kurs.equationsolverapp.model");
        return emf;
    }

    @Profile("prod")
    @Bean
    public LocalContainerEntityManagerFactoryBean createEmf_prod(JpaVendorAdapter adapter) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", env.getProperty("prod.url"));
        properties.put("javax.persistence.jdbc.user", env.getProperty("prod.user"));
        properties.put("javax.persistence.jdbc.password", env.getProperty("prod.password"));
        properties.put("javax.persistence.jdbc.driver", env.getProperty("prod.driver"));
        properties.put("javax.persistence.schema-generation.database.action", env.getProperty("prod.schema-generation.database.action"));
        emf.setJpaPropertyMap(properties);
        emf.setJpaVendorAdapter(adapter);
        emf.setPackagesToScan("pl.kurs.equationsolverapp.model");
        return emf;
    }


    @Profile(value={"dev", "!prod"})
    @Bean
    public JpaVendorAdapter createVendorAdapterDev() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.valueOf(env.getProperty("dev.database")));
        adapter.setShowSql(true);
        return adapter;
    }

    @Profile("prod")
    @Bean
    public JpaVendorAdapter createVendorAdapterProd() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.valueOf(env.getProperty("prod.database")));
        adapter.setShowSql(true);
        return adapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }


}

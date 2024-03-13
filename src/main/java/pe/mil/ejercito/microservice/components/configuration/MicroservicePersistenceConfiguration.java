package pe.mil.ejercito.microservice.components.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pe.mil.ejercito.microservice.components.properties.DataSourceProperties;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * MicroservicePersistenceConfiguration
 * <p>
 * MicroservicePersistenceConfiguration class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 8/03/2024
 */

@Log4j2
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "pe.mil.ejercito.microservice.repositories",
        entityManagerFactoryRef = "managerFactoryBean"
)
public class MicroservicePersistenceConfiguration {


    private final DataSourceProperties dataSourceProperties;

    public MicroservicePersistenceConfiguration(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }


    @Bean(name = "primaryDataSource")
    public DataSource primaryDataSource() {
        log.debug("init primaryDataSource, {}", dataSourceProperties);
        return DataSourceBuilder.create()
                .url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .driverClassName(dataSourceProperties.getDriver())
                .build();

    }

    @Bean(name = "multiRoutingDataSource")
    public DataSource multiRoutingDataSource() {

        log.info("init primaryDataSource in multiRoutingDataSource method, {}", primaryDataSource());

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("novoMicroservicesDataSource", primaryDataSource());
        var multiRoutingDataSource = new MultiRoutingDataSource();
        log.debug("multiRoutingDataSource value connection {} ", multiRoutingDataSource);
        multiRoutingDataSource.setDefaultTargetDataSource(primaryDataSource());
        multiRoutingDataSource.setTargetDataSources(targetDataSources);

        log.info("DataSource successfully configured");
        return multiRoutingDataSource;
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("primaryDataSource") DataSource primaryDataSource) {
        log.info("init jdbcTemplate, {}", primaryDataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(primaryDataSource);
        log.info("JdbcTemplate successfully configured");
        return jdbcTemplate;
    }

    @Primary
    @Bean(name = "managerFactoryBean")
    public LocalContainerEntityManagerFactoryBean managerFactoryBean(@Name(value = "multiRoutingDataSource") DataSource multiRoutingDataSource) {
        log.debug("init managerFactoryBean, {}", dataSourceProperties);
        LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        managerFactoryBean.setDataSource(multiRoutingDataSource);
        managerFactoryBean.setPackagesToScan("pe.mil.ejercito.microservice.repositories");
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        managerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        managerFactoryBean.setJpaProperties(getProperties());
        return managerFactoryBean;
    }


    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Name(value = "managerFactoryBean") AbstractEntityManagerFactoryBean multiEntityManager) {
        log.debug("init multiTransactionManager, {}", multiEntityManager);
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(multiEntityManager.getObject());
        log.info("PlatformTransactionManager successfully configured");
        return transactionManager;
    }


    @Bean(name = "sessionFactoryBean")
    public LocalSessionFactoryBean sessionFactoryBean(@Name(value = "primaryDataSource") DataSource primaryDataSource) {
        log.debug("init getSessionFactory, {}", primaryDataSource);
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        try {
            factory.setDataSource(primaryDataSource);
            factory.setPackagesToScan("pe.mil.ejercito.microservice.repositories");
            factory.setHibernateProperties(getProperties());
        } catch (Exception e) {
            log.info("session factory error {} ", e.getMessage());
        }

        return factory;
    }


    @Bean
    public PersistenceExceptionTranslationPostProcessor processor() {
        log.debug("init processor");
        return new PersistenceExceptionTranslationPostProcessor();
    }


    private Properties getProperties() {
        final Properties properties = new Properties();
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        properties.put("hikari.minimumIdle", 5);
        properties.put("hikari.maximumPoolSize", 400);
        properties.put("hikari.idleTimeout", 30000);
        properties.put("hikari.maxLifetime", 2_000_000);
        properties.put("hikari.connectionTimeout", 30000);
        properties.put("hikari.poolName", "HikariPool");
        properties.put("hikari.auto-commit", true);
        properties.put("hikari.data-source-properties.cachePrepStmts", true);
        properties.put("hikari.data-source-properties.prepStmtCacheSize", 512);
        properties.put("hikari.data-source-properties.prepStmtCacheSqlLimit", 2048);
        properties.put("hikari.data-source-properties.useServerPrepStmts", true);
        return properties;
    }
}



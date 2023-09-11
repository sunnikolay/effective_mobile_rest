package com.nstiazhkin.smapi.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource( { "classpath:application.yml" } )
@EnableJpaRepositories(
        basePackages = "com.nstiazhkin.smapi.repo",
        entityManagerFactoryRef = "smEntityManager",
        transactionManagerRef = "smTransactionManager"
)
public class AppDbConfig {
    
    @Value( "${spring.datasource.hibernate.dialect}" )
    private String dialect;
    
    @Value( "${spring.datasource.hibernate.hbm2ddl.auto}" )
    private String hbmDdl;
    
    @Primary
    @Bean()
    @ConfigurationProperties( prefix = "spring.datasource" )
    @FlywayDataSource
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean( name = "smEntityManager" )
    @Primary
    public LocalContainerEntityManagerFactoryBean smEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource( dataSource() );
        em.setPackagesToScan( "com.nstiazhkin.smapi.domain" );
        
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter( vendorAdapter );
        final HashMap<String, Object> properties = new HashMap<>();
        properties.put( "hibernate.hbm2ddl.auto", hbmDdl );
        properties.put( "hibernate.dialect", dialect );
        em.setJpaPropertyMap( properties );
        
        return em;
    }
    
    @Bean
    @Primary
    public PlatformTransactionManager smTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( smEntityManager().getObject() );
        return transactionManager;
    }
    
}

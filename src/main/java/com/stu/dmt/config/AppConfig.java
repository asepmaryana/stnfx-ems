package com.stu.dmt.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = {"com.stu.dmt.repository"})
@ComponentScan(basePackages = "com.stu.dmt")
@PropertySource(value = "classpath:application.properties")
public class AppConfig {
	
	@Autowired
	private Environment env;
	
	@Bean(destroyMethod="close")
    public DataSource dataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try { dataSource.setDriverClass(env.getRequiredProperty("jdbc.driverClassName")); } catch (Exception e) {}
        dataSource.setJdbcUrl(env.getRequiredProperty("jdbc.url"));
        dataSource.setUser(env.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
        
        dataSource.setAcquireIncrement(Integer.parseInt(env.getRequiredProperty("c3p0.acquireIncrement")));
        dataSource.setIdleConnectionTestPeriod(Integer.parseInt(env.getRequiredProperty("c3p0.idleConnectionTestPeriod")));
        dataSource.setInitialPoolSize(Integer.parseInt(env.getRequiredProperty("c3p0.initialPoolSize")));
        dataSource.setMinPoolSize(Integer.parseInt(env.getRequiredProperty("c3p0.minPoolSize")));
        dataSource.setMaxPoolSize(Integer.parseInt(env.getRequiredProperty("c3p0.maxPoolSize")));
        dataSource.setMaxStatements(Integer.parseInt(env.getRequiredProperty("c3p0.maxStatements")));
		
        return dataSource;
    }
	
	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource)
	{
		DataSourceInitializer dsInitializer = new DataSourceInitializer();
		dsInitializer.setDataSource(dataSource);
		ResourceDatabasePopulator dbPopulator = new ResourceDatabasePopulator();
		if(!env.getProperty("hibernate.init_db").trim().equals("true"))
			dbPopulator.addScript(new ClassPathResource("data.sql"));
		dsInitializer.setDatabasePopulator(dbPopulator);
		dsInitializer.setEnabled(Boolean.parseBoolean(env.getProperty("hibernate.init_db").trim()));
		return dsInitializer;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setJpaProperties(hibernateProperties());
		em.setPackagesToScan("com.stu.dmt.model");
		em.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		return em;
	}
	
	private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
        if(!env.getProperty("hibernate.hbm2ddl.auto").trim().equals("")) properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        if(!env.getProperty("hibernate.default_schema").trim().equals("")) properties.put("hibernate.default_schema", env.getProperty("hibernate.default_schema"));
        properties.put("hibernate.jdbc.batch_size", env.getRequiredProperty("hibernate.jdbc.batch_size"));
        return properties;        
    }		
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		EntityManagerFactory emf = entityManagerFactory().getObject();
		JpaTransactionManager tx = new JpaTransactionManager(emf);
		tx.setJpaDialect(new HibernateJpaDialect());
		return tx;
	}
	
	@Bean
	public PollerConfig getPollerConfig() {
		PollerConfig cfg = new PollerConfig();
		cfg.setCorePoolSize(Integer.parseInt(env.getProperty("poller.corePoolSize", "5")));
		cfg.setMaximumPoolSize(Integer.parseInt(env.getProperty("poller.maximumPoolSize", "10")));
		cfg.setPollCycle(Integer.parseInt(env.getProperty("poller.pollCycle", "5")));
		return cfg;
	}
}

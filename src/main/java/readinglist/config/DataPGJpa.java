package readinglist.config;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

//import org.hibernate.ejb.HibernateEntityManager;

import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import org.springframework.orm.jpa.HibernateEntityManager;

@Configuration
@PropertySource("classpath:application.properties")
@EntityScan(basePackages={"readinglist.dataPG"})
@EnableJpaRepositories("readinglist.dataPG")
//@ImportResource({"classpath*:applicationContext.xml"})
public class DataPGJpa {
	
//	@Bean
//    public DataSource getDataSource() {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("org.postgresql.Driver");
//        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/testjpa");
//        dataSourceBuilder.username("postgres");
//        dataSourceBuilder.password("LOS//UNG");
//        
//        return dataSourceBuilder.build();
//    }
	
	
	
	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("org.postgresql.Driver");
	    dataSource.setUrl("jdbc:postgresql://localhost:5432/testjpa");
	    dataSource.setUsername("postgres");
	    dataSource.setPassword("LOS//UNG");
	    
	    // schema init
	    //ClassPathResource initSchema = new ClassPathResource("schema-postgresql.sql");
	    //ClassPathResource initData   = new ClassPathResource("data-postgresql.sql");
	    //DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, initData);
	    //DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
	    //DatabasePopulatorUtils.execute(databasePopulator, dataSource);

	    return dataSource;
	}
	
	
	
    @Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.POSTGRESQL);
		//adapter.setDatabase(Database.PostgreSQL9Dialect);
		adapter.setShowSql(false);
		adapter.setGenerateDdl(true);
		return adapter;
	}
	
 
  
  @Bean
  // C'EST L'ENTITY MANAGER FACTORY
  public Object emf() {
    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    //LocalEntityManagerFactoryBean emf = new LocalEntityManagerFactoryBean();
    //emf.setDataSource(getDataSource());
    emf.setDataSource(dataSource());
    //emf.setPersistenceUnitName("reader");
    //emf.setPersistenceUnitName("book");
    //emf.setPersistenceUnitName("livres");
    emf.setPersistenceUnitName("users");
    emf.setJpaVendorAdapter(jpaVendorAdapter());
    emf.setPackagesToScan("mysecurejob.dataPG");
    Properties jpaProperties = new Properties();
    jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
    //jpaProperties.setProperty("hibernate.hbm2ddl.auto", "none");
    emf.setJpaProperties(jpaProperties);
    return emf;
  }
  
  
	
  
  
  @Bean
  public JpaTransactionManager transactionManager() {
    return new JpaTransactionManager(); // does this need an emf???
  }
  
    
}

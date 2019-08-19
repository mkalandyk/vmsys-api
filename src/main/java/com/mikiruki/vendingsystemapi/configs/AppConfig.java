package com.mikiruki.vendingsystemapi.configs;

import com.mikiruki.vendingsystemapi.dao.*;
import com.mikiruki.vendingsystemapi.models.*;
import com.mikiruki.vendingsystemapi.services.MailingService;
import com.mikiruki.vendingsystemapi.services.OrderListService;
import com.mikiruki.vendingsystemapi.services.VendingMachineService;
import com.mikiruki.vendingsystemapi.utils.JSONParserHelper;
import javassist.compiler.SyntaxError;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

@Configuration
@EnableCaching
public class AppConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    BasicDataSource dataSource() throws URISyntaxException {

        BasicDataSource dataSource = new BasicDataSource();
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + "/vms");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    LocalSessionFactoryBean hibernate5AnnotatedSessionFactory() throws URISyntaxException {

        Properties hibernateProperties = new Properties();

        hibernateProperties.put("hibernate.dialect", "com.mikiruki.vendingsystemapi.utils.CustomPostgreSQLDialect");
        hibernateProperties.put("hibernate.current_session_context_class", "thread");
        hibernateProperties.put("hibernate.show_sql", true);
        hibernateProperties.put("hibernate.enable_lazy_load_no_trans", true);

        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();

        localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setAnnotatedClasses(Product.class, VendingMachine.class, MachineContentID.class, MachineContent.class, OrderList.class, User.class, Warehouse.class);
        localSessionFactoryBean.setHibernateProperties(hibernateProperties);

        return localSessionFactoryBean;
    }

    @Bean
    Session mailSession() {

        Session mailSession;

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        mailSession = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("vmsservice001@gmail.com", "Q@power22");
            }
        });

        return mailSession;
    }

    @Bean
    CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cacheFactory = new EhCacheManagerFactoryBean();
        cacheFactory.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cacheFactory.setShared(true);
        return cacheFactory;
    }

    @Bean
    MailingService mailingService() {
        return new MailingService();
    }

    @Bean
    UserDAO userDAO() {
        return new UserDAOImpl();
    }

    @Bean
    VendingMachineDAO vendingMachineDAO() {
        return new VendingMachineDAOImpl();
    }

    @Bean
    ProductDAO productDAO() {
        return new ProductDAOImpl();
    }

    @Bean
    OrderListDAO orderListDAO() {
        return new OrderListDAOImpl();
    }

    @Bean
    VendingMachineService vendingMachineService() {
        return  new VendingMachineService();
    }

    @Bean
    OrderListService orderListService() {
        return new OrderListService();
    }

    @Bean
    WarehouseDAO warehouseDAO() {
        return new WarehouseDAOImpl();
    }

    @Bean
    JSONParserHelper<User> userParserHelper() { return new JSONParserHelper<User>(){}; }

    @Bean
    JSONParserHelper<Product> productParserHelper() { return new JSONParserHelper<Product>(){}; }
}

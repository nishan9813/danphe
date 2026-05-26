package com.example.repository.config;

import com.example.common.config.ExternalConfigProvider;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.apache.ibatis.session.SqlSessionFactory;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.example.repository.mapper")
public class DataSourceConfig {

    private final ExternalConfigProvider config;

    public DataSourceConfig(ExternalConfigProvider config) {
        this.config = config;
    }


    @PostConstruct
    void migrate() {
        if ("true".equalsIgnoreCase(config.get("spring.flyway.enabled", "true"))) {
            Flyway.configure()
                    .dataSource(
                            requireProperty("db.url"),
                            requireProperty("db.username"),
                            requireProperty("db.password"))
                    .schemas(config.get("spring.flyway.schemas", "admin,global_event").split(","))
                    .defaultSchema(config.get("spring.flyway.default-schema", "admin"))
                    .locations(config.get("spring.flyway.locations", "classpath:db/migration").split(","))
                    .baselineOnMigrate(Boolean.parseBoolean(config.get("spring.flyway.baseline-on-migrate", "true")))
                    .baselineVersion(config.get("spring.flyway.baseline-version", "0"))
                    .load()
                    .migrate();
        }
    }


    @Bean
    @Primary
    public DataSource dataSource(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(300000);
        config.setMaxLifetime(1800000);

        return new HikariDataSource(config);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath*:mappers/**/*.xml")
        );
        org.apache.ibatis.session.Configuration mybatis =
                new org.apache.ibatis.session.Configuration();
        mybatis.setMapUnderscoreToCamelCase(true);
        factory.setConfiguration(mybatis);
        return factory.getObject();
    }

    private String requireProperty(String key) {
        String value = config.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Required DB config property '" + key + "' is missing from external config file");
        }
        return value;
    }
}
package com.linktic.helper.connector.config;

import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class H2Config {

  @Bean
  public DataSource createDataSourceWithConfigurationProperties() {
    log.info("Creando Data Source a partir de propiedades de configuración.");
    return DataSourceBuilder.create()
        .url(dataSourceProperties().getUrl())
        .driverClassName(dataSourceProperties().getDriverClassName())
        .username(dataSourceProperties().getUsername())
        .password(dataSourceProperties().getPassword())
        .build();
  }

  /**
   * Data source properties data source config properties.
   *
   * @return the data source config properties
   */
  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSourceConfigProperties dataSourceProperties() {
    return new DataSourceConfigProperties();
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private class DataSourceConfigProperties {

    private String url;
    private String username;
    private String password;
    private String driver;
    private String driverClassName;
    private String maxActive;
    private String maxIdle;
    private String minIdle;
    private String initialSize;
    private String removeAbandoned;
    private String schema;
  }


}

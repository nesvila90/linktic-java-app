package com.linktic.helper.connector.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

@Configuration
@Profile("test")
public class H2ConsoleConfig {

  private org.h2.tools.Server webServer;

  private org.h2.tools.Server tcpServer;

  @EventListener(org.springframework.context.event.ContextRefreshedEvent.class)
  public void start() throws java.sql.SQLException {
    this.webServer = org.h2.tools.Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start();
    this.tcpServer = org.h2.tools.Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
  }

  @EventListener(org.springframework.context.event.ContextClosedEvent.class)
  public void stop() {
    this.tcpServer.stop();
    this.webServer.stop();
  }
}

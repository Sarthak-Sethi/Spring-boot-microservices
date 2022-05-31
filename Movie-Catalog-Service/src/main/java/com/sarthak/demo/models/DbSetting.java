package com.sarthak.demo.models;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("db")
public class DbSetting {
    private String connectionUrl;
    private String host;
    private int port;

    DbSetting() {

    }

    /**
     * @return String return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return int return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }


    /**
     * @return String return the connectionUrl
     */
    public String getConnectionUrl() {
        return connectionUrl;
    }

    /**
     * @param connectionUrl the connectionUrl to set
     */
    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

}

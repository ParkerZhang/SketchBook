package com.example.springvaultconfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("example")
public class MyConfiguration {
    private String username;
    private String password;

    public void setUserName(String username) {
        this.username = username;
    }

    public void String (String username) {
        this.username = username;
    }
    public String getUserName() {
         return username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

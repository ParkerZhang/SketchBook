package org.example;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@ConfigurationProperties("example")
@Profile("App2")
public class MyConfiguration {
    @Value("${hello.world}") // another way of retrieving value from hello.world in vault ,
    private String username;
    private String password; // retrieves  value from example.password in vault

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

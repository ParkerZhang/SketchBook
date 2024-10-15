package org.example.minikube;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public static String password;
    @Value("${spring.application.password}")
    public void setPassword(String value){password=value;}


    public static String hostname;
    @Value("${HOSTNAME}")
    public void setHostname(String value) {hostname=value;}

    public static String username;
    @Value("${spring.application.username}")
    public void setUsername(String value){username=value;}

    public static String appName;
    @Value("${spring.application.name")
    public void setAppName (String value){appName=value;};
}

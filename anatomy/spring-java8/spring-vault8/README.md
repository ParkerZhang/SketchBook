Spring-Vault running on Java 8 , temurin-1.8.0_422

Spring-cloud version : 
    2021.0.2        3.1.0 ✔
    2021.0.9        3.1.4 ✔
    2022.0.1        4.0.0 ✗
    2022.0.2        4.0.1 ✗
    2023.0.3        4.1.3 ✗

spring-boot-starter 
    2.6.7 

application.properties
    
    spring.config.import= vault://
        direts spring config import from vault, 
    spring.config.import=
        spring load from application.properties
    if any spring.config.import has vault://, spring will load value from vault, 
        regardless where it is, in application.properties or application-App1.properties

#spring.config.import= vault://
App1 
    using Autowire @Value

App2 
    using @ConfigurationProperties
        spring does not fail when value does not exist in either properties files or vault

Run :
    start vault
        env.bat
        local-run-vault.bat
        set_sample.bat
    Vault UI http://127.0.0.1:8200 
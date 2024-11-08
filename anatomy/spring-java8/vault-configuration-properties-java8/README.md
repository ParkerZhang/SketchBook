
to turn on Vault

java -jar .\target\vault-configuration-properties-java8-1.0-SNAPSHOT.jar --spring.profiles.active=App2 --spring.config.import=vault://

to turn off Vualt
java -jar .\target\vault-configuration-properties-java8-1.0-SNAPSHOT.jar --spring.profiles.active=App2 --spring.config.import

To Run Vault:
    start vault
        env.bat
        local-run-vault.bat
        set_sample.bat
    Vault UI http://127.0.0.1:8200 
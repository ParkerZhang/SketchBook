package spa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SpaApplication {
    private static final Logger log = LoggerFactory.getLogger(SpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner spa(UniqueTransactionIdRepository repository) {
        return (args) -> {
            repository.save(new UniqueTransactionId(30000, 20000, "new", "S00003000020000", "Equity.Performance.Basket"));
            repository.save(new UniqueTransactionId(30001, 19990, "new", "S00003000119999", "Equity.Performance.Basket"));

            log.info("UTI findAll():");
            repository.findAll().forEach(
                    uti -> {
                        log.info(uti.toString());
                    }
            );
            log.info("");

            log.info("UTI findSwapId(30000L):");

            repository.findBySwapId(30000L).forEach(id -> {
                        log.info(id.toString());
                    }
            );
            log.info("");
        };
    }
}

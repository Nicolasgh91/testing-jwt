package ar.edu.uade.api.deliverygo.config;

import ar.edu.uade.api.deliverygo.model.Comercio;
import ar.edu.uade.api.deliverygo.repository.ComercioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;

@Configuration
public class DemoDataLoader {
    @Bean
    CommandLineRunner data(ComercioRepository r) {
        return args -> {
            r.save(new Comercio("Burger House", "20-12345678-9", "burger@deliverygo.com", true));
        };
    }
}

package ar.edu.uade.api.deliverygo.repository;

import ar.edu.uade.api.deliverygo.model.Comercio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComercioRepository extends JpaRepository<Comercio, Long> {
    Optional<Comercio> findByCuit(String cuit);
}

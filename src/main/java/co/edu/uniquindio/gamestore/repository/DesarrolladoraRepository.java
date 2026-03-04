package co.edu.uniquindio.gamestore.repository;

import co.edu.uniquindio.gamestore.entity.Desarrolladora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DesarrolladoraRepository extends JpaRepository<Desarrolladora, Long> {

    @Override
    Optional<Desarrolladora> findById(Long aLong);
}

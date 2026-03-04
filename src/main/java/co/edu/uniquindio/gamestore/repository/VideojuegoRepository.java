package co.edu.uniquindio.gamestore.repository;

import co.edu.uniquindio.gamestore.entity.Genero;
import co.edu.uniquindio.gamestore.entity.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {

    //Spring construye la consulta automáticamente
    List<Videojuego> findByGenero (Genero genero);

    //Busca los titulos que contengan texto ignorando las mayusculas
    List<Videojuego> findByTituloContainingIgnoreCase(String titulo);

    Optional<Videojuego> findById(Long id);


    //Consulta utilizando JPQL (orientado a las enidades)
    @Query("SELECT v FROM Videojuego v WHERE v.precio BETWEEN :min AND :max")
    List<Videojuego> buscarPorRangoPrecio (@Param("min") Double min, @Param("max") Double max);

    //Consulta en SQL nativo
    @Query(value = "SELECT * FROM videojuego ORDER BY fecha_creacion DESC LIMIT 5", nativeQuery = true)
    List<Videojuego> ultimos5();
}

package co.edu.uniquindio.gamestore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;
@Getter
@Setter

public class Desarrolladora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column
    private String sitioWeb;

    @OneToMany(mappedBy = "desarrolladora")
    //Relación inversa, ya que la FK está en la entidad Videojuego
    @JsonIgnore
    //Evita los ciclos infinitos en JSON
    private List<Videojuego> videojuegos;
}

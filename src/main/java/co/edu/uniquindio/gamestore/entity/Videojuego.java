package co.edu.uniquindio.gamestore.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter


@Entity //Indica que esta clase es una entidad JPA en la tabla de la BD
public class Videojuego {

    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Se autogenera en la BD
    private Long id;

    @Column(nullable = false, length = 100)
    //No puede ser null y tiene maximo
    private String titulo;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false, unique = true)
    private String codigoRegistro;

    @Enumerated(EnumType.STRING) //Guarda el nombre del enum como texto en la base de datos
    private Genero genero;

    @ManyToOne
    //Muchos videojuegos pueden pertenecer a la misma desarrolladora
    @JoinColumn(name = "desarrolladora_id", nullable = false)
    //FK en la tabla de los videojuegos
    private Desarrolladora desarrolladora;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

    @Transient
    //Esto no se guarda en la base de datos
    private Double precioConIva;

    @PrePersist
    //Es ejecutado antes de ser insertado en la BD
    public void prePersist(){
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    //Se ejecuta antes de actualizar
    public void preUpdate(){
        fechaActualizacion = LocalDateTime.now();
    }


}

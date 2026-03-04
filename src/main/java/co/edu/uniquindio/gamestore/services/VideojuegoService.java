package co.edu.uniquindio.gamestore.services;


import co.edu.uniquindio.gamestore.entity.Desarrolladora;
import co.edu.uniquindio.gamestore.entity.Videojuego;
import co.edu.uniquindio.gamestore.exception.ResourceNotFoundException;
import co.edu.uniquindio.gamestore.repository.DesarrolladoraRepository;
import co.edu.uniquindio.gamestore.repository.VideojuegoRepository;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//Indica que está clase es una clase de lógica de negocio
public class VideojuegoService {

    private final VideojuegoRepository repository;
    private final DesarrolladoraRepository desarrolladoraRepository;

    public VideojuegoService(VideojuegoRepository repository, DesarrolladoraRepository desarrolladoraRepository){
        this.repository = repository;
        this.desarrolladoraRepository = desarrolladoraRepository;
    }

    public Videojuego crear(Videojuego videojuego){

        //Validacion de que precio > 0
        if(videojuego.getPrecio() < 0){
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        if(videojuego.getTitulo() == null || videojuego.getTitulo().isBlank()){
            throw new IllegalArgumentException("El titulo es obligatorio");

        }

        //Verificar que ladesarrolladora del videojuego sí exista
        Desarrolladora desarrolladora = desarrolladoraRepository.findById(videojuego.getDesarrolladora().getId()).orElseThrow(()->
                new ResourceNotFoundException("La desarrolladora no se encontró"));

        videojuego.setDesarrolladora(desarrolladora);

        Videojuego guardado = repository.save(videojuego);

        calcularIva(guardado);

        return guardado;
    }

    private void calcularIva(Videojuego videojuego) {
        videojuego.setPrecioConIva(videojuego.getPrecio() * 1.19);
    }

    public Videojuego aplicardescuento(Long id, double porcentaje){

        Videojuego videojuego = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException( "El videojuego no se encontró"));

        if(porcentaje <= 0 || porcentaje > 100) {
            throw new IllegalArgumentException("Porcentaje inválido");
        }

        double nuevoPrecio = videojuego.getPrecio() - (videojuego.getPrecio() * porcentaje / 100);
        videojuego.setPrecio(nuevoPrecio);

        repository.save(videojuego);

        calcularIva(videojuego);

        return videojuego;
    }

    public List<Videojuego> listarTodos(){
        List<Videojuego> lista = repository.findAll();

        //Se calcula el IVA de cada videojuego listadoju
        for (Videojuego videojuego : lista){
            videojuego.setPrecioConIva(videojuego.getPrecio() * 1.19);
        }

        return lista;
    }

}

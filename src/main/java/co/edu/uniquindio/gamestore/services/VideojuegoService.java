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

    private final VideojuegoRepository videojuegoRepository;
    private final DesarrolladoraRepository desarrolladoraRepository;

    public VideojuegoService(VideojuegoRepository repository, DesarrolladoraRepository desarrolladoraRepository){
        this.videojuegoRepository = repository;
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

        Videojuego guardado = videojuegoRepository.save(videojuego);

        calcularIva(guardado);

        return guardado;
    }

    public List<Videojuego> buscarPorRango(Double min, Double max){
        List<Videojuego> lista = videojuegoRepository.buscarPorRangoPrecio(min, max);
        lista.forEach(this::calcularIva);
        return lista;

    }



    private void calcularIva(Videojuego videojuego) {
        videojuego.setPrecioConIva(videojuego.getPrecio() * 1.19);
    }



    public Videojuego aplicardescuento(Long id, double porcentaje){

        Videojuego videojuego = videojuegoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException( "El videojuego no se encontró"));

        if(porcentaje <= 0 || porcentaje > 100) {
            throw new IllegalArgumentException("Porcentaje inválido");
        }

        double nuevoPrecio = videojuego.getPrecio() - (videojuego.getPrecio() * porcentaje / 100);
        videojuego.setPrecio(nuevoPrecio);

        videojuegoRepository.save(videojuego);

        calcularIva(videojuego);

        return videojuego;
    }



    public Videojuego obtenerPorId(Long id){
        Videojuego videojuego = videojuegoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Videojuego no encontrado"));
        calcularIva(videojuego);

        return videojuego;

    }



    public List<Videojuego> listarTodos(){
        List<Videojuego> lista = videojuegoRepository.findAll();

        //Se calcula el IVA de cada videojuego listadoju
        for (Videojuego videojuego : lista){
            videojuego.setPrecioConIva(videojuego.getPrecio() * 1.19);
        }

        return lista;
    }



    public Videojuego actualizar(Long id, Videojuego nuevo){

        Videojuego existente = obtenerPorId(id);

        validar(nuevo);

        existente.setPrecio(nuevo.getPrecio());
        existente.setGenero(nuevo.getGenero());
        existente.setTitulo(nuevo.getTitulo());
        existente.setCodigoRegistro(nuevo.getCodigoRegistro());

        return videojuegoRepository.save(existente);
    }



    private void validar(Videojuego videojuego){
        if(videojuego.getPrecio() < 0)
            throw new IllegalArgumentException("El prexio no puede ser negativo");

        if(videojuego.getTitulo() == null || videojuego.getTitulo().isBlank())
            throw new IllegalArgumentException("Titulo obligatorio");
    }

    public List<Videojuego> buscarPorTitulo(String titulo){
        List<Videojuego> lista = videojuegoRepository.findByTituloContainingIgnoreCase(titulo);
        lista.forEach(this::calcularIva);
        return lista;
    }

    public void eliminar(Long id){
        if(!videojuegoRepository.existsById(id))
            throw new IllegalArgumentException("El videojuego no se encontró");

        videojuegoRepository.deleteById(id);
    }
}

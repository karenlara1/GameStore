package co.edu.uniquindio.gamestore.controller;


import co.edu.uniquindio.gamestore.entity.Videojuego;
import co.edu.uniquindio.gamestore.services.VideojuegoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Recibe solicitudes HTTP, retorna JSON
@RestController

@RequestMapping("/api/videojuegos")
public class VideojuegoController {
    private final VideojuegoService service;

    public VideojuegoController(VideojuegoService service){
        this.service = service;
    }

    //Obtener todos los videojuegos
    @GetMapping
    List<Videojuego> listar(){
        return service.listarTodos();
    }

    //GObtener por id
    @GetMapping("/{id}")
    public Videojuego obtenerPorId(@PathVariable Long id){
        return service.obtenerPorId(id);
    }

    //Crea un objeto Videojuego
    @PostMapping
    public Videojuego crear(@RequestBody Videojuego videojuego){
        return service.crear(videojuego);
    }

    //Actualizar id
    @PutMapping("/{id}")
    public Videojuego actualizar(@PathVariable Long id, @RequestBody Videojuego videojuego){
        return service.actualizar(id, videojuego);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }

    @GetMapping("/buscae")
    public List<Videojuego> buscarPorTitulo(@RequestParam String titulo){
        return service.buscarPorTitulo(titulo);
    }

    @GetMapping("/rango-precio")
    public List<Videojuego> buscarPorRangoPrecio(@RequestParam Double min, @RequestParam Double max){
        return service.buscarPorRango(min, max);
    }
    @PatchMapping("/{id}/descuento")
    public Videojuego descuento(@PathVariable Long id, @RequestParam double porcentaje){
        return service.aplicardescuento(id, porcentaje);
    }
}

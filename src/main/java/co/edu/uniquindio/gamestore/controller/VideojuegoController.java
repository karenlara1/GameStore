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

    //Crea un objeto Videojuego
    @PostMapping
    public Videojuego crear(@RequestBody Videojuego videojuego){
        return service.crear(videojuego);
    }


    @PatchMapping("/{id}/descuento")
    public Videojuego descuento(@PathVariable Long id, @RequestParam double porcentaje){
        return service.aplicardescuento(id, porcentaje);
    }
}

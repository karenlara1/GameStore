package co.edu.uniquindio.gamestore.controller;

import co.edu.uniquindio.gamestore.entity.Desarrolladora;
import co.edu.uniquindio.gamestore.services.DesarrolladoraService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/desarrolladoras")
public class DesarrolladoraController {

    private final DesarrolladoraService desarrolladoraService;

    public DesarrolladoraController(DesarrolladoraService desarrolladoraService){
        this.desarrolladoraService = desarrolladoraService;
    }

    //Get -> Listas las desarrolladoras
    @GetMapping
    public List<Desarrolladora> listarTodas(){
        return desarrolladoraService.listarTodas();
    }

    //Post -> Crear una nueva
    @PostMapping
    public Desarrolladora crear(@RequestBody Desarrolladora desarrolladora){
        return desarrolladoraService.crear(desarrolladora);
    }
}

package co.edu.uniquindio.gamestore.services;

import co.edu.uniquindio.gamestore.entity.Desarrolladora;
import co.edu.uniquindio.gamestore.repository.DesarrolladoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesarrolladoraService {

    private final DesarrolladoraRepository desarrolladoraRepository;

    public DesarrolladoraService(DesarrolladoraRepository desarrolladoraRepository){
        this.desarrolladoraRepository = desarrolladoraRepository;
    }

    public List<Desarrolladora> listarTodas(){
        return desarrolladoraRepository.findAll();
    }

    public Desarrolladora crear(Desarrolladora desarrolladora){
        return desarrolladoraRepository.save(desarrolladora);
    }
}

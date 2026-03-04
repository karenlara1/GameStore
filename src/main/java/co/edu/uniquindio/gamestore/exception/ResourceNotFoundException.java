package co.edu.uniquindio.gamestore.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String mensaje){
        super(mensaje);
    }
}

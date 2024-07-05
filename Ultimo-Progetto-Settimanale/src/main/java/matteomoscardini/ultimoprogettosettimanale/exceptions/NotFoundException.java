package matteomoscardini.ultimoprogettosettimanale.exceptions;


import java.util.UUID;

public class NotFoundException extends RuntimeException{
    public NotFoundException(UUID id){
        super("The searched Record with id " + id +"is not found");

    }
    public NotFoundException(String message){super(message);}
}

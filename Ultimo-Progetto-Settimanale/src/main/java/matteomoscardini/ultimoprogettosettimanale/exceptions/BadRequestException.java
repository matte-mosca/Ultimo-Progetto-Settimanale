package matteomoscardini.ultimoprogettosettimanale.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException{
    public List<ObjectError> errorsList;
    public BadRequestException(String message){
        super(message);
    }

    public BadRequestException(List<ObjectError> errorsList){
        super("There are some errors in the payload-validation");
        this.errorsList = errorsList;
    }
}

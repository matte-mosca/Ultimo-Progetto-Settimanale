package matteomoscardini.ultimoprogettosettimanale.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUserPAyload (@NotEmpty(message = "Don't forget to add your first name")
                              @Size(min = 3, max = 30, message = "The name must be between 3 and 30 characters")
                              String name,
                              @NotEmpty(message = "Don't forget to add your surname")
                              @Size(min = 3, max = 30, message = "The surname must be between 3 and 30 characters")
                              String surname,
                              @NotEmpty(message = "Don't forget to add your email")
                              @Email(message = "L'email inserita non è valida")
                              String email,
                              @NotEmpty(message = "Don't forget to add a password")
                              @Size(min = 4, message = "The password must be between 6 and 30 characters")
                              String password){
}

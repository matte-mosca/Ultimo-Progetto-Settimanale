package matteomoscardini.ultimoprogettosettimanale.controllers;

import matteomoscardini.ultimoprogettosettimanale.exceptions.BadRequestException;
import matteomoscardini.ultimoprogettosettimanale.payloads.NewUserPAyload;
import matteomoscardini.ultimoprogettosettimanale.payloads.NewUserResponse;
import matteomoscardini.ultimoprogettosettimanale.payloads.UserLoginPayload;
import matteomoscardini.ultimoprogettosettimanale.payloads.UserLoginResponsePayload;
import matteomoscardini.ultimoprogettosettimanale.services.AuthService;
import matteomoscardini.ultimoprogettosettimanale.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserLoginResponsePayload login(@RequestBody UserLoginPayload body){
        return new UserLoginResponsePayload(this.authService.authenticateUserAndGenerateToken(body));

    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponse saveUser(@RequestBody @Validated NewUserPAyload body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return new NewUserResponse(this.userService.save(body).getId());
    }
}

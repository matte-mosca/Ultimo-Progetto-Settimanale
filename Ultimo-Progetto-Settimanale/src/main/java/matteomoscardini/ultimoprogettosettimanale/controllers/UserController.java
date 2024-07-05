package matteomoscardini.ultimoprogettosettimanale.controllers;

import matteomoscardini.ultimoprogettosettimanale.entities.User;
import matteomoscardini.ultimoprogettosettimanale.exceptions.BadRequestException;
import matteomoscardini.ultimoprogettosettimanale.payloads.NewUserPAyload;
import matteomoscardini.ultimoprogettosettimanale.payloads.NewUserResponse;
import matteomoscardini.ultimoprogettosettimanale.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public Page<User> getAllUsers (@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortBy){
        return this.userService.getAllUsers(page, size, sortBy);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    private NewUserResponse saveNewUser(@RequestBody @Validated
                                        NewUserPAyload body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return new NewUserResponse(userService.save(body).getId());
    }

    @PutMapping("/{userID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findUserByIdAndDelete(@PathVariable UUID id){
        this.userService.findUserByIdAndDelete(id);
    }
}

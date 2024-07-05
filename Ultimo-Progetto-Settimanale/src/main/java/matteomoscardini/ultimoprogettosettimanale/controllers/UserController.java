package matteomoscardini.ultimoprogettosettimanale.controllers;

import matteomoscardini.ultimoprogettosettimanale.entities.Event;
import matteomoscardini.ultimoprogettosettimanale.entities.User;
import matteomoscardini.ultimoprogettosettimanale.exceptions.BadRequestException;
import matteomoscardini.ultimoprogettosettimanale.payloads.NewUserPAyload;
import matteomoscardini.ultimoprogettosettimanale.payloads.NewUserResponse;
import matteomoscardini.ultimoprogettosettimanale.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
      public NewUserResponse saveNewUser(@RequestBody @Validated
                                        NewUserPAyload body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return new NewUserResponse(userService.save(body).getId());
    }
    @GetMapping("/{userId}")
    public User getSingleUser(@PathVariable UUID userId){
        return this.userService.findById(userId);
    }


    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('EVENT_PLANNER')")
    public User findUserByIdAndUpdate(@PathVariable UUID userId, @RequestBody User body){
        return this.userService.findUserByIdAndUpdate(userId, body);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('EVENT_PLANNER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findUserByIdAndDelete(@PathVariable UUID userId){
        this.userService.findUserByIdAndDelete(userId);
    }


    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentAuthenticatedUser){
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody User updatedUser){
        return this.userService.findUserByIdAndUpdate(currentAuthenticatedUser.getId(), updatedUser);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal User currentAuthenticatedUser){
        this.userService.findUserByIdAndDelete(currentAuthenticatedUser.getId());
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addEvent(@PathVariable UUID userId, @RequestBody Event event){
        this.userService.addEvent(userId, event);
    }

}

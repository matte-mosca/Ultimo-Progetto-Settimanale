package matteomoscardini.ultimoprogettosettimanale.services;

import matteomoscardini.ultimoprogettosettimanale.entities.Event;
import matteomoscardini.ultimoprogettosettimanale.entities.User;
import matteomoscardini.ultimoprogettosettimanale.exceptions.BadRequestException;
import matteomoscardini.ultimoprogettosettimanale.exceptions.NotFoundException;
import matteomoscardini.ultimoprogettosettimanale.payloads.NewUserPAyload;
import matteomoscardini.ultimoprogettosettimanale.repositories.EventDAO;
import matteomoscardini.ultimoprogettosettimanale.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    EventDAO eventDAO;

    @Autowired
    PasswordEncoder bCrypt;


    public Page<User> getAllUsers(int page, int size, String sortBy) {
        if (size > 100 ) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.userDAO.findAll(pageable);
    }

    public User save(NewUserPAyload body) throws BadRequestException {
        this.userDAO.findByEmail(body.email()).ifPresent(
                user -> {
                    throw new BadRequestException("The email "+user.getEmail()+ " is already used. Choose another email");
                }
        );
        User newUser = new User(body.name(), body.surname(),  body.email(), bCrypt.encode(body.password()));
        return userDAO.save(newUser);

    }
    public User findById(UUID userId){
        return this.userDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User findUserByIdAndUpdate(UUID id, User updatedUser){
        Optional<User> optionalUser = userDAO.findById(id);
        if (optionalUser.isPresent()){
            User found = optionalUser.get();
            found.setName(updatedUser.getName());
            found.setSurname(updatedUser.getSurname());
            found.setEmail(updatedUser.getEmail());

            return this.userDAO.save(found);
        }else {
            throw new NotFoundException(id);
        }


    }

    public void findUserByIdAndDelete(UUID id){
        Optional<User> optionalUser = userDAO.findById(id);
        if (optionalUser.isPresent()){
            User found = optionalUser.get();
            this.userDAO.delete(found);
        }else{
            throw new NotFoundException(id);
        }
    }

    public User findByEmail (String email) {
        return userDAO.findByEmail(email).orElseThrow(()-> new NotFoundException("There are no users with email: " + email));
    }

    public void addEvent(UUID userId, Event event){
        User user = userDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));

        {

            event.getUsers().add(user);
            user.getEvents().add(event);
            userDAO.save(user);
            eventDAO.save(event);

        }
    }
}

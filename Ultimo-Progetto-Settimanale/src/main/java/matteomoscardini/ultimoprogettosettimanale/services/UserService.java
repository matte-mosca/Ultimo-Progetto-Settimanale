package matteomoscardini.ultimoprogettosettimanale.services;

import matteomoscardini.ultimoprogettosettimanale.entities.Event;
import matteomoscardini.ultimoprogettosettimanale.entities.User;
import matteomoscardini.ultimoprogettosettimanale.exceptions.BadRequestException;
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

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    EventDAO eventDAO;


    public Page<User> getAllUsers(int page, int size, String sortBy) {
        if (size > 100 ) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.userDAO.findAll(pageable);
    }

    public User save(NewUserPAyload body) throws BadRequestException {
        this.userDAO.findByeMail(body.email()).ifPresent(
                user -> {
                    throw new BadRequestException("The email "+user.getEmail()+ " is already used. Choose another email");
                }
        );
        User newUser = new User(body.name(), body.surname(),  body.email(), body.password());
        return userDAO.save(newUser);
    }
}

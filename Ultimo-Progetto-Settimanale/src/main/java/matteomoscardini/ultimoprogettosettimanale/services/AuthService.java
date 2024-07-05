package matteomoscardini.ultimoprogettosettimanale.services;

import matteomoscardini.ultimoprogettosettimanale.entities.User;
import matteomoscardini.ultimoprogettosettimanale.exceptions.UnauthorizedException;
import matteomoscardini.ultimoprogettosettimanale.payloads.UserLoginPayload;
import matteomoscardini.ultimoprogettosettimanale.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUserAndGenerateToken(UserLoginPayload body){
        User user = this.userService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), user.getPassword())){
            return jwtTools.createToken(user);
        }else{
            throw new UnauthorizedException("Invalid credentials! Please log in again!");
        }
    }
}

package i.ru.authorizationservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorizationController {
    AuthorizationService service;

    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }

    @GetMapping("/authorize")
    public List<Authorities> getAuthorities(@RequestParam("user") String user, @RequestParam("password") String password) {
        return service.getAuthorities(user, password);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    @ExceptionHandler(InvalidCredentials.class)
    public String handleInvalidCredentials(InvalidCredentials e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED) //401
    @ExceptionHandler(UnauthorizedUser.class)
    public String handUnauthorizedUser(UnauthorizedUser e) {
        return e.getMessage();
    }
}

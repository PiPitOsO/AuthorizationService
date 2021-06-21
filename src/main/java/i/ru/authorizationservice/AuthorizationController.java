package i.ru.authorizationservice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorizationController {
    AuthorizationService service;

    @GetMapping("/authorize")
    public List<Authorities> getAuthorities(@RequestParam("user") String user, @RequestParam("password") String password) {
        System.out.println("controller " + user + " " + password); // проверка
        return service.getAuthorities(user, password);
    }

    @ExceptionHandler(NullPointerException.class)
    public void handleNullPointerException(NullPointerException e) {
    }
}
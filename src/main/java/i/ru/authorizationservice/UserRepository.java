package i.ru.authorizationservice;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static i.ru.authorizationservice.Authorities.*;

@Repository
public class UserRepository {
    public List<Authorities> getUserAuthorities(String user, String password) throws InvalidCredentials {
        HashMap<String, String> users = new HashMap<>(); //создадим пользователей
        users.put("Ivan", "1234");
        users.put("Petr", "2345");
        users.put("Anna", "3456");
        users.put("Kirill", "4567");
        users.put("Oleg", "5678");
        users.put("Vlad", "6789");
        users.put("Pasha", "7890");

        ArrayList<Authorities> admin = new ArrayList<>(); //раскидаем права на админа
        admin.add(READ);
        admin.add(WRITE);
        admin.add(DELETE);

        ArrayList<Authorities> employee = new ArrayList<>(); //раскидаем права на сотрудника
        employee.add(READ);
        employee.add(WRITE);

        ArrayList<Authorities> guest = new ArrayList<>(); //раскидаем права на гостя
        guest.add(READ);

        ArrayList<Authorities> unwanted = new ArrayList<>(); //без доступа

        HashMap<String, ArrayList<Authorities>> access = new HashMap<>(); //присвоим пользователю перечень прав
        access.put("Ivan", admin);
        access.put("Petr", admin);
        access.put("Anna", employee);
        access.put("Kirill", employee);
        access.put("Oleg", guest);
        access.put("Vlad", unwanted);
        access.put("Pasha", guest);

        if (users.get(user).equals(password)) { //проверим пароль пользователя по логину с паролям в запросе
            return access.get(user); //отправляем лист прав
        } else {
            throw new InvalidCredentials("Invalid password"); //при неверном пароле выбрасываем исключение и ловим его
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    @ExceptionHandler(InvalidCredentials.class)
    public void handleInvalidCredentials(InvalidCredentials e) {
    }
}
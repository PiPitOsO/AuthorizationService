package i.ru.authorizationservice;

import org.springframework.stereotype.Repository;

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
        access.put("InvalidPassword", unwanted);
        access.put("Ivan", admin);
        access.put("Petr", admin);
        access.put("Anna", employee);
        access.put("Kirill", employee);
        access.put("Oleg", guest);
        access.put("Vlad", guest);
        access.put("Pasha", guest);

        if (users.get(user) == null) { //проверяем есть ли такой пользователь
            access.put(user, unwanted); //добавляем без прав
            return access.get(user); //отдаём
        }

        if (users.get(user).equals(password)) { //проверим пароль пользователя по логину с паролям в запросе
            return access.get(user); //если нашёл и пароль совпал отправляем
        } else {
            throw new UnauthorizedUser("Invalid user password: " + user); //исключение при не верном пароле
        }
    }
}

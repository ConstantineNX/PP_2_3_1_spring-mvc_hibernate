import config.DataBaseConfig;
import model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.UserDaoService;
import java.sql.SQLException;
import java.util.List;

public class MainTest {
    public static void main(String[] args) throws SQLException {

        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(DataBaseConfig.class);

        UserDaoService userDaoService = annotationConfigApplicationContext.getBean(UserDaoService.class);

        userDaoService.saveUser(new User("John", "Doil", (byte) 32, "Moscow", "users1@mail.ru","123456789"));
        userDaoService.saveUser(new User("Don", "Digidon", (byte) 62, "Kazan", "users2@mail.ru","12345678910"));
        userDaoService.saveUser(new User("Din", "Digidon", (byte) 52, "Grenobil", "users3@mail.ru","12345678911"));

        List<User> users = userDaoService.findAll();
        for (User user : users) {
            System.out.println("Id: " + user.getId());
            System.out.println("Имя: " + user.getFirstName());
            System.out.println("Фамилия: " + user.getLastName());
            System.out.println("Возраст: " + user.getAge());
            System.out.println("Город: " + user.getCity());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Телефон: " + user.getPhone());
        }

        annotationConfigApplicationContext.close();
    }


}

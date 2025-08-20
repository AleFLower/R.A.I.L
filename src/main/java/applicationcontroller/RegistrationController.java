package applicationcontroller;

import bean.RegistrationBean;
import dao.RegistrationDao;
import exception.PasswordReadException;
import exception.UserAlreadyExistsException;
import factory.DaoFactory;
import factory.TypeOfPersistence;

import java.io.IOException;
import java.sql.SQLException;

public class RegistrationController {
    private String email;
    private String password;
    private String username;


    public RegistrationController(RegistrationBean bean)  {
        email= bean.getEmail();
        username=bean.getUsername();
        password= bean.getPassword();
    }
    public void registrateUser(TypeOfPersistence typeOfPersistence) throws SQLException, UserAlreadyExistsException, PasswordReadException, IOException {

        DaoFactory factory = DaoFactory.getFactory(typeOfPersistence);
        RegistrationDao registrationDao = factory.getRegistrationDao();

        if(!registrationDao.registrateUser(username, email, password)){

            throw new UserAlreadyExistsException("The username or the email are linked\nto another account");
        }
    }
}

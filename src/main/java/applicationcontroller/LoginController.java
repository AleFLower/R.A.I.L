package applicationcontroller;

import bean.LoginBean;
import dao.LoginDao;
import exception.PasswordReadException;
import exception.UserNotFoundException;
import factory.DaoFactory;
import factory.TypeOfPersistence;

import java.sql.SQLException;

public class LoginController {

    private final String email;
    private final String password;

    public LoginController(LoginBean beanLogin) {
        this.email=beanLogin.getEmail();
        this.password=beanLogin.getPassword();
        new AccountController();
    }
    public void login(TypeOfPersistence typeOfPersistence) throws SQLException, UserNotFoundException, PasswordReadException {

        DaoFactory factory = DaoFactory.getFactory(typeOfPersistence);
        LoginDao loginDao = factory.getLoginDao();
        if (loginDao.verifyAccount(email, password)) {
            return;
        }

        throw new UserNotFoundException("No user found with these credentials");

    }

}

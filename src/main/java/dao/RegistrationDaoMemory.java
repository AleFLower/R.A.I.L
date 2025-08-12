package dao;

import model.Role;


public class RegistrationDaoMemory implements RegistrationDao {

    @Override
    public boolean registrateUser(String username, String email, String password)  {
        if (verifyUserExistance(username, email)) {
            // Salva nel "database in memoria" condiviso
            LoggedUsers.usernames.put(email, username);
            LoggedUsers.users.put(email, password);
            LoggedUsers.userRoles.put(email, Role.USER); //di deafult, ho un admin gia nel sistema, quindi tutti gli altri sono user
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean verifyUserExistance(String username, String email)  {

        //non so se lanciare, forse deve ritornare solo false per come è stato progettato?
        return !LoggedUsers.usernames.containsValue(username) || !LoggedUsers.users.containsKey(email);
    }
}

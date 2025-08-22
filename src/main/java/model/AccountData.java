package model;

import java.io.Serializable;

public class AccountData implements Serializable {
    private String email;
    private String username;
    private String password;
    private String userCode;
    private Role role;

    public AccountData(String email, String username, String password, String userCode, Role role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.userCode = userCode;
        this.role = role;
    }

    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getUserCode() { return userCode; }
    public Role getRole() { return role; }
}

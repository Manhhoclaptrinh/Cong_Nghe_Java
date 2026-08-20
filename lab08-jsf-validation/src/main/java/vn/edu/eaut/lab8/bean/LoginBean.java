package vn.edu.eaut.lab8.bean;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;

    public String login() {

        if ("admin".equals(username)
                && "123456".equals(password)) {

            return "index?faces-redirect=true";
        }

        FacesContext.getCurrentInstance()
                .addMessage(
                    null,
                    new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Đăng nhập thất bại",
                        "Sai tài khoản hoặc mật khẩu"
                    )
                );

        return null;
    }

    public String logout() {

        username = null;
        password = null;

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();

        return "login?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
package com.cetuer.smartparkinglot.data.bean;

/**
 * app登录注册
 *
 * @author Cetuer
 * @date 2021/12/17 9:57
 */
public class MemberLogin {
    public MemberLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String username;

    private String password;

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

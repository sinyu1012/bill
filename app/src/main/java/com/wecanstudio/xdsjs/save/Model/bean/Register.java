package com.wecanstudio.xdsjs.save.Model.bean;

/**
 * Created by xdsjs on 2015/11/20.
 */
public class Register {

    /**
     * username : wer
     * password : 123456
     * password_sec : 123456
     */

    private String username;
    private String password;
    private String password_sec;

    public Register(String username, String password, String password_sec) {
        this.username = username;
        this.password = password;
        this.password_sec = password_sec;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassword_sec(String password_sec) {
        this.password_sec = password_sec;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPassword_sec() {
        return password_sec;
    }
}

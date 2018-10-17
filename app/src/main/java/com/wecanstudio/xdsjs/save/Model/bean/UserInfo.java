package com.wecanstudio.xdsjs.save.Model.bean;

/**
 * Created by xdsjs on 2015/11/20.
 */
public class UserInfo {

    /**
     * imgurl : www.123.com
     * password :
     * id : 2
     * password_sec : 123
     * username : wer1234
     * status : 1
     */

    private String imgurl;
    private String password;
    private int id;
    private String password_sec;
    private String username;
    private String status;

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword_sec(String password_sec) {
        this.password_sec = password_sec;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getPassword_sec() {
        return password_sec;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

}

package com.zxxapp.mall.maintenance.bean.account;

import com.example.http.ParamNames;

import java.io.Serializable;

/**
 * Created by Thinten on 2017-12-14
 * www.thinten.com
 * 9486@163.com.
 */

public class User  implements Serializable {


    @ParamNames("UserName")
    public String UserName;
    @ParamNames("Password")
    public String Password;
    @ParamNames("UserID")
    public Integer UserID;
    @ParamNames("NickName")
    public String NickName;
    @ParamNames("GroupName")
    public String GroupName;
    @ParamNames("phone")
    public String phone;
    @ParamNames("email")
    public String email;
    @ParamNames("token")
    public String token;
    @ParamNames("avatarImg")
    public String avatarImg;
    @ParamNames("inviter")
    public String inviter;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Integer getUserID() {
        return UserID;
    }

    public void setUserID(Integer userID) {
        UserID = userID;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }


}

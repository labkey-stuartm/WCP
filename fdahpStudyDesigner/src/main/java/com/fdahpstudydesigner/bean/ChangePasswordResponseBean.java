package com.fdahpstudydesigner.bean;

import com.fdahpstudydesigner.bo.UserBO;

public class ChangePasswordResponseBean {
  private String message;
  private UserBO user;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public UserBO getUser() {
    return user;
  }

  public void setUser(UserBO user) {
    this.user = user;
  }
}

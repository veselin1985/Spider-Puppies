package com.spidermanteam.spiderpuppies.security.models;

import com.spidermanteam.spiderpuppies.models.User;

public class JwtUser {
  private Long id;
  private String username;
  private String password;
  private String role;

  public JwtUser() {

  }

  public JwtUser(User user, String role) {
    this.setId(user.getId());
    this.setUsername(user.getUsername());
    this.setPassword(user.getPassword());
    this.setRole(role);

  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
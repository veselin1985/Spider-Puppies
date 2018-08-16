package com.spidermanteam.spiderpuppies.models;


import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authorities {

    @Column(name = "username")
    private String username;


    @Column(name = "authority")
    private String authority;

    public Authorities() {

    }

    public Authorities(String role) {
        this.authority = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}

package com.derivedmed.proj.model;

import com.derivedmed.proj.util.annotations.Column;
import com.derivedmed.proj.util.annotations.Model;

import java.util.Objects;

@Model
public class User {

    @Column(name = "user_id")
    private int id;

    @Column(name = "role_id")
    private int role_id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "rating")
    private int rating;

    private Role role;

    public User() {

    }

    public User(int role_id, String login, String password, int rating) {
        this.role_id = role_id;
        this.login = login;
        this.password = password;
        this.rating = rating;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                role_id == user.role_id &&
                rating == user.rating &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, role_id, login, password, rating);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role_id=" + role_id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", rating=" + rating +
                ", role=" + role +
                '}';
    }
}

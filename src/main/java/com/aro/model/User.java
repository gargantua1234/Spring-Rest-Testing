package com.aro.model;

import java.io.Serializable;

/**
 * Created by Arek on 16.02.2017.
 */
public class User implements Serializable{
    private int id;
    private String name;
    private String lastName;

    public User(){}

    public User(int id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public User(String name, String lastName){
        this.name = name;
        this.lastName = lastName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='"+lastName+"'"+
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if(object == null)
            return false;
        if(object == this)
            return true;
        if(object instanceof  User){
            User user = (User) object;
            if(user.id == this.id && user.name.equals(this.name)
                    && user.lastName.equals(this.lastName))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.id)
                + this.name.hashCode()
                + this.lastName.hashCode();
    }
}

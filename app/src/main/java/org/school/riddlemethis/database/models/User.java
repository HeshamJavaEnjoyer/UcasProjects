package org.school.riddlemethis.database.models;


public class User {
    private String user_name;
    private String  email;
    private String birthdate;
    private String gender;
    private String country;

    //constructor

    public User() {
    }

    public User(String user_name, String email, String birthdate, String gender, String country) {
        this.user_name = user_name;
        this.email = email;
        this.birthdate = birthdate;
        this.gender = gender;
        this.country = country;
    }


    //GetterAndSetter

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}

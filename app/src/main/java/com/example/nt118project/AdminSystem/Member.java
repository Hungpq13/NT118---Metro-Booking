
package com.example.nt118project.AdminSystem;
public class Member {
    private String name;
    private String email;

    private String password;

    public Member(String name, String email , String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}


package com.example.nt118project.AdminSystem;
public class Member {
    private String name;
    private String DoB;
    private String sex;
    private String email;
    private String id;

    private String password;

    public Member(String id, String name,  String email , String DoB, String sex, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
        this.DoB = DoB ;
        this.sex = sex ;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public String getDoB() {
        return DoB;
    }
    public String getSex() {
        return sex;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }
}

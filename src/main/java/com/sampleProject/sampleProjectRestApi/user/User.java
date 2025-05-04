package com.sampleProject.sampleProjectRestApi.user;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "storeUsers")  // FIXED: Explicit table name
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
   private String userName;
   private  String password;

    private String email;
   private LocalDate date;
    @Column(name = "role")
   private String role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();



   public User(){

   }
    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
    public User(String userName, String password,String email,String role,LocalDate date,List<Order> orders){
        this.userName = userName;
        this.password = password;
        this.email=email;
        this.date = date;
        this.role = role;
        this.orders = orders;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
       this.email = email;
    }

    public void setCreatedAt(LocalDate date) {
       this.date = date;
    }

    public String getEmail(){
       return email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    //    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", userName='" + userName + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }
}


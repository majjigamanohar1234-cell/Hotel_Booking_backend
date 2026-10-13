package com.stayease.user;
import jakarta.persistence.*;
@Entity @Table(name="app_users")
public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String name; @Column(unique=true,nullable=false) private String email; @Column(nullable=false) private String passwordHash;
 public AppUser(){} public AppUser(String name,String email,String passwordHash){this.name=name;this.email=email;this.passwordHash=passwordHash;}
 public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public String getPasswordHash(){return passwordHash;}
}
package com.manibhadra.user_auth_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private  String emailId;
    private String mobileNumber;
    private String password;

    private String name;
    private String gender;
    private String lookingFor;
    private int age;
    private int ageRangeFrom;
    private int ageRangeTo;
    private String address;
    private String work;
    private String moreInfo;
    private String lifeStyle;
    private String passions;
}

package com.manibhadra.user_auth_system.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinDTO {

    @Id
    private String emailId;
    @Column(unique = true)
    private String username;
    private String mobileNumber;
    private String password;
    private String credentials;
    private String token;
}

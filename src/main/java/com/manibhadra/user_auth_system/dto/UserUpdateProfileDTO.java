package com.manibhadra.user_auth_system.dto;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserUpdateProfileDTO {

    @Id
    private  String emailId;
    private String mobileNumber;
        private String name;
        private int age;
        private int ageRangeFrom;
        private int ageRangeTo;
        private String gender;
        private String lookingFor;
        private String address;
        private String work;
        private String moreInfo;
        private String lifeStyle;
        private String passions;
}

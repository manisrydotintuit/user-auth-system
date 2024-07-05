package com.manibhadra.user_auth_system.controller;

import com.manibhadra.user_auth_system.constant.MessageConstant;
import com.manibhadra.user_auth_system.dto.UserJoinDTO;
import com.manibhadra.user_auth_system.dto.UserUpdateProfileDTO;
import com.manibhadra.user_auth_system.exception.CustomExceptions;
import com.manibhadra.user_auth_system.model.ApiResponse;
import com.manibhadra.user_auth_system.model.AuthResponse;
import com.manibhadra.user_auth_system.service.JwtService;
import com.manibhadra.user_auth_system.service.UserServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserServiceDTO userServiceDTO;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse> userJoin(@RequestBody UserJoinDTO userJoinDTO) {

        UserJoinDTO savedRecord = userServiceDTO.saveUserCredentials(userJoinDTO);
        if (savedRecord != null) {
            return ResponseEntity.ok().body(new ApiResponse<>(savedRecord, true, HttpStatus.OK.value(), "Joined successfully", null));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, HttpStatus.BAD_REQUEST.value(), "Failed", "some error occurred"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> userLogin(@RequestBody UserJoinDTO userJoinDTO) {
        UserJoinDTO validateRecord = userServiceDTO.checkLoginCredentials(userJoinDTO);
        if (validateRecord != null) {
            String token = jwtService.generateToken(validateRecord.getCredentials());
            validateRecord.setToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(validateRecord, true, HttpStatus.OK.value(), "Login successful", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(null, false, HttpStatus.BAD_REQUEST.value(), "Login failed", "Invalid credentials"));
        }
    }


    @PostMapping("/addMoreUserDetails")
    public ResponseEntity<ApiResponse> addMoreUserDetails(@RequestHeader("Authorization") String token, @RequestBody UserUpdateProfileDTO userUpdateProfileDTO) {

        String email = jwtService.extractUserName(token.replace("Bearer ", ""));
        if (!email.equals(userUpdateProfileDTO.getEmailId())) {
            throw new CustomExceptions(MessageConstant.UNAUTHORIZED);
        }
        UserUpdateProfileDTO addedDetails = userServiceDTO.addMoreUserDetails(userUpdateProfileDTO);
        if (addedDetails != null) {
            return ResponseEntity.ok().body(new ApiResponse<>(addedDetails, true, HttpStatus.OK.value(), "Updated successfully", null));
        } else {
            return ResponseEntity.ok().body(new ApiResponse<>(null, false, HttpStatus.BAD_REQUEST.value(), "failed", "some error occurred"));
        }
    }


}



package com.manibhadra.user_auth_system.service;

import com.manibhadra.user_auth_system.dto.UserJoinDTO;
import com.manibhadra.user_auth_system.dto.UserUpdateProfileDTO;

public interface UserServiceDTO {
     UserJoinDTO saveUserCredentials(UserJoinDTO userJoinDTO);

     UserJoinDTO checkLoginCredentials(UserJoinDTO userJoinDTO);

     UserUpdateProfileDTO addMoreUserDetails( UserUpdateProfileDTO userUpdateProfileDTO);
}

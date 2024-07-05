package com.manibhadra.user_auth_system.service;

import com.manibhadra.user_auth_system.constant.MessageConstant;
import com.manibhadra.user_auth_system.dto.UserJoinDTO;
import com.manibhadra.user_auth_system.dto.UserUpdateProfileDTO;
import com.manibhadra.user_auth_system.exception.CustomExceptions;
import com.manibhadra.user_auth_system.model.User;
import com.manibhadra.user_auth_system.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.manibhadra.user_auth_system.regexPattern.RegexPattern.*;

@Service
public class UserServiceDTOImpl implements UserServiceDTO {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserJoinDTO saveUserCredentials(UserJoinDTO userJoinDTO) {
        validateUserCredentialsForJoin(userJoinDTO);

        if (userRepository.existsByUsername(userJoinDTO.getUsername())) {
            throw new CustomExceptions(MessageConstant.USERNAME_ALREADY_EXISTED);
        }
        if (userRepository.existsByEmailId(userJoinDTO.getEmailId())) {
            throw new CustomExceptions(MessageConstant.EMAIL_ALREADY_EXIST);
        }
        if (userRepository.existsByMobileNumber(userJoinDTO.getMobileNumber())) {
            throw new CustomExceptions(MessageConstant.MOBILE_NUMBER_ALREADY_EXIST);
        }

        if (!userJoinDTO.getUsername().matches(USERNAME_REGEX)) {
            throw new CustomExceptions(MessageConstant.INVALID_USERNAME_FORMAT);
        }

        if (!userJoinDTO.getEmailId().matches(EMAIL_REGEX)) {
            throw new CustomExceptions(MessageConstant.INVALID_EMAIL_FORMAT);
        }

        if (!userJoinDTO.getMobileNumber().matches(MOBILE_REGEX)) {
            throw new CustomExceptions(MessageConstant.INVALID_MOBILE_NUMBER_FORMAT);
        }
        if (!userJoinDTO.getPassword().matches(PASSWORD_REGEX)) {
            throw new CustomExceptions(MessageConstant.INVALID_PASSWORD_FORMAT);
        }

        User user = convertToJoinDto(userJoinDTO);
        userRepository.save(user);

        return userJoinDTO;
    }

    private void validateUserCredentialsForJoin(UserJoinDTO userJoinDTO) {
        if (userJoinDTO.getEmailId() == null || userJoinDTO.getEmailId().isEmpty()) {
            throw new CustomExceptions(MessageConstant.EMAIL_NULL_ERROR);
        }
        if (userJoinDTO.getUsername() == null || userJoinDTO.getUsername().isEmpty()) {
            throw new CustomExceptions(MessageConstant.USERNAME_NULL_ERROR);
        }
        if (userJoinDTO.getMobileNumber() == null || userJoinDTO.getMobileNumber().isEmpty()) {
            throw new CustomExceptions(MessageConstant.MOBILE_NUMBER_NULL_ERROR);
        }
        if (userJoinDTO.getPassword() == null || userJoinDTO.getPassword().isEmpty()) {
            throw new CustomExceptions(MessageConstant.PASSWORD_NULL_ERROR);
        }
    }

    private User convertToJoinDto(UserJoinDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmailId(dto.getEmailId());
        user.setMobileNumber(dto.getMobileNumber());
        user.setPassword(dto.getPassword());
        return user;
    }

    @Override
    public UserJoinDTO checkLoginCredentials(UserJoinDTO userJoinDTO) {
        validateUserCredentialsForLogin(userJoinDTO);

        User user = null;
        String identifier = null;

        if (userJoinDTO.getEmailId() != null && !userJoinDTO.getEmailId().isEmpty()) {
            user = userRepository.findByEmailId(userJoinDTO.getEmailId());
            identifier = userJoinDTO.getEmailId();
        } else if (userJoinDTO.getMobileNumber() != null && !userJoinDTO.getMobileNumber().isEmpty()) {
            user = userRepository.findByMobileNumber(userJoinDTO.getMobileNumber());
            identifier = userJoinDTO.getMobileNumber();
        }

        if (user == null) {
            throw new CustomExceptions(MessageConstant.USER_NOT_FOUND);
        }

        if (!user.getPassword().equals(userJoinDTO.getPassword())) {
            throw new CustomExceptions(MessageConstant.WRONG_PASSWORD);
        }


        userJoinDTO.setCredentials(identifier);
        return userJoinDTO;
    }

    private void validateUserCredentialsForLogin(UserJoinDTO userJoinDTO) {
        if ((userJoinDTO.getEmailId() == null || userJoinDTO.getEmailId().isEmpty())
                && (userJoinDTO.getMobileNumber() == null || userJoinDTO.getMobileNumber().isEmpty())) {
            throw new CustomExceptions(MessageConstant.EMAIL_OR_MOBILE_REQUIRED);
        }

        if (userJoinDTO.getPassword() == null || userJoinDTO.getPassword().isEmpty()) {
            throw new CustomExceptions(MessageConstant.PASSWORD_NULL_ERROR);
        }
    }

    @Override
    public UserUpdateProfileDTO addMoreUserDetails(UserUpdateProfileDTO userUpdateProfileDTO) {
        User dbUser = userRepository.findByEmailId(userUpdateProfileDTO.getEmailId());
        if (dbUser == null) {
            throw new CustomExceptions(MessageConstant.USER_NOT_FOUND);
        }
        validateAndUpdate(dbUser, userUpdateProfileDTO);
        userRepository.save(dbUser);
        return convertToDTO(dbUser);
    }

    private void validateAndUpdate(User dbUser, UserUpdateProfileDTO userUpdateProfileDTO) {
        if (userUpdateProfileDTO.getName() != null && !userUpdateProfileDTO.getName().isEmpty()) {
            dbUser.setName(userUpdateProfileDTO.getName());
        }

        if (userUpdateProfileDTO.getAge() != 0) {
            dbUser.setAge(userUpdateProfileDTO.getAge());
        }

        if (userUpdateProfileDTO.getAgeRangeFrom() != 0) {
            dbUser.setAgeRangeFrom(userUpdateProfileDTO.getAgeRangeFrom());
        }
        if (userUpdateProfileDTO.getAgeRangeTo() != 0) {
            dbUser.setAgeRangeTo(userUpdateProfileDTO.getAgeRangeTo());
        }
        if (userUpdateProfileDTO.getGender() != null && !userUpdateProfileDTO.getGender().isEmpty()) {
            dbUser.setGender(userUpdateProfileDTO.getGender());
        }

        if (userUpdateProfileDTO.getAddress() != null && !userUpdateProfileDTO.getAddress().isEmpty()) {
            dbUser.setAddress(userUpdateProfileDTO.getAddress());
        }

        if (userUpdateProfileDTO.getWork() != null && !userUpdateProfileDTO.getWork().isEmpty()) {
            dbUser.setWork(userUpdateProfileDTO.getWork());
        }

        if (userUpdateProfileDTO.getLookingFor() != null && !userUpdateProfileDTO.getLookingFor().isEmpty()) {
            dbUser.setLookingFor(userUpdateProfileDTO.getLookingFor());
        }
        if (userUpdateProfileDTO.getMoreInfo() != null && !userUpdateProfileDTO.getMoreInfo().isEmpty()) {
            dbUser.setMoreInfo(userUpdateProfileDTO.getMoreInfo());
        }

        if (userUpdateProfileDTO.getLifeStyle() != null && !userUpdateProfileDTO.getLifeStyle().isEmpty()) {
            dbUser.setLifeStyle(userUpdateProfileDTO.getLifeStyle());
        }

        if (userUpdateProfileDTO.getPassions() != null && !userUpdateProfileDTO.getPassions().isEmpty()) {
            dbUser.setPassions(userUpdateProfileDTO.getPassions());
        }
    }

    private UserUpdateProfileDTO convertToDTO(User user) {
        UserUpdateProfileDTO dto = new UserUpdateProfileDTO();
        dto.setEmailId(user.getEmailId());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setName(user.getName());
        dto.setAge(user.getAge());
        dto.setGender(user.getGender());
        dto.setLookingFor(user.getLookingFor());
        dto.setAgeRangeFrom(user.getAgeRangeFrom());
        dto.setAgeRangeTo(user.getAgeRangeTo());
        dto.setAddress(user.getAddress());
        dto.setWork(user.getWork());
        dto.setMoreInfo(user.getMoreInfo());
        dto.setLifeStyle(user.getLifeStyle());
        dto.setPassions(user.getPassions());
        return dto;
    }
}
package com.manibhadra.user_auth_system.service;

import com.manibhadra.user_auth_system.constant.MessageConstant;
import com.manibhadra.user_auth_system.exception.CustomExceptions;
import com.manibhadra.user_auth_system.model.User;
import com.manibhadra.user_auth_system.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.manibhadra.user_auth_system.regexPattern.RegexPattern.MOBILE_REGEX;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = null;

        if (identifier.matches(MOBILE_REGEX)) {
            user = userRepository.findByMobileNumber(identifier);
        } else {
            user = userRepository.findByEmailId(identifier);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found with identifier: " + identifier);
        }

        return new org.springframework.security.core.userdetails.User(
                identifier,
                user.getPassword(),
                Collections.emptyList()
        );
    }
}

package com.manibhadra.user_auth_system.repo;

import com.manibhadra.user_auth_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailId(String emailId);

    boolean existsByEmailId(String emailId);

    boolean existsByMobileNumber(String mobileNumber);

    boolean existsByUsername(String username);

    User findByMobileNumber(String mobileNumber);
}

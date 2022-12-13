package com.pwang.projoect.domain.user.repository;

import com.pwang.projoect.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByuserIdAndPassword(String userId, String password);
}

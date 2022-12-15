package com.pwang.projoect.domain.user.repository;

import com.pwang.projoect.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("select u from User u where u.userId = :userId")
    User duplicationUserId(@Param("userId") String UserId);
}

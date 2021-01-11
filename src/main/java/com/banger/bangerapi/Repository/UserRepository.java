package com.banger.bangerapi.Repository;

import com.banger.bangerapi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {
    User findByUserName(String username);
    User findByEmail(String email);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    @Query("select user from User user where user.status='active' and user.type='customer'")
    List<User> findActiveUsers();
    @Modifying
    @Query("UPDATE  User  user SET  user.status='blacklist' where user.Id=:userId")
    void blacklist(@Param("userId") Integer id);
    @Modifying
    @Query("UPDATE  User  user SET  user.status='active' where user.Id=:userId")
    void activate(@Param("userId") Integer id);

    @Query("select user from User user where user.status='blacklist'")
    List<User> findBlacklistUsers();
}

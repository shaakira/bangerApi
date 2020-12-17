package com.banger.bangerapi.Repository;

import com.banger.bangerapi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {
    User findByUserName(String username);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);

}

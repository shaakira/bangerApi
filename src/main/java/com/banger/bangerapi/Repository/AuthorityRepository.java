package com.banger.bangerapi.Repository;

import com.banger.bangerapi.Models.Authority;
import com.banger.bangerapi.Models.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,Integer> {

    Authority findByName(AuthorityType name);
}

package com.banger.bangerapi.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailServices {
    UserDetails loadUserByEmail(String var1) throws UsernameNotFoundException;
}

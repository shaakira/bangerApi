package com.banger.bangerapi.Controller;

import com.banger.bangerapi.Models.Jwt.JwtRequest;
import com.banger.bangerapi.Models.Jwt.JwtResponse;
import com.banger.bangerapi.Models.User;
import com.banger.bangerapi.Security.BangerUserDetails;
import com.banger.bangerapi.Security.JwtTokenUtil;
import com.banger.bangerapi.Security.JwtUserDetailsService;
import com.banger.bangerapi.Service.UserService;
import jdk.jshell.spi.ExecutionControlProvider;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println(userDetails);
        if (userDetails == null) {
            System.out.println("asdasdsa");
        }
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername(), roles));
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) throws Exception {

        return userService.registerUser(user);

    }

    private void authenticate(String username, String password) throws Exception {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("The password ciphertext in the system is a blank string");

//
//            throw new ResponseStatusException(
//
//                    HttpStatus.BAD_REQUEST, "Foo Not Found", e);


        }
    }
}
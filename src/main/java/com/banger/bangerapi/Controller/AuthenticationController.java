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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/GooglesignUp")
    public ResponseEntity<?> registerUserGoogle( @RequestBody User registerUser) {
        ResponseEntity response= userService.googleLogin(registerUser);
        User loggedInUser = null;
        // String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(registerUser.getUserName());
            if (!passwordEncoder.matches("default", userDetails.getPassword())) {
                throw new BadCredentialsException("Bad Credentials");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            loggedInUser   = ((BangerUserDetails) authentication.getPrincipal()).getUserDetails();

        } catch (AuthenticationException e) {

        }
        if(loggedInUser!=null) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(registerUser.getUserName());
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername(), roles, loggedInUser.getStatus()));
        }
        else
        {
            return null;
        }
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        final String token = jwtTokenUtil.generateToken(userDetails);
        User user=userService.getUser(authenticationRequest.getUserName());
        System.out.println(userDetails);
        if (userDetails == null) {

        }
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername(), roles,user.getStatus()));
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) throws Exception {

      userService.registerUser(user);
        authenticate(user.getUserName(), user.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        final String token = jwtTokenUtil.generateToken(userDetails);
        User user2=userService.getUser(user.getUserName());
        System.out.println(userDetails);
        if (userDetails == null) {

        }
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername(), roles,user2.getStatus()));


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
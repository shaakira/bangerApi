package com.banger.bangerapi.Controller;

import com.banger.bangerapi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUser/{username}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username) throws Exception {
        return ResponseEntity.ok(userService.getUser(username));
    }
}

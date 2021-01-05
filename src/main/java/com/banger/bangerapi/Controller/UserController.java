package com.banger.bangerapi.Controller;

import com.banger.bangerapi.Models.User;
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
    @PostMapping("/update/{username}")
    public ResponseEntity<?> updateUSer(@PathVariable("username") String username,@RequestBody User user) throws Exception {
        return userService.updateUser(user,username);
    }
    @PostMapping("/updatePassword/{username}")
    public ResponseEntity<?> updatePassword(@PathVariable("username") String username,@RequestBody User user) throws Exception {
        return userService.updatePassword(user,username);
    }
}

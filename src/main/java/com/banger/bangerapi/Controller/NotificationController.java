package com.banger.bangerapi.Controller;

import com.banger.bangerapi.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequestMapping("api/notifications")
@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getAllNotifications")
    private ResponseEntity<?> getAllNotifications() throws Exception {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }
    @GetMapping("/deleteNotification/{id}")
    public  ResponseEntity<?> deleteNotification(@PathVariable int id){
        return  notificationService.deleteNotification(id);
    }
}

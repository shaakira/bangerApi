package com.banger.bangerapi.Service;

import com.banger.bangerapi.Models.Notification;
import com.banger.bangerapi.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications(){
        List<Notification> notifications=notificationRepository.findAll();
        return notifications;
    }
    public ResponseEntity<String> deleteNotification(int id){
        notificationRepository.deleteById(id);
        return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
    }
}

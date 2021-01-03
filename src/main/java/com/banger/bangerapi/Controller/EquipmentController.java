package com.banger.bangerapi.Controller;

import com.banger.bangerapi.Service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/equipment")
@RestController
@CrossOrigin
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("/getAllEquipments")
    private ResponseEntity<?> getAllEquipments() throws Exception{
        return ResponseEntity.ok(equipmentService.getAllEquipments());
    }
}

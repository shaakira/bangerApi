package com.banger.bangerapi.Controller;

import com.banger.bangerapi.Models.Equipment;
import com.banger.bangerapi.Service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/updateEquipment/{id}")
    public ResponseEntity<?> updateEquipment(@PathVariable("id") int id,@RequestBody Equipment equipment) throws Exception {
        return equipmentService.updateEquipment(equipment,id);
    }
    @GetMapping("/deleteEquipment/{id}")
    public  ResponseEntity<?> deleteEquipment(@PathVariable int id){
        return  equipmentService.deleteEquipment(id);
    }
}

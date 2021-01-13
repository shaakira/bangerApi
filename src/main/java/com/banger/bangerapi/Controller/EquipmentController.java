package com.banger.bangerapi.Controller;

import com.banger.bangerapi.Models.Equipment;
import com.banger.bangerapi.Service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/addEquipment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity addEquipment(@RequestParam MultipartFile file,String name,String description,double price,int count){
        Equipment equipment=new Equipment();
        equipment.setName(name);
        equipment.setDescription(description);
        equipment.setPrice(price);
        equipment.setCount(count);
        return equipmentService.addEquipment(file,equipment);
    }
}

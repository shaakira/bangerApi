package com.banger.bangerapi.Service;

import com.banger.bangerapi.Models.Equipment;
import com.banger.bangerapi.Repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentService() {
    }


    public List<Equipment> getAllEquipments(){
        List<Equipment> equipment=equipmentRepository.findAll();
        return equipment;
    }
    public ResponseEntity<String> updateEquipment(Equipment equipment,int id){
        Equipment existingEquipment=equipmentRepository.findById(id).orElse(null);
        existingEquipment.setCount(equipment.getCount());
        existingEquipment.setName(equipment.getName());
        existingEquipment.setDescription(equipment.getDescription());
        existingEquipment.setPrice(equipment.getPrice());
        equipmentRepository.save(existingEquipment);
        return new ResponseEntity<>("Equipment Updated Successfully",HttpStatus.OK);
    }
    public ResponseEntity<String> deleteEquipment(int id){
        equipmentRepository.deleteById(id);
        return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
    }
}

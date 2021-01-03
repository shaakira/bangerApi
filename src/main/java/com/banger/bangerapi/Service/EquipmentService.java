package com.banger.bangerapi.Service;

import com.banger.bangerapi.Models.Equipment;
import com.banger.bangerapi.Repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}

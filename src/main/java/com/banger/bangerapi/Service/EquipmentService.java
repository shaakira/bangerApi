package com.banger.bangerapi.Service;

import com.banger.bangerapi.Models.Equipment;
import com.banger.bangerapi.Repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;
    @Value("${C:\\Users\\SHAAKIRA\\Desktop\\EIRLS\\Banger\\bangerapi\\src\\main\\webapp\\resources\\Image}")
    public String uploadDir;

    @Autowired
    public EquipmentService() {
    }

    public ResponseEntity<String> addEquipment(MultipartFile files, Equipment equipment) {

        upload(files);
        equipment.setImage(files.getOriginalFilename());
        equipmentRepository.save(equipment);
        return new ResponseEntity<>("Equipment Added Successfully", HttpStatus.OK);
    }

    public void upload(MultipartFile file) {
        try {
            Path copyLocation = Paths
                    .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {

        }

    }

    public List<Equipment> getAllEquipments() {
        List<Equipment> equipment = equipmentRepository.findAll();
        return equipment;
    }

    public ResponseEntity<String> updateEquipment(Equipment equipment, int id) {
        Equipment existingEquipment = equipmentRepository.findById(id).orElse(null);
        existingEquipment.setCount(equipment.getCount());
        existingEquipment.setName(equipment.getName());
        existingEquipment.setDescription(equipment.getDescription());
        existingEquipment.setPrice(equipment.getPrice());
        equipmentRepository.save(existingEquipment);
        return new ResponseEntity<>("Equipment Updated Successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteEquipment(int id) {
        equipmentRepository.deleteById(id);
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}

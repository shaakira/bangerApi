package com.banger.bangerapi.Repository;

import com.banger.bangerapi.Models.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Integer> {
}

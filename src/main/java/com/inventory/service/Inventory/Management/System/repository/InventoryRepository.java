package com.inventory.service.Inventory.Management.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventory.service.Inventory.Management.System.entity.Inventory;

/*Extending JPA Repository For Using Predefined db persist methods */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}

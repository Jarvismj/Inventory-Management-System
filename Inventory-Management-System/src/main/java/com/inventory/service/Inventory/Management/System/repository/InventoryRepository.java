package com.inventory.service.Inventory.Management.System.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.service.Inventory.Management.System.entity.Inventory;

public interface InventoryRepository  extends JpaRepository<Inventory,Long> {
	
	
	

}

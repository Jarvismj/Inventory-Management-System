package com.inventory.service.Inventory.Management.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventory.service.Inventory.Management.System.entity.UserInfo;

/*Extending JPA Repository For Using Predefined db persist methods */
@Repository
public interface UserRepo extends JpaRepository<UserInfo, Integer> {

	UserInfo findByUsername(String username);
}

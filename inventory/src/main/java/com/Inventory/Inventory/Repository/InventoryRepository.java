package com.Inventory.Inventory.Repository;



import com.Inventory.Inventory.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    boolean existsByItemId(String itemId);  // Duplicate check for itemId
    @Query(value = "SELECT * FROM inventory WHERE id = ?1", nativeQuery = true)
    Inventory getInventoryById(long id);

    @Query(value = "SELECT * FROM inventory WHERE item_id = ?1", nativeQuery = true)
    Inventory getInventoryByItemId(String itemId);
    @Query(value = "SELECT * FROM inventory", nativeQuery = true)
    List<Inventory> getAllInventory();


}
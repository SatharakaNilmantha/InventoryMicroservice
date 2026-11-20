package com.Inventory.Inventory.Controller;

import com.Inventory.Inventory.Dto.InventoryDto;
import com.Inventory.Inventory.Exception.DuplicateException;
import com.Inventory.Inventory.Exception.NotFoundException;
import com.Inventory.Inventory.Services.InventoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    @Autowired
    private InventoryServices inventoryServices;

    @PostMapping("/add")
    public ResponseEntity<String> addInventory(@RequestBody InventoryDto inventoryDto) {
        try {
            String response = inventoryServices.addInventory(inventoryDto);
            return ResponseEntity.ok(response);
        } catch (DuplicateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred while adding inventory.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllInventory() {
        try {
            List<InventoryDto> inventories = inventoryServices.getAllInventory();
            return ResponseEntity.ok(inventories);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getInventoryById(@PathVariable String itemId) {
        try {
            InventoryDto inventoryDto = inventoryServices.getInventoryByItemId(itemId);
            return ResponseEntity.ok(inventoryDto);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateInventory(@PathVariable long id, @RequestBody InventoryDto inventoryDto) {
        try {
            String response = inventoryServices.updateInventory(id, inventoryDto);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DuplicateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable long id) {
        try {
            String response = inventoryServices.deleteInventory(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
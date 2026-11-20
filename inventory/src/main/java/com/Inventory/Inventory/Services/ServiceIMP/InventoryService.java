package com.Inventory.Inventory.Services.ServiceIMP;



import com.Inventory.Inventory.Dto.InventoryDto;
import com.Inventory.Inventory.Entity.Inventory;
import com.Inventory.Inventory.Exception.DuplicateException;
import com.Inventory.Inventory.Exception.NotFoundException;
import com.Inventory.Inventory.Repository.InventoryRepository;
import com.Inventory.Inventory.Services.InventoryServices;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService implements InventoryServices {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String addInventory(InventoryDto inventoryDto) {
        // Check for duplicate itemId
        if (inventoryRepository.existsByItemId(inventoryDto.getItemId())) {
            throw new DuplicateException("Inventory item with ID '" + inventoryDto.getItemId() + "' already exists.");
        }

        Inventory inventory = modelMapper.map(inventoryDto, Inventory.class);
        inventoryRepository.save(inventory);
        return "Inventory added successfully with itemId : " + inventoryDto.getItemId();
    }

    @Override
    public List<InventoryDto> getAllInventory() {
        List<Inventory> inventories = inventoryRepository.getAllInventory();
        return modelMapper.map(inventories, new TypeToken<List<InventoryDto>>() {}.getType());
    }

    @Override
    public InventoryDto getInventoryByItemId(String itemId) {
        Inventory inventory = inventoryRepository.getInventoryByItemId(itemId);
        if (inventory == null) {
            throw new NotFoundException("Inventory not found with id : " + itemId);
        }
        return modelMapper.map(inventory, InventoryDto.class);
    }

    @Override
    public String updateInventory(long id, InventoryDto inventoryDto) {
        Inventory existingInventory = inventoryRepository.getInventoryById(id);
        if (existingInventory == null) {
            throw new NotFoundException("Inventory not found with id : " + id);
        }

        // Check duplicate itemId on update
        if (!existingInventory.getItemId().equals(inventoryDto.getItemId()) &&
                inventoryRepository.existsByItemId(inventoryDto.getItemId())) {
            throw new DuplicateException("Inventory item with ID '" + inventoryDto.getItemId() + "' already exists.");
        }

        existingInventory.setItemId(inventoryDto.getItemId());
        existingInventory.setProductId(inventoryDto.getProductId());
        existingInventory.setQuantity(inventoryDto.getQuantity());

        inventoryRepository.save(existingInventory);
        return "Inventory updated successfully with id : " + id;
    }

    @Override
    public String deleteInventory(long id) {
        Inventory inventory = inventoryRepository.getInventoryById(id);
        if (inventory == null) {
            throw new NotFoundException("Inventory not found with id : " + id);
        }
        inventoryRepository.deleteById(id);
        return "Inventory deleted successfully with id : " + id;
    }
}
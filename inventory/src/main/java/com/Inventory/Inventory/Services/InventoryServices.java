package com.Inventory.Inventory.Services;


import com.Inventory.Inventory.Dto.InventoryDto;

import java.util.List;

public interface InventoryServices {
    String addInventory(InventoryDto inventoryDto);
    List<InventoryDto> getAllInventory();
    public InventoryDto getInventoryByItemId(String itemId);
    String updateInventory(long id, InventoryDto inventoryDto);
    String deleteInventory(long id);
}
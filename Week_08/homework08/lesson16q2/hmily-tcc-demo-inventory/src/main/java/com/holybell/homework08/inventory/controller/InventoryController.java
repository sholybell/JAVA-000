
package com.holybell.homework08.inventory.controller;

import com.holybell.homework08.inventory.dto.InventoryDTO;
import com.holybell.homework08.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @RequestMapping("/decrease")
    public Boolean decrease(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.decrease(inventoryDTO);
    }

    @RequestMapping("/mockWithTryException")
    public Boolean mockWithTryException(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.mockWithTryException(inventoryDTO);
    }
}

package com.holybell.homework08.order.client;

import com.holybell.homework08.order.client.InventoryClient;
import com.holybell.homework08.order.dto.InventoryDTO;
import org.springframework.stereotype.Component;

@Component
public class InventoryHystrix implements InventoryClient {

    @Override
    public Boolean mockWithTryException(InventoryDTO inventoryDTO) {
        return false;
    }

}

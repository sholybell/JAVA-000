
package com.holybell.homework08.order.client;

import com.holybell.homework08.order.dto.InventoryDTO;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "inventory-service")
public interface InventoryClient {

    /**
     * 模拟库存扣减异常.
     *
     * @param inventoryDTO 实体对象
     * @return true 成功
     */
    @Hmily
    @RequestMapping("/inventory-service/inventory/mockWithTryException")
    Boolean mockWithTryException(@RequestBody InventoryDTO inventoryDTO);
    
}

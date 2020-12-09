
package com.holybell.homework08.inventory.service.impl;

import com.holybell.homework08.inventory.dto.InventoryDTO;
import com.holybell.homework08.inventory.mapper.InventoryMapper;
import com.holybell.homework08.inventory.service.InventoryService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Autowired
    private InventoryMapper inventoryMapper;

    /**
     * 扣减库存操作.
     * 这一个tcc接口
     *
     * @param inventoryDTO 库存DTO对象
     * @return true
     */
    @Override
    @HmilyTCC(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public Boolean decrease(InventoryDTO inventoryDTO) {
        LOGGER.info("==========try扣减库存decrease===========");
        inventoryMapper.decrease(inventoryDTO);
        return true;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    @Transactional
    public Boolean mockWithTryException(InventoryDTO inventoryDTO) {
        //这里是模拟异常所以就直接抛出异常了
        throw new HmilyRuntimeException("库存扣减异常！");
    }

    public Boolean confirmMethod(InventoryDTO inventoryDTO) {
        LOGGER.info("==========confirmMethod库存确认方法===========");
        return inventoryMapper.confirm(inventoryDTO) > 0;
    }

    public Boolean cancelMethod(InventoryDTO inventoryDTO) {
        LOGGER.info("==========cancelMethod库存取消方法===========");
        return inventoryMapper.cancel(inventoryDTO) > 0;
    }
}

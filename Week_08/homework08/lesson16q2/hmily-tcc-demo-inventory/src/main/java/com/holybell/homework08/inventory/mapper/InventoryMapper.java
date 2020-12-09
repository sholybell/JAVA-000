
package com.holybell.homework08.inventory.mapper;

import com.holybell.homework08.inventory.dto.InventoryDTO;
import org.apache.ibatis.annotations.Update;

public interface InventoryMapper {

    /**
     * Decrease int.
     *
     * @param inventoryDTO the inventory dto
     * @return the int
     */
    @Update("update inventory set total_inventory = total_inventory - #{count}," +
            " lock_inventory= lock_inventory + #{count} " +
            " where product_id =#{productId} and total_inventory > 0  ")
    int decrease(InventoryDTO inventoryDTO);

    /**
     * Decrease tac int.
     *
     * @param inventoryDTO the inventory dto
     * @return the int
     */
    @Update("update inventory set total_inventory = total_inventory - #{count} " +
            " where product_id =#{productId} and total_inventory > 0  ")
    int decreaseTAC(InventoryDTO inventoryDTO);

    /**
     * Test decrease int.
     *
     * @param inventoryDTO the inventory dto
     * @return the int
     */
    @Update("update inventory set total_inventory = total_inventory - #{count}" +
            " where product_id =#{productId} and total_inventory > 0  ")
    int testDecrease(InventoryDTO inventoryDTO);

    /**
     * Confirm int.
     *
     * @param inventoryDTO the inventory dto
     * @return the int
     */
    @Update("update inventory set " +
            " lock_inventory = lock_inventory - #{count} " +
            " where product_id =#{productId} and lock_inventory > 0 ")
    int confirm(InventoryDTO inventoryDTO);

    /**
     * Cancel int.
     *
     * @param inventoryDTO the inventory dto
     * @return the int
     */
    @Update("update inventory set total_inventory = total_inventory + #{count}," +
            " lock_inventory= lock_inventory - #{count} " +
            " where product_id =#{productId}  and lock_inventory > 0 ")
    int cancel(InventoryDTO inventoryDTO);
}

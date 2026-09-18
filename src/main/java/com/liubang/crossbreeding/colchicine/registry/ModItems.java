package com.liubang.crossbreeding.colchicine.registry;

import com.liubang.crossbreeding.colchicine.CrossbreedingColchicine;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items REGISTRY =
            DeferredRegister.createItems(CrossbreedingColchicine.MOD_ID);

    /** 秋水仙素溶液。使用原版药水瓶模型，不可堆叠。 */
    public static final DeferredItem<Item> COLCHICINE_SOLUTION = REGISTRY.register(
            "colchicine_solution",
            () -> new Item(new Item.Properties().stacksTo(1))
    );

    public static void register(IEventBus modBus) {
        REGISTRY.register(modBus);
    }
}
package com.liubang.crossbreeding.colchicine;

import com.liubang.crossbreeding.colchicine.registry.ModCreativeTabs;
import com.liubang.crossbreeding.colchicine.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(CrossbreedingColchicine.MOD_ID)
public class CrossbreedingColchicine {

    public static final String MOD_ID = "crossbreeding_colchicine";

    public CrossbreedingColchicine(IEventBus modBus, ModContainer container) {
        ModItems.register(modBus);
        ModCreativeTabs.register(modBus);
    }
}
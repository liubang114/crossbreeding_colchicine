package com.liubang.crossbreeding.colchicine.registry;

import com.liubang.crossbreeding.colchicine.CrossbreedingColchicine;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> REGISTRY =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CrossbreedingColchicine.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN =
            REGISTRY.register("main", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.crossbreeding_colchicine"))
                    .icon(() -> new ItemStack(ModItems.COLCHICINE_SOLUTION.get()))
                    .displayItems((params, output) -> {
                        output.accept(new ItemStack(ModItems.COLCHICINE_SOLUTION.get()));
                    })
                    .build());

    public static void register(IEventBus modBus) {
        REGISTRY.register(modBus);
    }
}
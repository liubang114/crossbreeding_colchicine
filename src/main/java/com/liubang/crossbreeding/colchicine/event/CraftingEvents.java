package com.liubang.crossbreeding.colchicine.event;

import com.liubang.crossbreeding.colchicine.CrossbreedingColchicine;
import com.liubang.crossbreeding.colchicine.registry.ModItems;
import com.liubang.crossbreeding.core.Gamete;
import com.liubang.crossbreeding.core.Genome;
import com.liubang.crossbreeding.registry.ModDataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * 工作台事件：秋水仙素溶液 + 单个配子 → 杂交种子（基因型翻倍）。
 * 消耗秋水仙素溶液后，返回一个空玻璃瓶。
 */
@EventBusSubscriber(modid = CrossbreedingColchicine.MOD_ID)
public class CraftingEvents {

    private static final ResourceLocation SEED_ID =
            ResourceLocation.fromNamespaceAndPath("crossbreeding", "crossbreeding_seed");
    private static final ResourceLocation FEMALE_GAMETE_ID =
            ResourceLocation.fromNamespaceAndPath("crossbreeding", "female_gamete");
    private static final ResourceLocation MALE_GAMETE_ID =
            ResourceLocation.fromNamespaceAndPath("crossbreeding", "male_gamete");

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack result = event.getCrafting();
        if (result.isEmpty()) return;
        if (!result.getItem().builtInRegistryHolder().key().location().equals(SEED_ID)) return;
        if (result.has(ModDataComponents.GENOME.get())) return;

        Player player = event.getEntity();
        Container input = event.getInventory();

        boolean hasColchicine = false;
        Gamete gamete = null;

        for (int i = 0; i < input.getContainerSize(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;

            if (stack.is(ModItems.COLCHICINE_SOLUTION.get())) {
                hasColchicine = true;
            } else {
                ResourceLocation id = stack.getItem().builtInRegistryHolder().key().location();
                if (id.equals(FEMALE_GAMETE_ID) || id.equals(MALE_GAMETE_ID)) {
                    gamete = stack.get(ModDataComponents.GAMETE.get());
                }
            }
        }

        if (!hasColchicine || gamete == null) return;

        // 单倍体翻倍成纯合二倍体
        Genome doubled = gamete.toDoubledGenome();
        result.set(ModDataComponents.GENOME.get(), doubled);

        // 消耗秋水仙素溶液后，把空玻璃瓶还给玩家
        ItemStack emptyBottle = new ItemStack(Items.GLASS_BOTTLE);
        if (!player.getInventory().add(emptyBottle)) {
            // 背包满了就丢在脚下
            player.drop(emptyBottle, false);
        }
    }
}
package com.liubang.crossbreeding.colchicine.event;

import com.liubang.crossbreeding.colchicine.CrossbreedingColchicine;
import com.liubang.crossbreeding.colchicine.registry.ModItems;
import com.liubang.crossbreeding.colchicine.util.AdvancementHelper;
import com.liubang.crossbreeding.core.Gamete;
import com.liubang.crossbreeding.core.Genome;
import com.liubang.crossbreeding.registry.ModDataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

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

        Genome doubled = gamete.toDoubledGenome();
        result.set(ModDataComponents.GENOME.get(), doubled);

        ItemStack emptyBottle = new ItemStack(Items.GLASS_BOTTLE);
        if (!player.getInventory().add(emptyBottle)) {
            player.drop(emptyBottle, false);
        }

        // 成就：单倍体育种
        if (player instanceof ServerPlayer serverPlayer) {
            AdvancementHelper.grant(serverPlayer, "haploid_breeding");
        }
    }
}

package com.liubang.crossbreeding.colchicine.event;

import com.liubang.crossbreeding.colchicine.CrossbreedingColchicine;
import com.liubang.crossbreeding.colchicine.registry.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

/**
 * 酿造配方：铃兰 + 普通水瓶 → 秋水仙素溶液。
 *
 * <p>参数顺序：addRecipe(下方药水, 上方材料, 输出)
 */
@EventBusSubscriber(modid = CrossbreedingColchicine.MOD_ID)
public class BrewingEvents {

    @SubscribeEvent
    public static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
        event.getBuilder().addRecipe(
                Ingredient.of(Items.POTION),                 // 下方：药水瓶（含普通水瓶）
                Ingredient.of(Items.LILY_OF_THE_VALLEY),     // 上方：铃兰
                new ItemStack(ModItems.COLCHICINE_SOLUTION.get())
        );
    }
}
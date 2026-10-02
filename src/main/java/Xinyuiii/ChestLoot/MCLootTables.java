package Xinyuiii.ChestLoot;

import com.seedfinding.mcfeature.loot.LootPool;
import com.seedfinding.mcfeature.loot.LootTable;
import com.seedfinding.mcfeature.loot.entry.ItemEntry;
import com.seedfinding.mcfeature.loot.function.SetCountFunction;
import com.seedfinding.mcfeature.loot.item.Items;
import com.seedfinding.mcfeature.loot.roll.UniformRoll;

public class MCLootTables {
    public static final LootTable RUINED_PORTAL_CHEST = new LootTable(
            new LootPool(new UniformRoll(4.0F, 8.0F),
                    new ItemEntry(Items.OBSIDIAN, 40).apply(version -> SetCountFunction.uniform(1.0F, 2.0F)),
                    new ItemEntry(Items.FLINT, 40).apply(version -> SetCountFunction.uniform(1.0F, 4.0F)),
                    new ItemEntry(Items.IRON_NUGGET, 40).apply(version -> SetCountFunction.uniform(9.0F, 18.0F)),
                    new ItemEntry(Items.FLINT_AND_STEEL, 40),
                    new ItemEntry(Items.FIRE_CHARGE, 40),
                    new ItemEntry(Items.GOLDEN_APPLE, 15),
                    new ItemEntry(Items.GOLD_NUGGET, 15).apply(version -> SetCountFunction.uniform(4.0F, 24.0F)),
                    new ItemEntry(Items.GOLDEN_SWORD, 15).apply(version -> new EnchantRandomly(Items.GOLDEN_SWORD)),
                    new ItemEntry(Items.GOLDEN_AXE, 15).apply(version -> new EnchantRandomly(Items.GOLDEN_AXE)),
                    new ItemEntry(Items.GOLDEN_HOE, 15).apply(version -> new EnchantRandomly(Items.GOLDEN_HOE)),
                    new ItemEntry(Items.GOLDEN_SHOVEL, 15).apply(version -> new EnchantRandomly(Items.GOLDEN_SHOVEL)),
                    new ItemEntry(Items.GOLDEN_PICKAXE, 15).apply(version -> new EnchantRandomly(Items.GOLDEN_PICKAXE)),
                    new ItemEntry(Items.GOLDEN_BOOTS, 15).apply(version -> new EnchantRandomly(Items.GOLDEN_BOOTS)),
                    new ItemEntry(Items.GOLDEN_CHESTPLATE, 15).apply(version -> new EnchantRandomly(Items.GOLDEN_CHESTPLATE)),
                    new ItemEntry(Items.GOLDEN_HELMET, 15).apply(version -> new EnchantRandomly(Items.GOLDEN_HELMET)),
                    new ItemEntry(Items.GOLDEN_LEGGINGS, 15).apply(version -> new EnchantRandomly(Items.GOLDEN_LEGGINGS)),
                    new ItemEntry(Items.GLISTERING_MELON_SLICE, 5).apply(version -> SetCountFunction.uniform(4.0F, 12.0F)),
                    new ItemEntry(Items.GOLDEN_HORSE_ARMOR, 5),
                    new ItemEntry(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, 5),
                    new ItemEntry(Items.GOLDEN_CARROT, 5).apply(version -> SetCountFunction.uniform(4.0F, 12.0F)),
                    new ItemEntry(Items.CLOCK, 5),
                    new ItemEntry(Items.GOLD_INGOT, 5).apply(version -> SetCountFunction.uniform(2.0F, 8.0F)),
                    new ItemEntry(Items.BELL),
                    new ItemEntry(Items.ENCHANTED_GOLDEN_APPLE),
                    new ItemEntry(Items.GOLD_BLOCK).apply(version -> SetCountFunction.uniform(1.0F, 2.0F)))
    );
}
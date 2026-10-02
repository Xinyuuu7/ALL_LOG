package Xinyuiii.ChestLoot;

import com.seedfinding.mcfeature.loot.LootPool;
import com.seedfinding.mcfeature.loot.LootTable;
import com.seedfinding.mcfeature.loot.entry.EmptyEntry;
import com.seedfinding.mcfeature.loot.entry.ItemEntry;
import com.seedfinding.mcfeature.loot.function.ApplyDamageFunction;
import com.seedfinding.mcfeature.loot.function.EnchantRandomlyFunction;
import com.seedfinding.mcfeature.loot.function.SetCountFunction;
import com.seedfinding.mcfeature.loot.item.Items;
import com.seedfinding.mcfeature.loot.roll.ConstantRoll;
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

    public static final LootTable BASTION_OTHER_CHEST = new LootTable(
            new LootPool(new ConstantRoll(1),
                    new ItemEntry(Items.DIAMOND_PICKAXE, 6).apply(version -> new EnchantRandomlyFunction(Items.DIAMOND_PICKAXE).apply(version)),
                    new ItemEntry(Items.DIAMOND_SHOVEL, 6),
                    new ItemEntry(Items.CROSSBOW, 6).apply(version -> new ApplyDamageFunction(), version -> new EnchantRandomlyFunction(Items.CROSSBOW).apply(version)),
                    new ItemEntry(Items.ANCIENT_DEBRIS, 12),
                    new ItemEntry(Items.NETHERITE_SCRAP, 4),
                    new ItemEntry(Items.SPECTRAL_ARROW, 10).apply(version -> SetCountFunction.uniform(10.0F, 22.0F)),
                    new ItemEntry(Items.PIGLIN_BANNER_PATTERN, 9),
                    new ItemEntry(Items.MUSIC_DISC_PIGSTEP, 5),
                    new ItemEntry(Items.GOLDEN_CARROT, 12).apply(version -> SetCountFunction.uniform(6.0F, 17.0F)),
                    new ItemEntry(Items.GOLDEN_APPLE, 9),
                    new ItemEntry(Items.ENCHANTED_BOOK, 10).apply(version -> new SoulSpeedEnchantRandomly())),
            new LootPool(new ConstantRoll(2),
                    new ItemEntry(Items.IRON_SWORD, 2).apply(version -> new ApplyDamageFunction(), version -> new EnchantRandomlyFunction(Items.IRON_SWORD).apply(version)),
                    new ItemEntry(Items.IRON_BLOCK, 2),
                    new ItemEntry(Items.GOLDEN_BOOTS).apply(version -> new SoulSpeedEnchantRandomly()),
                    new ItemEntry(Items.GOLDEN_AXE).apply(version -> new EnchantRandomlyFunction(Items.GOLDEN_AXE).apply(version)),
                    new ItemEntry(Items.GOLD_BLOCK, 2),
                    new ItemEntry(Items.CROSSBOW),
                    new ItemEntry(Items.GOLD_INGOT, 2).apply(version -> SetCountFunction.uniform(1.0F, 6.0F)),
                    new ItemEntry(Items.IRON_INGOT, 2).apply(version -> SetCountFunction.uniform(1.0F, 6.0F)),
                    new ItemEntry(Items.GOLDEN_SWORD),
                    new ItemEntry(Items.GOLDEN_CHESTPLATE),
                    new ItemEntry(Items.GOLDEN_HELMET),
                    new ItemEntry(Items.GOLDEN_LEGGINGS),
                    new ItemEntry(Items.GOLDEN_BOOTS),
                    new ItemEntry(Items.CRYING_OBSIDIAN, 2).apply(version -> SetCountFunction.uniform(1.0F, 5.0F))),
            new LootPool(new UniformRoll(3.0F, 4.0F),
                    new ItemEntry(Items.GILDED_BLACKSTONE, 2).apply(version -> SetCountFunction.uniform(1.0F, 5.0F)),
                    new ItemEntry(Items.CHAIN).apply(version -> SetCountFunction.uniform(2.0F, 10.0F)),
                    new ItemEntry(Items.MAGMA_CREAM, 2).apply(version -> SetCountFunction.uniform(2.0F, 6.0F)),
                    new ItemEntry(Items.BONE_BLOCK).apply(version -> SetCountFunction.uniform(3.0F, 6.0F)),
                    new ItemEntry(Items.IRON_NUGGET).apply(version -> SetCountFunction.uniform(2.0F, 8.0F)),
                    new ItemEntry(Items.OBSIDIAN).apply(version -> SetCountFunction.uniform(4.0F, 6.0F)),
                    new ItemEntry(Items.GOLD_NUGGET).apply(version -> SetCountFunction.uniform(2.0F, 8.0F)),
                    new ItemEntry(Items.STRING).apply(version -> SetCountFunction.uniform(4.0F, 6.0F)),
                    new ItemEntry(Items.ARROW, 2).apply(version -> SetCountFunction.uniform(5.0F, 17.0F)),
                    new ItemEntry(Items.COOKED_PORKCHOP)),
            new LootPool(new ConstantRoll(1),
                    new EmptyEntry(11),
                    new ItemEntry(Xinyuiii.ChestLoot.Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE)),
            new LootPool(new ConstantRoll(1),
                    new EmptyEntry(9),
                    new ItemEntry(Xinyuiii.ChestLoot.Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
    );
}
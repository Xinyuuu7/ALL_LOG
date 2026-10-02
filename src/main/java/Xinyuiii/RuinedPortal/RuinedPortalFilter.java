package Xinyuiii.RuinedPortal;

import Xinyuiii.ChestLoot.DecoratorRand;
import Xinyuiii.ChestLoot.MCLootTables;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mcfeature.loot.LootContext;
import com.seedfinding.mcfeature.loot.LootTable;
import com.seedfinding.mcfeature.loot.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class RuinedPortalFilter {
    //4 28 minecraft:ruined_portal
    //4 29 minecraft:ruined_portal_desert
    //4 30 minecraft:ruined_portal_jungle
    //4 31 minecraft:ruined_portal_mountain
    //4 32 minecraft:ruined_portal_nether
    //4 33 minecraft:ruined_portal_ocean
    //4 34 minecraft:ruined_portal_swamp

    // don't need to check standard and nether

    //DESERT_BIOME_ID:
    //desert 2
    //desert_hills 17
    //desert_lakes 130

    //JUNGLE_BIOME_ID:
    //jungle 21
    //jungle_hills 22
    //jungle_edge (sparse_jungle) 23
    //modified_jungle 149
    //modified_jungle_edge 151
    //bamboo_jungle 168
    //bamboo_jungle_hills 169

    //MOUNTAINS_BIOME_ID:
    //mountains (windswept_hills) 3
    //savanna_plateau 36
    //badlands 37
    //wooded_badlands_plateau 38
    //stone_shore (stony_shore) 25
    //wooded_mountains (windswept_forest) 34
    //gravelly_mountains (windswept_gravelly_hills) 131
    //shattered_savanna (windswept_savanna) 163
    //eroded_badlands 165
    //meadow 177
    //grove 178
    //snowy_slopes 179
    //jagged_peaks 180
    //frozen_peaks 181
    //stony_peaks 182
    //cherry_grove 185

    //OCEAN_BIOME_ID:
    //ocean 0
    //frozen_ocean 10
    //deep_ocean 24
    //warm_ocean 44
    //lukewarm_ocean 45
    //cold_ocean 46
    //deep_lukewarm_ocean 48
    //deep_cold_ocean 49
    //deep_frozen_ocean 50

    //SWAMP_BIOME_ID:
    //swamp 6
    //mangrove_swamp 184

    private final DecoratorRand rand = new DecoratorRand();
    private final LootContext ctx = new LootContext(0L);
    private final LootTable RPLOOT = MCLootTables.RUINED_PORTAL_CHEST;

    private final List<Integer> DESERT_BIOME_ID = Arrays.asList(2, 17, 130);
    private final List<Integer> JUNGLE_BIOME_ID = Arrays.asList(21, 22, 23, 149, 151, 168, 169);
    private final List<Integer> MOUNTAINS_BIOME_ID = Arrays.asList(3, 25, 34, 36, 37, 38, 131, 163, 165,
            177, 178, 179, 180, 181, 182, 185);
    private final List<Integer> OCEAN_BIOME_ID = Arrays.asList(0, 10, 24, 44, 45, 46, 48, 49, 50);
    private final List<Integer> SWAMP_BIOME_ID = Arrays.asList(6, 184);

    public RuinedPortalFilter() {}

    public boolean check(long worldSeed, CPos pos, int type, int biome) {
        if (DESERT_BIOME_ID.contains(biome) ||
                JUNGLE_BIOME_ID.contains(biome) ||
                OCEAN_BIOME_ID.contains(biome) ||
                SWAMP_BIOME_ID.contains(biome)) {
            return false;
        }
        int missingObsidian = getMissingObsidian(type);
        long populationSeed = rand.getPopulationSeed(worldSeed, pos.getX() << 4, pos.getZ() << 4);
        int salt_index = MOUNTAINS_BIOME_ID.contains(biome) ? 31 : 28;
        rand.setDecoratorSeed(populationSeed,salt_index,4);
        ctx.setSeed(rand.nextLong());
        List<ItemStack> items = RPLOOT.generate(ctx);
        boolean lit = false;
        boolean axe = false;
        int obsidian = 0;
        for (ItemStack item : items) {
            String name = item.getItem().getName();
            switch (name) {
                case "fire_charge", "flint_and_steel" -> lit = true;
                case "golden_axe" -> axe = true;
                case "obsidian" -> obsidian = item.getCount();
            }
        }
        return lit && axe && obsidian >= missingObsidian;
    }

    private static int getMissingObsidian(int typeID) {
        return switch (typeID) {
            case 0, 8 -> 2;
            case 1, 2 -> 4;
            case 3, 7 -> 3;
            case 5, 6 -> 1;
            case 9 -> 7;
            default -> 5;
        };
    }
}
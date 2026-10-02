package Xinyuiii.Bastion;

import Xinyuiii.Bastion.enumType.BastionType;
import Xinyuiii.Bastion.properties.BastionGenerator;
import Xinyuiii.Bastion.properties.BastionGenerator.Piece;
import Xinyuiii.Bastion.reecriture.BastionPools.BastionStructureLoot;
import Xinyuiii.ChestLoot.DecoratorRand;
import Xinyuiii.ChestLoot.MCLootTables;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcfeature.loot.LootContext;
import com.seedfinding.mcfeature.loot.LootTable;
import com.seedfinding.mcfeature.loot.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BastionFilter {
    //4 18 minecraft:bastion_remnant

    private final BastionGenerator generator = new BastionGenerator(MCVersion.v1_21);
    private final DecoratorRand rand = new DecoratorRand();
    private final LootContext ctx = new LootContext(0L);
    private final LootTable BLOOT = MCLootTables.BASTION_OTHER_CHEST;

    public BastionFilter() {}

    public boolean checkLoot(long worldSeed, CPos pos) {
        generator.generate(worldSeed, pos);
        BastionType type = generator.getType();
        List<Piece> pieces = generator.getPieces();
        List<ItemStack> chests = new ArrayList<>();

        if (type == BastionType.HOUSING) {
            CPos rightRamparts = null;
            List<CPos> ramparts = new ArrayList<>();
            for (Piece piece : pieces) {
                switch (piece.name) {
                    case "units/walls/wall_base" -> rightRamparts = piece.pos.toChunkPos();
                    case "units/ramparts/ramparts_0" -> ramparts.add(piece.pos.toChunkPos());
                }
            }
            for (CPos chestChunk : ramparts) {
                long populationSeed = rand.getPopulationSeed(worldSeed, chestChunk.getX() << 4, chestChunk.getZ() << 4);
                rand.setDecoratorSeed(populationSeed, 40018);
                if (chestChunk == rightRamparts) {
                    rand.nextLong();
                    rand.nextLong();
                }
                for (int i = 0; i < 3; i++) {
                    ctx.setSeed(rand.nextLong());
                    chests.addAll(BLOOT.generate(ctx));
                }
            }
        }

        if (type == BastionType.BRIDGE) {
            for (Piece piece : pieces) {
                List<LootTable> looTables = BastionStructureLoot.STRUCTURE_LOOT_1_21_10.get(piece.name);
                if (!looTables.isEmpty()) {
                    BPos firstOffest = BastionStructureLoot.STRUCTURE_LOOT_OFFSETS.get(piece.name).getFirst();
                    CPos chestChunk = piece.pos.add(piece.getTransformedPos(firstOffest, piece.rotation)).toChunkPos();
                    long populationSeed = rand.getPopulationSeed(worldSeed, chestChunk.getX() << 4, chestChunk.getZ() << 4);
                    rand.setDecoratorSeed(populationSeed, 40018);
                    for (int i = 0; i < 3; i++) {
                        ctx.setSeed(rand.nextLong());
                        chests.addAll(BLOOT.generate(ctx));
                    }
                }
            }
        }

        if (type == BastionType.STABLES) {
            List<CPos> bottomChests = new ArrayList<>();
            List<CPos> ramparts = new ArrayList<>();
            for (Piece piece : pieces) {
                switch (piece.name) {
                    case "hoglin_stable/walls/wall_base", "hoglin_stable/walls/side_wall_0" -> bottomChests.add(piece.pos.toChunkPos());
                    case "hoglin_stable/ramparts/ramparts_1" -> ramparts.add(piece.pos.toChunkPos());
                }
            }
            for (CPos chestChunk : ramparts) {
                long populationSeed = rand.getPopulationSeed(worldSeed, chestChunk.getX() << 4, chestChunk.getZ() << 4);
                rand.setDecoratorSeed(populationSeed, 40018);
                if (bottomChests.contains(chestChunk)) {
                    rand.nextLong();
                }
                for (int i = 0; i < 3; i++) {
                    ctx.setSeed(rand.nextLong());
                    chests.addAll(BLOOT.generate(ctx));
                }
            }
        }
        int obsidian = 0;
        for (ItemStack item : chests) {
            if (item.getItem().getName().equals("obsidian")) {
                obsidian += item.getCount();
            }
        }
        return obsidian >= 20;
    }
}